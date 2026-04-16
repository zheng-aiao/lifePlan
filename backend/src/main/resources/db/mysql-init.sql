-- 创建数据库
CREATE DATABASE IF NOT EXISTS life_plan DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE life_plan;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar_url VARCHAR(500) COMMENT '头像URL',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建任务主表
CREATE TABLE IF NOT EXISTS task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
    title VARCHAR(200) NOT NULL COMMENT '任务标题',
    description TEXT COMMENT '任务描述',
    category VARCHAR(50) COMMENT '任务分类：工作/学习/健身/生活等',
    task_type TINYINT NOT NULL COMMENT '任务类型：1-年度任务 2-月度任务 3-日任务',
    task_status TINYINT DEFAULT 0 COMMENT '任务状态：0-待开始 1-进行中 2-暂停中 3-已完成 4-未完成 5-已放弃',
    task_group VARCHAR(50) DEFAULT '' COMMENT '任务组别',
    task_priority TINYINT DEFAULT 1 COMMENT '优先级：1-低 2-中 3-高',
    task_progress INT DEFAULT 0 COMMENT '进度百分比 0-100',
    sub_task_group VARCHAR(50) DEFAULT '' COMMENT '子任务组别',
    
    planned_start_time DATETIME COMMENT '计划开始时间',
    planned_end_time DATETIME COMMENT '计划结束时间',
    actual_start_time DATETIME COMMENT '实际开始时间',
    actual_end_time DATETIME COMMENT '实际结束时间',
    actual_duration INT COMMENT '实际用时（分钟）',
    
    parent_id BIGINT COMMENT '父任务ID，用于任务层级',
    user_id BIGINT NOT NULL COMMENT '所属用户ID',
    
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-否 1-是',
    is_delayed TINYINT DEFAULT 0 COMMENT '是否延时：0-否 1-是',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    INDEX idx_user_type (user_id, task_type),
    INDEX idx_status (task_status),
    INDEX idx_time (planned_start_time, planned_end_time),
    INDEX idx_parent (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务主表';

-- 创建子任务表
CREATE TABLE IF NOT EXISTS sub_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '子任务ID',
    sub_task_group VARCHAR(50) DEFAULT '' COMMENT '子任务组别，用于关联任务',
    title VARCHAR(200) NOT NULL COMMENT '子任务标题',
    status TINYINT DEFAULT 0 COMMENT '状态：0-未完成 1-已完成',
    priority TINYINT DEFAULT 1 COMMENT '优先级：1-低 2-中 3-高 4-紧急',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    finish_time DATETIME COMMENT '实际完成时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_sub_task_group (sub_task_group)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子任务表';

-- 创建任务状态变更表
CREATE TABLE IF NOT EXISTS task_status_change (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '变更记录ID',
    task_id BIGINT NOT NULL COMMENT '所属任务ID',
    change_type TINYINT NOT NULL COMMENT '变更类型：1-开始 2-暂停 3-恢复 4-完成 5-放弃 6-延时完成',
    feedback_content TEXT COMMENT '反馈内容/原因',
    duration INT COMMENT '持续时间（分钟）',
    
    user_id BIGINT NOT NULL COMMENT '操作用户ID',
    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    INDEX idx_task_type (task_id, change_type),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务状态变更表';

-- 插入默认用户（如果不存在）
INSERT IGNORE INTO user (username, password_hash, nickname, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '管理员', 1);
