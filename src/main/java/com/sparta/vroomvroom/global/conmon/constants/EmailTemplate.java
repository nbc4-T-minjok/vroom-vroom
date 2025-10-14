package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplate {
    EMAIL_VERIFY_REQUEST(
            "VroomVroom 이메일 인증 안내",
            """
            <h2>이메일 인증 안내</h2>
            <p>아래 링크를 클릭하여 이메일 인증을 완료해주세요.</p>
            <a href="%s" target="_blank">이메일 인증하기</a>
            <p>유효시간: %d분</p>
            """
    );

    private final String subject;
    private final String content;

    public String buildContent(Object... args){
        return content.formatted(args);
    }
}
