package com.lifeplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lifeplan.dto.SubTaskCreateDTO;
import com.lifeplan.dto.SubTaskUpdateDTO;
import com.lifeplan.entity.SubTask;

import java.util.List;

public interface SubTaskService extends IService<SubTask> {
    
    SubTask createSubTask(SubTaskCreateDTO dto);
    
    void deleteSubTask(Long id);
    
    SubTask updateSubTask(Long id, SubTaskUpdateDTO dto);
    
    List<SubTask> getSubTasksByGroup(String subTaskGroup);
    
    SubTask completeSubTask(Long id);
    
    SubTask uncompleteSubTask(Long id);
}
