package com.sparta.vroomvroom.global.conmon.S3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

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
    multipartFile에서 원본 파일 이름 가져옴
    -> 파일명 중복 방지 위해 UUID 생성
    -> URL 접근 위해 공백은 _로 대체 후 UUID와 합치기 (UUID_공백제거원본파일명)
    -> dirName 폴더 하위에 저장 (dirName : S3에서 폴더 역할)
    -> S3 업로드 후 해당 URL반환
     */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        String original = multipartFile.getOriginalFilename() == null ? "file" : multipartFile.getOriginalFilename();
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

        // URL 요청 생성
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        // S3에 업로드 된 파일의 URL 반환
        URL url = s3Client.utilities().getUrl(getUrlRequest);
        return url.toString();
    }

    // 다중 파일 업로드
    public List<String> uploadAll(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            urls.add(upload(file, dirName));
        }
        return urls;
    }

    // 파일 삭제
    public void delete(String key) {
        DeleteObjectRequest delReq = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        s3Client.deleteObject(delReq);
    }

    // 파일 삭제
    public void delete(String dirName, String imageURL) {
        String key = dirName + "/" + imageURL;
        DeleteObjectRequest delReq = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        s3Client.deleteObject(delReq);
    }

    // 기존 파일 삭제 후 새 파일 업로드
    public String update(MultipartFile newFile, String oldKey, String dirName) throws IOException {
        if (oldKey != null && !oldKey.isBlank()) {
            try {
                delete(oldKey);
            } catch (Exception e) {
                log.warn("기존 파일 삭제 실패: {}", e.getMessage());
                // 필요하면 예외 재던지기
            }
        }
        return upload(newFile, dirName);
    }

    // 기존 파일 삭제 후 새 파일 업로드
    public String update(MultipartFile newFile, String newDirName, String imageURL, String oldDirName) throws IOException {
        String oldKey = oldDirName + "/" + imageURL;
        if (oldKey != null && !oldKey.isBlank()) {
            try {
                delete(oldKey);
            } catch (Exception e) {
                log.warn("기존 파일 삭제 실패: {}", e.getMessage());
                // 필요하면 예외 재던지기
            }
        }
        return upload(newFile, newDirName);
    }
}
