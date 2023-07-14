package com.vabiss.okrbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponseDto {

    private String success;
    private Object object;

    public static SuccessResponseDto of(String success) {
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setSuccess(success);
        return successResponseDto;
    }

    public static SuccessResponseDto of(String success, Object object) {
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setSuccess(success);
        successResponseDto.setObject(object);
        return successResponseDto;
    }

}
