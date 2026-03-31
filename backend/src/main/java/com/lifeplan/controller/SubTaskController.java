package com.lifeplan.controller;

import com.lifeplan.common.Result;
import com.lifeplan.dto.SubTaskCreateDTO;
import com.lifeplan.dto.SubTaskUpdateDTO;
import com.lifeplan.entity.SubTask;
import com.lifeplan.service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sub-tasks")
public class SubTaskController {
    
    @Autowired
    private SubTaskService subTaskService;
    
    @PostMapping
    public Result<SubTask> createSubTask(@Validated @RequestBody SubTaskCreateDTO dto) {
        SubTask subTask = subTaskService.createSubTask(dto);
        return Result.success("创建成功", subTask);
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteSubTask(@PathVariable Long id) {
        subTaskService.deleteSubTask(id);
        return Result.success("删除成功");
    }
    
    @PutMapping("/{id}")
    public Result<SubTask> updateSubTask(@PathVariable Long id, @RequestBody SubTaskUpdateDTO dto) {
        SubTask subTask = subTaskService.updateSubTask(id, dto);
        return Result.success("更新成功", subTask);
    }
    
    @GetMapping
    public Result<Map<String, Object>> getSubTasksByGroup(@RequestParam String subTaskGroup) {
        List<SubTask> subTasks = subTaskService.getSubTasksByGroup(subTaskGroup);
        Map<String, Object> data = new HashMap<>();
        data.put("sub_task_group", subTaskGroup);
        data.put("total", subTasks.size());
        data.put("sub_tasks", subTasks);
        return Result.success(data);
    }
    
    @PostMapping("/{id}/complete")
    public Result<SubTask> completeSubTask(@PathVariable Long id) {
        SubTask subTask = subTaskService.completeSubTask(id);
        return Result.success("子任务已完成", subTask);
    }
    
    @PostMapping("/{id}/uncomplete")
    public Result<SubTask> uncompleteSubTask(@PathVariable Long id) {
        SubTask subTask = subTaskService.uncompleteSubTask(id);
        return Result.success("已取消完成状态", subTask);
    }
}
