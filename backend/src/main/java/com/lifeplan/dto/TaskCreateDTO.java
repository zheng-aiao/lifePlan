package com.lifeplan.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class TaskCreateDTO {
    
    @NotBlank(message = "任务标题不能为空")
    @Size(max = 200, message = "任务标题长度不能超过200个字符")
    private String title;
    
    private String description;
    
    private String category;
    
    @NotNull(message = "任务类型不能为空")
    private Integer taskType;
    
    private String taskGroup;
    
    private Integer taskPriority;
    
    private String subTaskGroup;
    
    private LocalDateTime plannedStartTime;
    
    private LocalDateTime plannedEndTime;
    
    private Long parentId;
}
