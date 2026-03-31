package com.lifeplan.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskQueryDTO {
    
    private Integer taskType;
    
    private Integer taskStatus;
    
    private String category;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private Integer page = 1;
    
    private Integer pageSize = 20;
}
