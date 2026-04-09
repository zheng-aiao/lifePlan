package com.lifeplan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lifeplan.common.BusinessException;
import com.lifeplan.dto.SubTaskCreateDTO;
import com.lifeplan.dto.SubTaskUpdateDTO;
import com.lifeplan.entity.SubTask;
import com.lifeplan.mapper.SubTaskMapper;
import com.lifeplan.service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubTaskServiceImpl extends ServiceImpl<SubTaskMapper, SubTask> implements SubTaskService {
    
    @Autowired
    private SubTaskMapper subTaskMapper;
    
    @Override
    @Transactional
    public SubTask createSubTask(SubTaskCreateDTO dto) {
        SubTask subTask = new SubTask();
        BeanUtil.copyProperties(dto, subTask);
        subTask.setStatus(0);
        subTask.setCreatedAt(LocalDateTime.now());
        subTask.setUpdatedAt(LocalDateTime.now());
        
        if (subTask.getSortOrder() == null) {
            subTask.setSortOrder(0);
        }
        
        save(subTask);
        return subTask;
    }
    
    @Override
    @Transactional
    public void deleteSubTask(Long id) {
        SubTask subTask = getById(id);
        if (subTask == null) {
            throw new BusinessException("子任务不存在");
        }
        removeById(id);
    }
    
    @Override
    @Transactional
    public SubTask updateSubTask(Long id, SubTaskUpdateDTO dto) {
        SubTask subTask = getById(id);
        if (subTask == null) {
            throw new BusinessException("子任务不存在");
        }
        
        if (dto.getTitle() != null) subTask.setTitle(dto.getTitle());
        if (dto.getPriority() != null) subTask.setPriority(dto.getPriority());
        if (dto.getSortOrder() != null) subTask.setSortOrder(dto.getSortOrder());
        if (dto.getStatus() != null) subTask.setStatus(dto.getStatus());
        
        subTask.setUpdatedAt(LocalDateTime.now());
        updateById(subTask);
        return subTask;
    }
    
    @Override
    public List<SubTask> getSubTasksByGroup(String subTaskGroup) {
        return subTaskMapper.selectBySubTaskGroup(subTaskGroup);
    }
    
    @Override
    @Transactional
    public SubTask completeSubTask(Long id) {
        SubTask subTask = getById(id);
        if (subTask == null) {
            throw new BusinessException("子任务不存在");
        }
        
        subTask.setStatus(1);
        subTask.setFinishTime(LocalDateTime.now());
        subTask.setUpdatedAt(LocalDateTime.now());
        updateById(subTask);
        return subTask;
    }
    
    @Override
    @Transactional
    public SubTask uncompleteSubTask(Long id) {
        SubTask subTask = getById(id);
        if (subTask == null) {
            throw new BusinessException("子任务不存在");
        }
        
        subTask.setStatus(0);
        subTask.setFinishTime(null);
        subTask.setUpdatedAt(LocalDateTime.now());
        updateById(subTask);
        return subTask;
    }
}
