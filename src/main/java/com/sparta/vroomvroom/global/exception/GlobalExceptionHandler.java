package com.sparta.vroomvroom.global.exception;

import com.sparta.vroomvroom.global.conmon.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Illegal (직접 던진 예외)
    @ExceptionHandler({IllegalArgumentException.class})
    public BaseResponse handleException(IllegalArgumentException ex) {
        return new BaseResponse(ex.getMessage());
    }

    // 나머지 예외
    @ExceptionHandler(Exception.class)
    public BaseResponse handleAllExceptions(Exception ex) {
        return new BaseResponse("요청이 실패했습니다. 서버 에러가 발생했습니다.");
    }
}
