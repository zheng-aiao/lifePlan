package com.lifeplan.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("task_category")
public class TaskCategory implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String dictKey;
    
    private String dictValue;
    
    private String dictType;
    
    private Integer sortOrder;
    
    private Integer status;
    
    private String description;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}