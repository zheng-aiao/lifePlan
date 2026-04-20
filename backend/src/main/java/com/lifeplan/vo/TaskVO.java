package com.lifeplan.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务VO类，用于返回任务基本信息，不包含子任务列表和活动日志
 */
@Data
public class TaskVO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Integer taskType;
    private Integer taskStatus;
    private String taskGroup;
    private Integer taskPriority;
    private Integer taskProgress;
    private String subTaskGroup;
    private LocalDateTime plannedStartTime;
    private LocalDateTime plannedEndTime;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private Integer actualDuration;
    private Long parentId;
    private Long userId;
    private Integer isDelayProcessed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}