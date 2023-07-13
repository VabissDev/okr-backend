package com.vabiss.okrbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceDto {

    private int id;
    private String name;
    private String owner;
    private String description;
    private String level;
    private String status;

}
