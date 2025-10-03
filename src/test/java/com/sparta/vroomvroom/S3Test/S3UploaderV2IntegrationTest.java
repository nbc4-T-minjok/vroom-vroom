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

    private final String dirName = "test";

    @Test
    @DisplayName("단일 파일 업로드 테스트 (삭제하지 않음)")
    void testUploadFile() throws IOException {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.png",
                "image/png",
                "hello-s3".getBytes()
        );

        // when
        String key = s3Uploader.upload(file, dirName);

        // then
        System.out.println("업로드된 Key: " + key);
        String url = s3Uploader.getFileUrl(key);
        System.out.println("업로드된 URL: " + url);

        assertThat(url).contains("https://");
    }

    @Test
    @DisplayName("다중 파일 업로드 테스트 (삭제하지 않음)")
    void testUploadMultipleFiles() throws IOException {
        // given
        MockMultipartFile file1 = new MockMultipartFile("file1", "a.png", "image/png", "data1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2", "b.png", "image/png", "data2".getBytes());

        // when
        List<String> keys = s3Uploader.uploadFiles(List.of(file1, file2), dirName);

        // then
        System.out.println("업로드된 Keys: " + keys);
        List<String> urls = s3Uploader.getFileUrls(keys);
        urls.forEach(url -> System.out.println("업로드된 URL: " + url));

        assertThat(urls).hasSize(2);
    }

    @Test
    @DisplayName("URL 조회 테스트")
    void testGetFileUrl() {
        // given: 업로드되어 있는 파일 key 직접 입력 (콘솔에서 확인한 key)
        String key = "test/{S3에 업로드 된 파일명}";

        // when
        String url = s3Uploader.getFileUrl(key);

        // then
        System.out.println("조회된 URL: " + url);
        assertThat(url).contains("https://");
    }

    @Test
    @DisplayName("파일 삭제 테스트")
    void testDeleteFile() {
        // given: 삭제할 파일 key 직접 입력 (업로드 후 콘솔에서 확인한 key)
        String key = "test/{S3에 업로드 된 파일명}";

        // when
        s3Uploader.delete(key);

        // then
        System.out.println("삭제 요청 완료: " + key);
        // 삭제는 콘솔에서 직접 확인
    }

    @Test
    @DisplayName("파일 업데이트 테스트 (기존 삭제 후 새 업로드)")
    void testUpdateFile() throws IOException {
        // given
        MockMultipartFile newFile = new MockMultipartFile(
                "file",
                "updated.png",
                "image/png",
                "new-content".getBytes()
        );
        String oldKey = "test/{S3에 업로드 된 파일명}"; // 실제 기존 key 넣기

        // when
        String newKey = s3Uploader.update(newFile, oldKey, dirName);

        // then
        String newUrl = s3Uploader.getFileUrl(newKey);
        System.out.println("새 파일 Key: " + newKey);
        System.out.println("새 파일 URL: " + newUrl);

        assertThat(newUrl).contains("https://");
    }
}
