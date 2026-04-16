package com.lifeplan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lifeplan.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    
    List<Task> selectByDate(@Param("date") LocalDate date, @Param("userId") Long userId);
    
    List<Task> selectByTypeAndStatus(@Param("taskType") Integer taskType, 
                                     @Param("taskStatus") Integer taskStatus,
                                     @Param("userId") Long userId);
    
    List<Task> selectByTypeAndDateRange(@Param("taskType") Integer taskType, 
                                        @Param("date") LocalDate date,
                                        @Param("userId") Long userId);
    
    int updateTaskStatus(@Param("id") Long id, @Param("taskStatus") Integer taskStatus);
    
    /**
     * 批量更新当天未完成任务的状态为未完成（task_status = 4）
     */
    int batchUpdateUncompletedTasks(@Param("today") LocalDate today);
    
    /**
     * 查询状态为未完成的日任务（taskType=3, taskStatus=4）
     */
    List<Task> selectIncompleteDailyTasks(@Param("userId") Long userId);
}
