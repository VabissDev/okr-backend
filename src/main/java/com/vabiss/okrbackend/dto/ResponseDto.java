package com.vabiss.okrbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String successMsg;
    private String errorMsg;
    private String errorCode;
    private Object object;

    public static ResponseDto of(String successMsg) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccessMsg(successMsg);
        return responseDto;
    }

    public static ResponseDto of(String successMsg, Object object) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccessMsg(successMsg);
        responseDto.setObject(object);
        return responseDto;
    }

    public static ResponseDto of(String errorCode, String errorMsg) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setErrorCode(errorCode);
        responseDto.setErrorMsg(errorMsg);
        return responseDto;
    }

}
