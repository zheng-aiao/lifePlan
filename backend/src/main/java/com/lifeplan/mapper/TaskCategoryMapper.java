package com.lifeplan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lifeplan.entity.TaskCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskCategoryMapper extends BaseMapper<TaskCategory> {
    
    /**
     * 根据类型查询类别列表
     */
    List<TaskCategory> selectByDictType(@Param("dictType") String dictType);
    
    /**
     * 根据类型和键查询类别
     */
    TaskCategory selectByTypeAndKey(@Param("dictType") String dictType, @Param("dictKey") String dictKey);
}