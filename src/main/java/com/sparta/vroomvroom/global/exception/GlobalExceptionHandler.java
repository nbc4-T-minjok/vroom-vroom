package com.sparta.vroomvroom.global.exception;

import com.sparta.vroomvroom.global.conmon.BaseResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Illegal (직접 던진 예외)
    @ExceptionHandler({IllegalArgumentException.class})
    public BaseResponse handleException(IllegalArgumentException ex) {
        return new BaseResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage(); // 첫 번째 에러 메시지 추출
        return new BaseResponse(errorMessage);
    }

    //권한 없음 예외
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse handleAllExceptions(AccessDeniedException ex) {
        return new BaseResponse("요청이 실패했습니다. 권한이 없습니다.");
    }

    // 나머지 예외
    @ExceptionHandler(Exception.class)
    public BaseResponse handleAllExceptions(Exception ex) {
        return new BaseResponse("요청이 실패했습니다. 서버 에러가 발생했습니다.");
    }
}
