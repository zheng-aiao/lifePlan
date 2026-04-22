INSERT INTO task_category (dict_key, dict_value, dict_type, sort_order, description) VALUES
('年任务', '1', 'task_type', 1, '年度任务'),
('季度任务', '2', 'task_type', 2, '季度任务'),
('月任务', '3', 'task_type', 3, '月度任务'),
('周任务', '4', 'task_type', 4, '周任务'),
('日任务', '5', 'task_type', 5, '日任务')
ON CONFLICT (dict_key) DO UPDATE SET
sort_order = EXCLUDED.sort_order, description = EXCLUDED.description;
