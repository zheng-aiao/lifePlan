package com.lifeplan.controller;

import com.lifeplan.common.Result;
import com.lifeplan.dto.TaskStatusChangeDTO;
import com.lifeplan.entity.TaskStatusChange;
import com.lifeplan.service.TaskStatusChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task-changes")
public class TaskStatusChangeController {
    
    @Autowired
    private TaskStatusChangeService taskStatusChangeService;
    
    @GetMapping("/{taskId}")
    public Result<Map<String, Object>> getStatusChangesByTaskId(@PathVariable Long taskId) {
        List<TaskStatusChange> changes = taskStatusChangeService.getStatusChangesByTaskId(taskId);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", taskId);
        data.put("timeline", changes);
        return Result.success(data);
    }
    
    @PostMapping
    public Result<TaskStatusChange> createStatusChange(@Validated @RequestBody TaskStatusChangeDTO dto) {
        Long userId = 1L;
        TaskStatusChange change = taskStatusChangeService.createStatusChange(dto, userId);
        return Result.success("创建成功", change);
    }
}
