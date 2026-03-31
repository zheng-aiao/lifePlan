package com.lifeplan.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class SubTaskCreateDTO {
    
    @NotBlank(message = "子任务组别不能为空")
    private String subTaskGroup;
    
    @NotBlank(message = "子任务标题不能为空")
    @Size(max = 200, message = "子任务标题长度不能超过200个字符")
    private String title;
    
    private Integer sortOrder;
}
