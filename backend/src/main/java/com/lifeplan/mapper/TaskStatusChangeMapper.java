package com.lifeplan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lifeplan.entity.TaskStatusChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskStatusChangeMapper extends BaseMapper<TaskStatusChange> {
    
    List<TaskStatusChange> selectByTaskId(@Param("taskId") Long taskId);
}
