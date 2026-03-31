package com.lifeplan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lifeplan.dto.TaskStatusChangeDTO;
import com.lifeplan.entity.TaskStatusChange;
import com.lifeplan.mapper.TaskStatusChangeMapper;
import com.lifeplan.service.TaskStatusChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskStatusChangeServiceImpl extends ServiceImpl<TaskStatusChangeMapper, TaskStatusChange> implements TaskStatusChangeService {
    
    @Autowired
    private TaskStatusChangeMapper taskStatusChangeMapper;
    
    @Override
    @Transactional
    public TaskStatusChange createStatusChange(TaskStatusChangeDTO dto, Long userId) {
        TaskStatusChange change = new TaskStatusChange();
        BeanUtil.copyProperties(dto, change);
        change.setUserId(userId);
        change.setCreatedAt(LocalDateTime.now());
        change.setUpdatedAt(LocalDateTime.now());
        save(change);
        return change;
    }
    
    @Override
    public List<TaskStatusChange> getStatusChangesByTaskId(Long taskId) {
        return taskStatusChangeMapper.selectByTaskId(taskId);
    }
}
