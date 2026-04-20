package com.lifeplan.service.impl;

import com.lifeplan.entity.TaskCategory;
import com.lifeplan.mapper.TaskCategoryMapper;
import com.lifeplan.service.TaskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCategoryServiceImpl implements TaskCategoryService {
    
    @Autowired
    private TaskCategoryMapper taskCategoryMapper;
    
    @Override
    public List<TaskCategory> getByDictType(String dictType) {
        return taskCategoryMapper.selectByDictType(dictType);
    }
    
    @Override
    public TaskCategory getByTypeAndKey(String dictType, String dictKey) {
        return taskCategoryMapper.selectByTypeAndKey(dictType, dictKey);
    }
    
    @Override
    public List<TaskCategory> getTaskTypes() {
        return taskCategoryMapper.selectByDictType("task_type");
    }
}