INSERT INTO "user" (username, password_hash, nickname, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '管理员', 1)
ON CONFLICT (username) DO UPDATE SET
nickname = EXCLUDED.nickname, status = EXCLUDED.status;
