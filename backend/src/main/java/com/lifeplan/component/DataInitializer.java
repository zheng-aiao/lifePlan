package com.lifeplan.component;

import com.lifeplan.entity.TaskCategory;
import com.lifeplan.mapper.TaskCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private TaskCategoryMapper taskCategoryMapper;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void run(String... args) throws Exception {
        // 首先确保表存在
        ensureTableExists();
        
        // 检查是否已存在任务类型数据
        List<TaskCategory> existingTypes = taskCategoryMapper.selectByDictType("task_type");
        
        if (existingTypes == null || existingTypes.isEmpty()) {
            // 插入任务类型初始化数据
            TaskCategory yearTask = new TaskCategory();
            yearTask.setDictKey("年任务");
            yearTask.setDictValue("1");
            yearTask.setDictType("task_type");
            yearTask.setSortOrder(1);
            yearTask.setStatus(1);
            yearTask.setDescription("年度任务");
            taskCategoryMapper.insert(yearTask);

            TaskCategory quarterTask = new TaskCategory();
            quarterTask.setDictKey("季度任务");
            quarterTask.setDictValue("2");
            quarterTask.setDictType("task_type");
            quarterTask.setSortOrder(2);
            quarterTask.setStatus(1);
            quarterTask.setDescription("季度任务");
            taskCategoryMapper.insert(quarterTask);

            TaskCategory monthTask = new TaskCategory();
            monthTask.setDictKey("月任务");
            monthTask.setDictValue("3");
            monthTask.setDictType("task_type");
            monthTask.setSortOrder(3);
            monthTask.setStatus(1);
            monthTask.setDescription("月度任务");
            taskCategoryMapper.insert(monthTask);

            TaskCategory weekTask = new TaskCategory();
            weekTask.setDictKey("周任务");
            weekTask.setDictValue("4");
            weekTask.setDictType("task_type");
            weekTask.setSortOrder(4);
            weekTask.setStatus(1);
            weekTask.setDescription("周任务");
            taskCategoryMapper.insert(weekTask);

            TaskCategory incompleteTask = new TaskCategory();
            incompleteTask.setDictKey("未完成日任务");
            incompleteTask.setDictValue("5");
            incompleteTask.setDictType("task_type");
            incompleteTask.setSortOrder(5);
            incompleteTask.setStatus(1);
            incompleteTask.setDescription("未完成的日任务");
            taskCategoryMapper.insert(incompleteTask);
            
            System.out.println("========== 任务类别初始化数据已插入 ==========");
        } else {
            System.out.println("========== 任务类别数据已存在，跳过初始化 ==========");
        }
    }
    
    private void ensureTableExists() {
        try {
            String createTableSql = """
                CREATE TABLE IF NOT EXISTS task_category (
                    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    dict_key VARCHAR(50) NOT NULL,
                    dict_value VARCHAR(100) NOT NULL,
                    dict_type VARCHAR(50) NOT NULL,
                    sort_order INT DEFAULT 0,
                    status SMALLINT DEFAULT 1,
                    description VARCHAR(500),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            jdbcTemplate.execute(createTableSql);
            
            // 创建索引
            jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_dict_type ON task_category(dict_type)");
            jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_dict_key ON task_category(dict_key)");
            
            // 创建触发器函数（如果不存在）
            String createFunctionSql = """
                CREATE OR REPLACE FUNCTION update_modified_column() 
                RETURNS TRIGGER AS $$
                BEGIN
                    NEW.updated_at = CURRENT_TIMESTAMP;
                    RETURN NEW;
                END;
                $$ language 'plpgsql'
                """;
            jdbcTemplate.execute(createFunctionSql);
            
            // 创建触发器
            jdbcTemplate.execute("DROP TRIGGER IF EXISTS update_task_category_modtime ON task_category");
            jdbcTemplate.execute("""
                CREATE TRIGGER update_task_category_modtime
                BEFORE UPDATE ON task_category
                FOR EACH ROW
                EXECUTE FUNCTION update_modified_column()
                """);
            
            System.out.println("========== task_category 表检查/创建完成 ==========");
        } catch (Exception e) {
            System.err.println("========== 创建 task_category 表时出错: " + e.getMessage() + " ==========");
        }
    }
}