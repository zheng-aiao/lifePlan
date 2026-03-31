package com.lifeplan.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sub_task")
public class SubTask implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String subTaskGroup;
    
    private String title;
    
    private Integer status;
    
    private Integer sortOrder;
    
    private LocalDateTime finishTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
