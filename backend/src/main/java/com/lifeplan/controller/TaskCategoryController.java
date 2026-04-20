package com.lifeplan.controller;

import com.lifeplan.entity.TaskCategory;
import com.lifeplan.service.TaskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-category")
public class TaskCategoryController {
    
    @Autowired
    private TaskCategoryService taskCategoryService;
    
    /**
     * 根据类型查询类别列表
     */
    @GetMapping("/type/{dictType}")
    public ResponseEntity<List<TaskCategory>> getByDictType(@PathVariable String dictType) {
        List<TaskCategory> categories = taskCategoryService.getByDictType(dictType);
        return ResponseEntity.ok(categories);
    }
    
    /**
     * 获取任务类型列表
     */
    @GetMapping("/task-types")
    public ResponseEntity<List<TaskCategory>> getTaskTypes() {
        List<TaskCategory> taskTypes = taskCategoryService.getTaskTypes();
        return ResponseEntity.ok(taskTypes);
    }
}