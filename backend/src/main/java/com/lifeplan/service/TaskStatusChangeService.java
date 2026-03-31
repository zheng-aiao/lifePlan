package com.lifeplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lifeplan.dto.TaskStatusChangeDTO;
import com.lifeplan.entity.TaskStatusChange;

import java.util.List;

public interface TaskStatusChangeService extends IService<TaskStatusChange> {
    
    TaskStatusChange createStatusChange(TaskStatusChangeDTO dto, Long userId);
    
    List<TaskStatusChange> getStatusChangesByTaskId(Long taskId);
}
