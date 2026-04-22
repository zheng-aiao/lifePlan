package com.lifeplan.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class DataInitService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void initTableData(String tableName, String initSqlResource) {
        // 检查表是否存在且为空
        if (isTableEmpty(tableName)) {
            // 读取并执行初始化 SQL
            executeInitSql(initSqlResource);
            log.info("成功初始化 {} 表数据", tableName);
        } else {
            log.info("{} 表已有数据，跳过初始化", tableName);
        }
    }

    private boolean isTableEmpty(String tableName) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName, Integer.class);
            return count == null || count == 0;
        } catch (Exception e) {
            log.warn("检查表 {} 是否为空时出错: {}", tableName, e.getMessage());
            return true; // 发生错误时假设表为空
        }
    }

    private void executeInitSql(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            log.error("执行初始化 SQL 失败: {}", e.getMessage());
        }
    }
}
