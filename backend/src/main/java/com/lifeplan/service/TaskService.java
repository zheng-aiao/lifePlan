package com.lifeplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lifeplan.dto.TaskCreateDTO;
import com.lifeplan.dto.TaskUpdateDTO;
import com.lifeplan.entity.Task;
import com.lifeplan.vo.TaskInfoVO;
import com.lifeplan.vo.TaskVO;

import java.time.LocalDate;
import java.util.List;

public interface TaskService extends IService<Task> {
    
    Task createTask(TaskCreateDTO dto);
    
    Task updateTask(Long id, TaskUpdateDTO dto);
    
    void deleteTask(Long id);

    TaskInfoVO getTaskDetailsById(Long taskId, Long userId);

    List<TaskInfoVO> getTaskDetailsByDate(LocalDate date, Long userId);
    
    /**
     * 查询指定类型且日期在计划时间范围内的任务，按优先级排序
     * @param taskType 任务类型
     * @param date 查询日期
     * @param userId 用户ID
     * @return 任务列表
     */
    List<TaskVO> getTasksByTypeAndDate(Integer taskType, LocalDate date, Long userId);
    
    /**
     * 查询状态为未完成的日任务（taskType=3, taskStatus=4）
     * @param userId 用户ID
     * @return 任务列表
     */
    List<TaskVO> getIncompleteDailyTasks(Long userId);
    
    Task startTask(Long id, Long userId);
    
    Task pauseTask(Long id, String reason, Long userId);
    
    Task resumeTask(Long id, Long userId);
    
    Task completeTask(Long id, String feedback, Long userId);
    
    Task abandonTask(Long id, String reason, Long userId);
    
    Task delayTask(Long id, String reason, Long userId);

}
