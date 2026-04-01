package com.lifeplan.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class TaskStatusChangeDTO {
    
    @NotNull(message = "任务ID不能为空")
    private Long taskId;
    
    @NotNull(message = "变更类型不能为空")
    private Integer changeType;
    
    private String feedbackContent;
}
