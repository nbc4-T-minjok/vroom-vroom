package com.sparta.vroomvroom.S3Test;

import com.sparta.vroomvroom.global.conmon.S3.S3Uploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class S3UploaderV2IntegrationTest {

    @Autowired
    private S3Uploader s3Uploader;

    @Test
    @DisplayName("단일 파일 업로드 & 삭제 실제 S3 테스트")
    void testUploadAndDelete() throws IOException {
        // given: 가짜 MultipartFile 준비
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.png",
                "image/png",
                "hello-s3".getBytes()
        );

        // when: 업로드
        String uploadedUrl = s3Uploader.upload(file, "test");
        System.out.println("Uploaded URL: " + uploadedUrl);

        // then: URL이 내가 지정한 버킷 주소를 포함해야 함
        assertThat(uploadedUrl).contains("https://");

        // 삭제 테스트
        // URL에서 key 추출 (버킷 주소 이후 부분만 key)
        String key = uploadedUrl.substring(uploadedUrl.indexOf("test/"));
        s3Uploader.delete(key);

    }

    @Test
    @DisplayName("여러 파일 업로드 테스트")
    void testMultipleUpload() throws IOException {
        // given
        MockMultipartFile file1 = new MockMultipartFile("file1", "a.png", "image/png", "data1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2", "b.png", "image/png", "data2".getBytes());

        // when
        List<String> urls = s3Uploader.uploadAll(List.of(file1, file2), "test");

        // then
        assertThat(urls).hasSize(2);
        urls.forEach(System.out::println);

        // cleanup
        for (String url : urls) {
            String key = url.substring(url.indexOf("test/"));
            s3Uploader.delete(key);
        }
    }

    @Test
    @DisplayName("파일 업데이트 테스트")
    void testUpdateFile() throws IOException {
        // given
        MockMultipartFile oldFile = new MockMultipartFile("file", "old.png", "image/png", "old".getBytes());
        MockMultipartFile newFile = new MockMultipartFile("file", "new.png", "image/png", "new".getBytes());

        // old 파일 먼저 업로드
        String oldUrl = s3Uploader.upload(oldFile, "test");
        String oldKey = oldUrl.substring(oldUrl.indexOf("test/"));

        // when: old 삭제 + new 업로드
        String newUrl = s3Uploader.update(newFile, oldKey, "test");
        System.out.println("Updated URL: " + newUrl);

        // then
        assertThat(newUrl).contains("test/");

        // cleanup
        String newKey = newUrl.substring(newUrl.indexOf("test/"));
        s3Uploader.delete(newKey);
    }
}
