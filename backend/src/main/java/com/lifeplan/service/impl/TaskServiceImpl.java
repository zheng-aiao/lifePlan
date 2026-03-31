package com.lifeplan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lifeplan.common.BusinessException;
import com.lifeplan.dto.TaskCreateDTO;
import com.lifeplan.dto.TaskUpdateDTO;
import com.lifeplan.entity.Task;
import com.lifeplan.entity.TaskStatusChange;
import com.lifeplan.mapper.TaskMapper;
import com.lifeplan.mapper.TaskStatusChangeMapper;
import com.lifeplan.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private TaskStatusChangeMapper taskStatusChangeMapper;
    
    @Override
    @Transactional
    public Task createTask(TaskCreateDTO dto) {
        Task task = new Task();
        BeanUtil.copyProperties(dto, task);
        task.setTaskStatus(0);
        task.setTaskProgress(0);
        task.setIsDeleted(0);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        if (task.getSubTaskGroup() == null || task.getSubTaskGroup().isEmpty()) {
            task.setSubTaskGroup(IdUtil.simpleUUID());
        }
        
        save(task);
        return task;
    }
    
    @Override
    @Transactional
    public Task updateTask(Long id, TaskUpdateDTO dto) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        
        if (dto.getTitle() != null) task.setTitle(dto.getTitle());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        if (dto.getCategory() != null) task.setCategory(dto.getCategory());
        if (dto.getTaskPriority() != null) task.setTaskPriority(dto.getTaskPriority());
        if (dto.getTaskProgress() != null) task.setTaskProgress(dto.getTaskProgress());
        if (dto.getPlannedStartTime() != null) task.setPlannedStartTime(dto.getPlannedStartTime());
        if (dto.getPlannedEndTime() != null) task.setPlannedEndTime(dto.getPlannedEndTime());
        
        task.setUpdatedAt(LocalDateTime.now());
        updateById(task);
        return task;
    }
    
    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        removeById(id);
    }
    
    @Override
    public Task getTaskById(Long id) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        return task;
    }
    
    @Override
    public List<Task> getTasksByDate(LocalDate date, Long userId) {
        return taskMapper.selectByDate(date, userId);
    }
    
    @Override
    @Transactional
    public Task startTask(Long id, Long userId) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        
        task.setTaskStatus(1);
        task.setActualStartTime(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        updateById(task);
        
        createStatusChange(id, 1, "开始任务", userId);
        return task;
    }
    
    @Override
    @Transactional
    public Task pauseTask(Long id, String reason, Long userId) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        
        task.setTaskStatus(2);
        task.setUpdatedAt(LocalDateTime.now());
        updateById(task);
        
        createStatusChange(id, 2, reason, userId);
        return task;
    }
    
    @Override
    @Transactional
    public Task resumeTask(Long id, Long userId) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        
        task.setTaskStatus(1);
        task.setUpdatedAt(LocalDateTime.now());
        updateById(task);
        
        createStatusChange(id, 3, "恢复任务", userId);
        return task;
    }
    
    @Override
    @Transactional
    public Task completeTask(Long id, String feedback, Long userId) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        
        task.setTaskStatus(3);
        task.setActualEndTime(LocalDateTime.now());
        task.setTaskProgress(100);
        task.setUpdatedAt(LocalDateTime.now());
        
        if (task.getActualStartTime() != null) {
            long duration = java.time.Duration.between(task.getActualStartTime(), task.getActualEndTime()).toMinutes();
            task.setActualDuration((int) duration);
        }
        
        updateById(task);
        createStatusChange(id, 4, feedback, userId);
        return task;
    }
    
    @Override
    @Transactional
    public Task abandonTask(Long id, String reason, Long userId) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        
        task.setTaskStatus(4);
        task.setUpdatedAt(LocalDateTime.now());
        updateById(task);
        
        createStatusChange(id, 5, reason, userId);
        return task;
    }
    
    @Override
    @Transactional
    public Task delayTask(Long id, String reason, Long userId) {
        Task task = getById(id);
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        
        task.setTaskStatus(4);
        task.setUpdatedAt(LocalDateTime.now());
        updateById(task);
        
        createStatusChange(id, 6, reason, userId);
        return task;
    }
    
    private void createStatusChange(Long taskId, Integer changeType, String feedbackContent, Long userId) {
        TaskStatusChange change = new TaskStatusChange();
        change.setTaskId(taskId);
        change.setChangeType(changeType);
        change.setFeedbackContent(feedbackContent);
        change.setUserId(userId);
        change.setCreatedAt(LocalDateTime.now());
        change.setUpdatedAt(LocalDateTime.now());
        taskStatusChangeMapper.insert(change);
    }
}
