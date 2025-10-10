package com.sparta.vroomvroom.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "response")
@Component
public class ResponseUtil {

    public void sendErrorResponse(HttpServletResponse response, String message, int responseStatus) {
        try {
            response.setStatus(responseStatus);
            response.setContentType("application/json;charset=UTF-8");

            BaseResponse baseResponse = new BaseResponse<>(message);


            String json = new ObjectMapper().writeValueAsString(baseResponse);

            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (IOException ioException) {
            log.error("응답 전송 실패", ioException);
        }
    }

    public void sendSuccessResponse(HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");

            //기본 = 성공 응답
            BaseResponse baseResponse = new BaseResponse();

            String json = new ObjectMapper().writeValueAsString(baseResponse);

            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (IOException ioException) {
            log.error("응답 전송 실패", ioException);
        }
    }
}
