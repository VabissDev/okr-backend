package com.vabiss.okrbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFormDto {

    private Integer id;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    private String role;

}
