package com.lifeplan.scheduler;

import com.lifeplan.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 任务状态定时调度器
 * 用于自动处理任务状态相关的定时任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskStatusScheduler {

    private final TaskMapper taskMapper;

    /**
     * 每天凌晨00:00:00执行
     * 将当天未完成的任务（task_status < 3）设置为未完成状态（task_status = 4）
     * 
     * 任务状态说明：
     * 0 - 待开始
     * 1 - 进行中
     * 2 - 暂停中
     * 3 - 已完成
     * 4 - 未完成
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateUncompletedTasks() {
        log.info("开始执行定时任务：更新未完成任务状态");
        
        try {
            LocalDate today = LocalDate.now();
            
            // 批量更新当天未完成的任务状态为未完成（task_status = 4）
            int updatedCount = taskMapper.batchUpdateUncompletedTasks(today);
            
            log.info("定时任务执行完成，共更新 {} 个任务状态为未完成", updatedCount);
            
        } catch (Exception e) {
            log.error("定时任务执行失败", e);
        }
    }
}
