package com.lifeplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lifeplan.dto.TaskCreateDTO;
import com.lifeplan.dto.TaskUpdateDTO;
import com.lifeplan.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService extends IService<Task> {
    
    Task createTask(TaskCreateDTO dto);
    
    Task updateTask(Long id, TaskUpdateDTO dto);
    
    void deleteTask(Long id);
    
    Task getTaskById(Long id);
    
    List<Task> getTasksByDate(LocalDate date, Long userId);
    
    Task startTask(Long id, Long userId);
    
    Task pauseTask(Long id, String reason, Long userId);
    
    Task resumeTask(Long id, Long userId);
    
    Task completeTask(Long id, String feedback, Long userId);
    
    Task abandonTask(Long id, String reason, Long userId);
    
    Task delayTask(Long id, String reason, Long userId);
}
