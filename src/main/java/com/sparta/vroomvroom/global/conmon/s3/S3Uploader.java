package com.sparta.vroomvroom.global.conmon.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Uploader{

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;


    /*
    *** 이미지 업로드 ***
    * multipartFile에서 원본 파일 이름 가져옴
    * -> 파일명 중복 방지 위해 UUID 생성
    * -> URL 접근 위해 공백은 _로 대체 후 UUID와 합치기 (UUID_공백제거원본파일명)
    * -> dirName 폴더 하위에 저장 (dirName : S3에서 폴더 역할)
    * -> key(저장경로)만 반환 (예 : test/uuid_fileName)
     */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        String original = multipartFile.getOriginalFilename();
        if (original == null || original.isBlank()) {
            throw new IllegalArgumentException("파일명이 비어 있습니다.");
        }

        try {
            String uuid = UUID.randomUUID().toString();
            String safeName = uuid + "_" + original.replaceAll("\\s+", "_");    // 공백은 _로 대체
            String key = dirName + "/" + safeName;  // S3 저장경로

            // 업로드 요청 생성
            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .acl(ObjectCannedACL.PUBLIC_READ)           // 누구나 읽을 수 있게 공개 권한 부여
                    .contentType(multipartFile.getContentType())    // MIME 타입
                    .build();

            // 업로드 실행
            s3Client.putObject(putReq,
                    RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));

            return key;
        } catch (IOException e) {
            log.error("파일 업로드 중 IOException 발생", e);
            throw new IllegalArgumentException("파일 업로드 중 오류가 발생했습니다.", e);
        } catch (Exception e) {
            log.error("S3 업로드 실패", e);
            throw new IllegalArgumentException("S3 업로드에 실패했습니다.", e);
        }

    }

    // 다중 파일 업로드
    public List<String> uploadFiles(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        try {
            List<String> keys = new ArrayList<>();
            for (MultipartFile file : multipartFiles) {
                keys.add(upload(file, dirName));
            }
            return keys;
        } catch (Exception e) {
            log.error("다중 파일 업로드 실패", e);
            throw new IllegalArgumentException("다중 파일 업로드에 실패했습니다.", e);
        }
    }

    /*
    *** 단일 이미지 조회 ***
    * 매개변수로 key(S3 저장경로)를 받아옴
    * -> 해당 key로 URL 요청 후 반환
     */
    public String getFileUrl(String key) {
        try {
            // key의 유효성 검사
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build());

            // URL 요청 생성
            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            // S3에 업로드 된 파일의 URL 반환
            URL url = s3Client.utilities().getUrl(getUrlRequest);
            return url.toString();
        } catch (NoSuchKeyException e) {
            log.error("존재하지 않는 S3 객체: key={}", key, e);
            throw new IllegalArgumentException("존재하지 않는 파일입니다: " + key, e);
        } catch (S3Exception e) {
            log.error("S3 오류로 URL 조회 실패: key={}", key, e);
            throw new IllegalArgumentException("파일 URL 조회에 실패했습니다.", e);
        }catch (Exception e) {
            log.error("파일 URL 조회 실패, key={}", key, e);
            throw new IllegalArgumentException("파일 URL 조회에 실패했습니다.", e);
        }
    }

    // 다중 이미지 URL 조회
    public List<String> getFileUrls(List<String> keys) {
        List<String> urls = new ArrayList<>();
        for (String key : keys) {
            urls.add(getFileUrl(key)); // 존재 확인 + URL 변환 재사용
        }
        return urls;
    }

    // 파일 삭제
    public void delete(String key) {
        try {
            // key의 유효성 검사
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build());

            DeleteObjectRequest delReq = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(delReq);
        } catch (NoSuchKeyException e) {
            log.error("S3 파일 삭제 실패 - 존재하지 않음: key={}", key, e);
            throw new IllegalArgumentException("삭제하려는 파일이 존재하지 않습니다: " + key, e);
        } catch (S3Exception e) {
            log.error("S3 삭제 요청 실패: key={}", key, e);
            throw new IllegalArgumentException("S3 삭제 요청 실패: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("파일 삭제 실패, key={}", key, e);
            throw new IllegalArgumentException("파일 삭제에 실패했습니다.", e);
        }
    }

    // 기존 파일 삭제 후 새 파일 업로드
    public String update(MultipartFile newFile, String oldKey, String dirName)  {
        try {
            if (oldKey != null && !oldKey.isBlank()) {
                // key의 유효성 검사
                s3Client.headObject(HeadObjectRequest.builder()
                        .bucket(bucket)
                        .key(oldKey)
                        .build());

                delete(oldKey);
            }
            return upload(newFile, dirName);
        } catch (NoSuchKeyException e) {
            log.error("업데이트 실패 - 기존 파일 없음: oldKey={}", oldKey, e);
            throw new IllegalArgumentException("기존 파일이 존재하지 않아 업데이트할 수 없습니다: " + oldKey, e);
        } catch (Exception e) {
            log.error("파일 업데이트 중 오류 발생: oldKey={}", oldKey, e);
            throw new IllegalArgumentException("파일 업데이트에 실패했습니다.", e);
        }
    }

    public void deleteByUrl(String url) {
        try {
            String key = extractKeyFromUrl(url);

            // 삭제 요청
            DeleteObjectRequest delReq = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3Client.deleteObject(delReq);

            log.info("S3 파일 삭제 완료: {}", key);
        } catch (Exception e) {
            log.error("S3 파일 삭제 실패: {}", url, e);
            throw new IllegalArgumentException("S3 파일 삭제 실패: " + e.getMessage(), e);
        }
    }

    private String extractKeyFromUrl(String url) {
        try {
            // S3 URL 기본 구조를 이용해 key 부분만 추출
            int index = url.indexOf(".amazonaws.com/");
            if (index == -1) {
                throw new IllegalArgumentException("올바른 S3 URL 형식이 아닙니다: " + url);
            }
            return url.substring(index + ".amazonaws.com/".length() + bucket.length() + 1);
        } catch (Exception e) {
            throw new IllegalArgumentException("S3 URL에서 key 추출 실패: " + url, e);
        }
    }
}

