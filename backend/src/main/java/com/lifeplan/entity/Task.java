package com.lifeplan.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("task")
public class Task implements Serializable {
    
    @TableId(type = IdType.AUTO)
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
    
    @TableLogic
    private Integer isDeleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
