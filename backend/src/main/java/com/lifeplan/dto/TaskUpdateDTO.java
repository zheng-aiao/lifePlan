package com.lifeplan.dto;

import lombok.Data;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class TaskUpdateDTO {
    
    @Size(max = 200, message = "任务标题长度不能超过200个字符")
    private String title;
    
    private String description;
    
    private String category;
    
    private Integer taskPriority;
    
    private Integer taskProgress;
    
    private LocalDateTime plannedStartTime;
    
    private LocalDateTime plannedEndTime;
    
    private Integer isDelayProcessed;
}
