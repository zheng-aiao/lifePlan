package com.lifeplan.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务信息 VO（包含任务信息 + 子任务列表 + 活动日志列表）
 */
@Data
public class TaskInfoVO implements Serializable {
    
    // ========== 任务基本信息 ==========
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
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // ========== 子任务列表 ==========
    private List<SubTaskInfo> subTasks;
    
    // ========== 活动日志列表 ==========
    private List<ActivityLog> activities;
    
    /**
     * 子任务信息
     */
    @Data
    public static class SubTaskInfo implements Serializable {
        
        private Long id;
        
        private String subTaskGroup;
        
        private String title;
        
        private Integer status;
        
        private Integer sortOrder;
        
        private LocalDateTime finishTime;
        
        private Long taskId;
        
        private LocalDateTime createdAt;
        
        private LocalDateTime updatedAt;
    }
    
    /**
     * 活动日志信息
     */
    @Data
    public static class ActivityLog implements Serializable {
        
        private Long id;
        
        private Long taskId;
        
        private Integer changeType;
        
        private String feedbackContent;
        
        private Integer duration;
        
        private Long userId;
        
        private LocalDateTime createdAt;
        
        private LocalDateTime updatedAt;
    }
}
