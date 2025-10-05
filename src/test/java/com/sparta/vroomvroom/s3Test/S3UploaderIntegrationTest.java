package com.sparta.vroomvroom.s3Test;

import com.sparta.vroomvroom.global.conmon.s3.S3Uploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class S3UploaderIntegrationTest {

    @Autowired
    private S3Uploader s3Uploader;

    private final String dirName = "test";

    @Test
    @DisplayName("단일 파일 업로드 → 조회 → 삭제 전체 테스트")
    void testUploadAndGetAndDeleteFile() throws IOException {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.png",
                "image/png",
                "hello-s3".getBytes()
        );

        // when: 업로드
        String key = s3Uploader.upload(file, dirName);
        System.out.println("업로드된 Key: " + key);

        // then: URL 조회
        String url = s3Uploader.getFileUrl(key);
        System.out.println("조회된 URL: " + url);
        assertThat(url).contains("https://");

        // finally: 삭제
        s3Uploader.delete(key);
        System.out.println("삭제 완료: " + key);
    }

    @Test
    @DisplayName("다중 파일 업로드 → 조회 → 삭제 전체 테스트")
    void testUploadAndDeleteMultipleFiles() throws IOException {
        // given
        MockMultipartFile file1 = new MockMultipartFile("file1", "a.png", "image/png", "data1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2", "b.png", "image/png", "data2".getBytes());

        // when
        List<String> keys = s3Uploader.uploadFiles(List.of(file1, file2), dirName);
        System.out.println("업로드된 Keys: " + keys);

        // then: URL 확인
        List<String> urls = s3Uploader.getFileUrls(keys);
        urls.forEach(url -> System.out.println("조회된 URL: " + url));
        assertThat(urls).hasSize(2);

        // finally: 삭제
        for (String k : keys) {
            s3Uploader.delete(k);
            System.out.println("삭제 완료: " + k);
        }
    }

    @Test
    @DisplayName("업데이트 테스트 (기존 파일 교체)")
    void testUpdateFile() throws IOException {
        // given: 기존 파일 업로드
        MockMultipartFile oldFile = new MockMultipartFile(
                "file",
                "old.png",
                "image/png",
                "old-content".getBytes()
        );
        String oldKey = s3Uploader.upload(oldFile, dirName);
        System.out.println("기존 파일 업로드: " + oldKey);

        MockMultipartFile newFile = new MockMultipartFile(
                "file",
                "new.png",
                "image/png",
                "new-content".getBytes()
        );

        // when: 업데이트 (기존 삭제 + 새 업로드)
        String newKey = s3Uploader.update(newFile, oldKey, dirName);
        System.out.println("새 파일 업로드: " + newKey);

        // then: URL 확인
        String newUrl = s3Uploader.getFileUrl(newKey);
        System.out.println("새 URL: " + newUrl);
        assertThat(newUrl).contains("https://");

        // finally: 새 파일 삭제
        s3Uploader.delete(newKey);
    }
}
