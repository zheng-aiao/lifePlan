package com.lifeplan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedEndTime;

    private Long parentId;

    private Long userId;

    private Integer isDelayProcessed;
}
