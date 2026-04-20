package com.lifeplan.controller;

import com.lifeplan.common.Result;
import com.lifeplan.entity.TaskCategory;
import com.lifeplan.service.TaskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class TaskCategoryController {

    @Autowired
    private TaskCategoryService taskCategoryService;

    @GetMapping("/type/{dictType}")
    public Result<List<TaskCategory>> getByDictType(@PathVariable String dictType) {
        List<TaskCategory> categories = taskCategoryService.getByDictType(dictType);
        return Result.success(categories);
    }

    @GetMapping("/types")
    public Result<List<TaskCategory>> getTaskTypes() {
        List<TaskCategory> taskTypes = taskCategoryService.getTaskTypes();
        return Result.success(taskTypes);
    }
}