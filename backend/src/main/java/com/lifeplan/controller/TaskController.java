package com.lifeplan.controller;

import com.lifeplan.common.Result;
import com.lifeplan.dto.TaskCreateDTO;
import com.lifeplan.dto.TaskUpdateDTO;
import com.lifeplan.entity.Task;
import com.lifeplan.service.TaskService;
import com.lifeplan.vo.TaskInfoVO;
import com.lifeplan.vo.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @PostMapping
    public Result<Map<String, Object>> createTask(@Validated @RequestBody TaskCreateDTO dto) {
        Task task = taskService.createTask(dto);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", task.getId());
        data.put("title", task.getTitle());
        data.put("task_status", task.getTaskStatus());
        data.put("created_at", task.getCreatedAt());
        return Result.success("创建成功", data);
    }


    @GetMapping("/{taskId}")
    public Result<TaskInfoVO> getTaskDetailsById(
            @PathVariable Long taskId,
            @RequestParam(required = false) Integer taskType,
            @RequestParam(required = false) Integer taskStatus,
            @RequestParam(required = false) String category) {
        Long userId = 1L;
        TaskInfoVO taskVo = taskService.getTaskDetailsById(taskId, userId);
        return Result.success(taskVo);
    }
    
    @PutMapping("/{id}")
    public Result<Map<String, Object>> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO dto) {
        Task task = taskService.updateTask(id, dto);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", task.getId());
        data.put("updated_at", task.getUpdatedAt());
        return Result.success("更新成功", data);
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return Result.success("删除成功");
    }
    
    @GetMapping("/date/{date}")
    public Result<List<TaskInfoVO>> getTasksByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(required = false) Integer taskType,
            @RequestParam(required = false) Integer taskStatus,
            @RequestParam(required = false) String category) {
        Long userId = 1L;
        List<TaskInfoVO> tasks = taskService.getTaskDetailsByDate(date, userId);
        return Result.success(tasks);
    }
    
    @GetMapping("/type/{taskType}/date/{date}")
    public Result<List<TaskVO>> getTasksByTypeAndDate(
            @PathVariable Integer taskType,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Long userId = 1L;
        List<TaskVO> tasks = taskService.getTasksByTypeAndDate(taskType, date, userId);
        return Result.success(tasks);
    }
    
    @GetMapping("/incomplete")
    public Result<List<TaskVO>> getIncompleteDailyTasks() {
        Long userId = 1L;
        List<TaskVO> tasks = taskService.getIncompleteDailyTasks(userId);
        return Result.success(tasks);
    }
    
    @PostMapping("/{id}/start")
    public Result<Map<String, Object>> startTask(@PathVariable Long id) {
        Long userId = 1L;
        Task task = taskService.startTask(id, userId);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", task.getId());
        data.put("task_status", task.getTaskStatus());
        data.put("actual_start_time", task.getActualStartTime());
        return Result.success("任务已开始", data);
    }
    
    @PostMapping("/{id}/pause")
    public Result<Map<String, Object>> pauseTask(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Long userId = 1L;
        String reason = request.get("reason");
        Task task = taskService.pauseTask(id, reason, userId);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", task.getId());
        data.put("task_status", task.getTaskStatus());
        return Result.success("任务已暂停", data);
    }
    
    @PostMapping("/{id}/resume")
    public Result<Map<String, Object>> resumeTask(@PathVariable Long id) {
        Long userId = 1L;
        Task task = taskService.resumeTask(id, userId);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", task.getId());
        data.put("task_status", task.getTaskStatus());
        return Result.success("任务已恢复", data);
    }
    
    @PostMapping("/{id}/complete")
    public Result<Map<String, Object>> completeTask(@PathVariable Long id, @RequestBody(required = false) Map<String, String> request) {
        Long userId = 1L;
        String feedback = request != null ? request.get("feedback") : null;
        Task task = taskService.completeTask(id, feedback, userId);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", task.getId());
        data.put("task_status", task.getTaskStatus());
        data.put("actual_end_time", task.getActualEndTime());
        data.put("actual_duration", task.getActualDuration());
        return Result.success("任务已完成", data);
    }
    
    @PostMapping("/{id}/abandon")
    public Result<Map<String, Object>> abandonTask(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Long userId = 1L;
        String reason = request.get("reason");
        Task task = taskService.abandonTask(id, reason, userId);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", task.getId());
        data.put("task_status", task.getTaskStatus());
        return Result.success("任务已放弃", data);
    }
    
    @PostMapping("/{id}/delay")
    public Result<Map<String, Object>> delayTask(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Long userId = 1L;
        String reason = request.get("reason");
        Task task = taskService.delayTask(id, reason, userId);
        Map<String, Object> data = new HashMap<>();
        data.put("task_id", task.getId());
        data.put("task_status", task.getTaskStatus());
        return Result.success("任务已延时", data);
    }
}
