package com.lifeplan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lifeplan.entity.SubTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubTaskMapper extends BaseMapper<SubTask> {
    
    List<SubTask> selectBySubTaskGroup(@Param("subTaskGroup") String subTaskGroup);
    
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
