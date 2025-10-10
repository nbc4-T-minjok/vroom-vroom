package com.sparta.vroomvroom.global.exception;

import com.sparta.vroomvroom.global.conmon.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //Illegal (직접 던진 예외)
    @ExceptionHandler({IllegalArgumentException.class})
    public BaseResponse handleException(IllegalArgumentException ex) {
        return new BaseResponse("요청이 실패했습니다. " + ex.getMessage());
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
    @ExceptionHandler(EntityNotFoundException.class)
    public BaseResponse handleAllExceptions(EntityNotFoundException ex) {
        return new BaseResponse("요청이 실패했습니다. " + ex.getMessage());
    }

    //권한 없음 예외
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse handleAllExceptions(AccessDeniedException ex) {
        return new BaseResponse("요청이 실패했습니다. 권한이 없습니다.");
    }

    // 나머지 예외
    @ExceptionHandler(Exception.class)
    public BaseResponse handleAllExceptions(Exception ex) {

        log.error("요청이 실패했습니다. : {}", ex.getMessage(), ex);
        return new BaseResponse("요청이 실패했습니다. 잠시 후 다시 실행해주세요.");
    }
}
