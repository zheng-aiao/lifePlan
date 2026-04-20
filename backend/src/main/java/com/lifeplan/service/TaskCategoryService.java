package com.lifeplan.service;

import com.lifeplan.entity.TaskCategory;

import java.util.List;

public interface TaskCategoryService {
    
    /**
     * 根据类型查询类别列表
     */
    List<TaskCategory> getByDictType(String dictType);
    
    /**
     * 根据类型和键查询类别
     */
    TaskCategory getByTypeAndKey(String dictType, String dictKey);
    
    /**
     * 获取任务类型列表
     */
    List<TaskCategory> getTaskTypes();
}