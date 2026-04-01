package com.lifeplan.dto;

import lombok.Data;

import jakarta.validation.constraints.Size;

@Data
public class SubTaskUpdateDTO {
    
    @Size(max = 200, message = "子任务标题长度不能超过200个字符")
    private String title;
    
    private Integer sortOrder;
    
    private Integer status;
}
