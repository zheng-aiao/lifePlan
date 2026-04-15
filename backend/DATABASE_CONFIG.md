# 数据库配置说明

本项目支持MySQL和PostgreSQL两种数据库，您可以根据需要选择使用哪种数据库。

## 配置文件结构

项目中包含以下数据库配置文件：

1. **`application.yml`** - 主配置文件，包含通用配置
2. **`application-mysql.yml`** - MySQL数据库配置
3. **`application-postgre.yml`** - PostgreSQL数据库配置

## 数据库初始化脚本

- **`db/mysql-init.sql`** - MySQL数据库初始化脚本
- **`db/postgre-init.sql`** - PostgreSQL数据库初始化脚本

## 如何选择数据库

### 方法一：修改主配置文件

在`application.yml`文件中，您可以修改`spring.profiles.active`属性来选择默认使用的数据库：

```yaml
spring:
  # 默认使用MySQL数据库
  profiles:
    active: mysql
    # 或使用PostgreSQL
    # active: postgre
```

### 方法二：运行时指定配置文件

在运行项目时，您可以通过命令行参数指定要使用的数据库配置：

#### 使用MySQL数据库
```bash
mvn spring-boot:run
```

或

```bash
mvn spring-boot:run -Dspring.profiles.active=mysql
```

#### 使用PostgreSQL数据库
```bash
mvn spring-boot:run -Dspring.profiles.active=postgre
```

## 数据库配置说明

### MySQL配置

在`application-mysql.yml`文件中，您可以修改以下配置：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/life_plan?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root  # 您的MySQL用户名
    password: Nucleus!  # 您的MySQL密码
```

### PostgreSQL配置

在`application-postgre.yml`文件中，您可以修改以下配置：

```yaml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://aws-1-eu-west-3.pooler.supabase.com:6543/postgres
    username: postgres.ropeeonpiwgdbknhjqyi  # 您的PostgreSQL用户名
    password: eE4bYAvkh8rWsLI7  # 您的PostgreSQL密码
```

## 端口配置

默认端口为8083，您可以在`application.yml`文件中修改：

```yaml
server:
  port: 8083  # 修改为您想要的端口
  servlet:
    context-path: /api
```

## 启动项目

完成配置后，您可以使用以下命令启动项目：

```bash
# 使用默认配置（MySQL）
mvn spring-boot:run

# 使用PostgreSQL配置
mvn spring-boot:run -Dspring.profiles.active=postgre
```

项目启动后，您可以通过以下URL访问：
```
http://localhost:8083/api
```