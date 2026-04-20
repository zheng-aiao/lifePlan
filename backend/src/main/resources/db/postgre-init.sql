-- 创建用户表
CREATE TABLE IF NOT EXISTS "user" (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar_url VARCHAR(500) COMMENT '头像URL',
    status SMALLINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT idx_username UNIQUE (username),
    CONSTRAINT idx_email UNIQUE (email)
) COMMENT='用户表';

-- 创建更新时间触发器
CREATE OR REPLACE FUNCTION update_modified_column() 
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_user_modtime
BEFORE UPDATE ON "user"
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

-- 创建任务主表
CREATE TABLE IF NOT EXISTS task (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY COMMENT '任务ID',
    title VARCHAR(200) NOT NULL COMMENT '任务标题',
    description TEXT COMMENT '任务描述',
    category VARCHAR(50) COMMENT '任务分类：工作/学习/健身/生活等',
    task_type SMALLINT NOT NULL COMMENT '任务类型：1-年度任务 2-月度任务 3-日任务',
    task_status SMALLINT DEFAULT 0 COMMENT '任务状态：0-待开始 1-进行中 2-暂停中 3-已完成 4-未完成 5-已放弃',
    task_group VARCHAR(50) DEFAULT '' COMMENT '任务组别',
    task_priority SMALLINT DEFAULT 1 COMMENT '优先级：1-低 2-中 3-高',
    task_progress INT DEFAULT 0 COMMENT '进度百分比 0-100',
    sub_task_group VARCHAR(50) DEFAULT '' COMMENT '子任务组别',
    
    planned_start_time TIMESTAMP COMMENT '计划开始时间',
    planned_end_time TIMESTAMP COMMENT '计划结束时间',
    actual_start_time TIMESTAMP COMMENT '实际开始时间',
    actual_end_time TIMESTAMP COMMENT '实际结束时间',
    actual_duration INT COMMENT '实际用时（分钟）',
    
    parent_id BIGINT COMMENT '父任务ID，用于任务层级',
    user_id BIGINT NOT NULL COMMENT '所属用户ID',
    
    is_deleted SMALLINT DEFAULT 0 COMMENT '是否删除：0-否 1-是',
    is_delayed SMALLINT DEFAULT 0 COMMENT '是否延时：0-否 1-是',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    
    CONSTRAINT idx_user_type UNIQUE (user_id, task_type),
    CONSTRAINT idx_status UNIQUE (task_status),
    CONSTRAINT idx_time UNIQUE (planned_start_time, planned_end_time),
    CONSTRAINT idx_parent UNIQUE (parent_id)
) COMMENT='任务主表';

CREATE TRIGGER update_task_modtime
BEFORE UPDATE ON task
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

-- 创建子任务表
CREATE TABLE IF NOT EXISTS sub_task (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY COMMENT '子任务ID',
    sub_task_group VARCHAR(50) DEFAULT '' COMMENT '子任务组别，用于关联任务',
    title VARCHAR(200) NOT NULL COMMENT '子任务标题',
    status SMALLINT DEFAULT 0 COMMENT '状态：0-未完成 1-已完成',
    priority SMALLINT DEFAULT 1 COMMENT '优先级：1-低 2-中 3-高 4-紧急',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    finish_time TIMESTAMP COMMENT '实际完成时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT idx_sub_task_group UNIQUE (sub_task_group)
) COMMENT='子任务表';

CREATE TRIGGER update_sub_task_modtime
BEFORE UPDATE ON sub_task
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

-- 创建任务状态变更表
CREATE TABLE IF NOT EXISTS task_status_change (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY COMMENT '变更记录ID',
    task_id BIGINT NOT NULL COMMENT '所属任务ID',
    change_type SMALLINT NOT NULL COMMENT '变更类型：1-开始 2-暂停 3-恢复 4-完成 5-放弃 6-延时完成',
    feedback_content TEXT COMMENT '反馈内容/原因',
    duration INT COMMENT '持续时间（分钟）',
    
    user_id BIGINT NOT NULL COMMENT '操作用户ID',
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT idx_task_type UNIQUE (task_id, change_type),
    CONSTRAINT idx_user UNIQUE (user_id)
) COMMENT='任务状态变更表';

CREATE TRIGGER update_task_status_change_modtime
BEFORE UPDATE ON task_status_change
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

-- 创建任务类别表
CREATE TABLE IF NOT EXISTS task_category (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY COMMENT '类别ID',
    dict_key VARCHAR(50) NOT NULL COMMENT '类别键',
    dict_value VARCHAR(100) NOT NULL COMMENT '类别值',
    dict_type VARCHAR(50) NOT NULL COMMENT '类别类型',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    status SMALLINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    description VARCHAR(500) COMMENT '描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    
    CONSTRAINT idx_dict_type UNIQUE (dict_type),
    CONSTRAINT idx_dict_key UNIQUE (dict_key)
) COMMENT='任务类别表';

CREATE TRIGGER update_task_category_modtime
BEFORE UPDATE ON task_category
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

-- 插入任务类别初始化数据
INSERT INTO task_category (dict_key, dict_value, dict_type, sort_order, description)
VALUES
('年任务', '1', 'task_type', 1, '年度任务'),
('季度任务', '2', 'task_type', 2, '季度任务'),
('月任务', '3', 'task_type', 3, '月度任务'),
('周任务', '4', 'task_type', 4, '周任务'),
('未完成日任务', '5', 'task_type', 5, '未完成的日任务')
ON CONFLICT (dict_key, dict_type) DO NOTHING;

-- 插入默认用户（如果不存在）
INSERT INTO "user" (username, password_hash, nickname, status) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '管理员', 1)
ON CONFLICT (username) DO NOTHING;
