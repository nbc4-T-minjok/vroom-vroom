package com.sparta.vroomvroom.global.conmon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.sparta.vroomvroom.global.conmon.constants.BaseResponseStatus.FAIL;
import static com.sparta.vroomvroom.global.conmon.constants.BaseResponseStatus.SUCCESS;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "message", "result"})
public final class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    /*

    1. 성공 (응답 데이터는 없음)
        new BaseResponse(); -> 기본 세팅이 isSuccess = true, message = 요청이 성공했습니다.
    2. 성공 (응답 데이터 있음)
        new BaseResponse(result); 기본 세팅이 isSuccess = true, message = 요청이 성공했습니다., result = {}
    3. 실패
        예외 나는 부분에서 new Illega("메시지")

     */


    // 요청에 성공한 경우
    // new BaseResponse(result)로 호출
    public BaseResponse(T result) {
        this.isSuccess = true;
        this.message = "요청에 성공했습니다";
        this.result = result;
    }

    //new BaseResponse()로 호출
    public BaseResponse() {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
    }

    // 요청에 실패한 경우
    //new BaseResponse(errorMessage)로 호출
    public BaseResponse(String errorMessage) {
        this.isSuccess = FAIL.isSuccess();
        this.message = errorMessage;
    }
}
