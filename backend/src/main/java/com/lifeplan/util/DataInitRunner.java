package com.lifeplan.util;

import com.lifeplan.component.DataInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitRunner implements CommandLineRunner {

    @Autowired
    private DataInitService dataInitService;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Override
    public void run(String... args) throws Exception {
        String dbType = activeProfile.contains("postgre") ? "postgre" : "mysql";
        
        String taskCategoryInitFile = "db/init-data/task_category_init_" + dbType + ".sql";
        String userInitFile = "db/init-data/user_init_" + dbType + ".sql";

        dataInitService.initTableData("task_category", taskCategoryInitFile);
        dataInitService.initTableData("user", userInitFile);
    }
}
