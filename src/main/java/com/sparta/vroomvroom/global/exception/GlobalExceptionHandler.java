package com.sparta.vroomvroom.global.exception;

import com.sparta.vroomvroom.global.conmon.BaseResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.stream.Collectors;

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

    // 지원하지 않는 HTTP 메소드
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return new BaseResponse("지원하지 않는 HTTP 메소드입니다. " + ex.getMethod());
    }

    // 데이터 무결성 위반
    @ExceptionHandler(DataIntegrityViolationException.class)
    public BaseResponse handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return new BaseResponse("데이터 무결성 오류가 발생했습니다. 중복되거나 잘못된 데이터입니다.");
    }

    // SQL 예외
    @ExceptionHandler(SQLException.class)
    public BaseResponse handleSQLException(SQLException ex) {
        log.error("SQL 오류 발생", ex);
        return new BaseResponse( "데이터베이스 오류가 발생했습니다.");
    }

    //필수 파라미터 예외
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse handleMissingParameter(MissingServletRequestParameterException ex) {
        return new BaseResponse("필수 파라미터가 누락되었습니다: " + ex.getParameterName());
    }

    // @Valid 실패 시
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining(", "));
        return new BaseResponse("입력값 검증 실패: " + errorMessage);
    }

    // 타입 불일치
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return new BaseResponse("파라미터 타입이 올바르지 않습니다: " + ex.getName());
    }

    // 나머지 예외
    @ExceptionHandler(Exception.class)
    public BaseResponse handleAllExceptions(Exception ex) {

        log.error("요청이 실패했습니다. : {}", ex.getMessage(), ex);
        return new BaseResponse("요청이 실패했습니다. 잠시 후 다시 실행해주세요.");
    }
}
