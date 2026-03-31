# Life Plan 后端项目

基于 Spring Boot + MyBatis-Plus + MySQL 的任务管理系统后端

## 技术栈

- Spring Boot 3.2.1
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Lombok
- Hutool

## 项目结构

```
backend/
├── src/main/java/com/lifeplan/
│   ├── LifePlanApplication.java    # 主启动类
│   ├── common/                     # 通用类
│   │   ├── Result.java            # 统一响应封装
│   │   ├── PageResult.java        # 分页结果封装
│   │   ├── BusinessException.java # 业务异常
│   │   └── GlobalExceptionHandler.java # 全局异常处理
│   ├── controller/                 # 控制器层
│   │   ├── TaskController.java
│   │   ├── SubTaskController.java
│   │   └── TaskStatusChangeController.java
│   ├── dto/                        # 数据传输对象
│   │   ├── TaskCreateDTO.java
│   │   ├── TaskUpdateDTO.java
│   │   ├── TaskQueryDTO.java
│   │   ├── SubTaskCreateDTO.java
│   │   ├── SubTaskUpdateDTO.java
│   │   └── TaskStatusChangeDTO.java
│   ├── entity/                     # 实体类
│   │   ├── Task.java
│   │   ├── SubTask.java
│   │   ├── User.java
│   │   └── TaskStatusChange.java
│   ├── mapper/                     # MyBatis Mapper
│   │   ├── TaskMapper.java
│   │   ├── SubTaskMapper.java
│   │   ├── UserMapper.java
│   │   └── TaskStatusChangeMapper.java
│   └── service/                    # 服务层
│       ├── TaskService.java
│       ├── SubTaskService.java
│       ├── TaskStatusChangeService.java
│       └── impl/
│           ├── TaskServiceImpl.java
│           ├── SubTaskServiceImpl.java
│           └── TaskStatusChangeServiceImpl.java
├── src/main/resources/
│   ├── application.yml             # 配置文件
│   ├── db/
│   │   └── init.sql               # 数据库初始化脚本
│   └── mapper/                     # MyBatis XML 映射文件
│       ├── TaskMapper.xml
│       ├── SubTaskMapper.xml
│       └── TaskStatusChangeMapper.xml
└── pom.xml                         # Maven 配置
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE life_plan DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行初始化脚本：
```bash
mysql -u root -p life_plan < src/main/resources/db/init.sql
```

3. 修改 `application.yml` 中的数据库配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/life_plan?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: your_password
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

项目将在 `http://localhost:8080/api` 启动

## API 接口

### 任务管理接口

- `POST /api/tasks` - 创建任务
- `GET /api/tasks/{date}` - 获取某日任务列表
- `PUT /api/tasks/{id}` - 更新任务
- `DELETE /api/tasks/{id}` - 删除任务
- `POST /api/tasks/{id}/start` - 开始任务
- `POST /api/tasks/{id}/pause` - 暂停任务
- `POST /api/tasks/{id}/resume` - 恢复任务
- `POST /api/tasks/{id}/complete` - 完成任务
- `POST /api/tasks/{id}/abandon` - 放弃任务
- `POST /api/tasks/{id}/delay` - 延时任务

### 子任务管理接口

- `POST /api/sub-tasks` - 创建子任务
- `DELETE /api/sub-tasks/{id}` - 删除子任务
- `PUT /api/sub-tasks/{id}` - 更新子任务
- `GET /api/sub-tasks` - 获取子任务列表
- `POST /api/sub-tasks/{id}/complete` - 完成子任务
- `POST /api/sub-tasks/{id}/uncomplete` - 取消完成子任务

### 活动日志接口

- `GET /api/task-changes/{taskId}` - 获取任务状态变更记录
- `POST /api/task-changes` - 创建状态变更记录

## 开发说明

### 统一响应格式

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 错误码说明

- 200: 成功
- 400: 参数错误
- 404: 资不存在
- 500: 服务器错误

## 许可证

MIT License
