# Student

这是一个简单的SSM架构的学生信息管理系统，作者初衷只是初始SSM框架整合开发。

## :speech_balloon:项目信息

> SSM框架的学生信息管理系统
>
> 系统环境：Windows
>
> JDK版本：jdk13
>
> 开发环境：eclipse 2019-9

**🙅学生管理系统**

![](infor/学生管理系统.png)

## 🍓文件结构

> 项目文件

```
+---src
|   +---config
|   |   +---mybatis
|   |   |   \---mapper
|   |   +---spring
|   |   \---springmvc
|   \---vip
|       \---wulinzeng
|           +---controller
|           +---dao
|           +---entity
|           +---interceptor
|           +---page
|           +---service
|           |   \---impl
|           \---util
\---WebContent
    +---easyui
    |   +---css
    |   +---js
    |   \---themes
    |       +---default
    |       |   \---images
    |       +---icons
    |       \---locale
    +---h-ui
    |   +---css
    |   +---images
    |   |   \---gq
    |   +---js
    |   +---lib
    |   |   +---Hui-iconfont
    |   |   |   \---1.0.1
    |   |   +---icheck
    |   |   \---jquery
    |   |       \---1.9.1
    |   \---skin
    |       \---default
    +---META-INF
    \---WEB-INF
        +---lib
        \---views
            +---grade
            +---system
            \---user
```

## :key: 数据库

`user`表设计

> user 表是操作管理员，数据存储的是管理员用户

| 列名     | 类型       | 备注                   |
| -------- | ---------- | ---------------------- |
| id       | integer    | 自增，主键，不允许为空 |
| username | vachar(32) | 不允许为空             |
| password | vachar(32) | 不允许为空             |

`grade`表设计

> 年级信息表

|  列名  |    类型     |          备注          |
| :----: | :---------: | :--------------------: |
|   id   |   integer   | 自增，主键，不允许为空 |
|  name  | vachar(32)  | 年级名称列，不允许为空 |
| remark | vachar(512) | 年级备注信息，可以为空 |



## 🍛功能模块

**🧾系统功能**

- 登录（管理员，学生）功能（已完善）
- 验证码功能（已完善）
- 后台请求拦截器 （已完善）
- 系统错误页面（4xx，5xx）
- 管理员页面 （已完善）
- BGM 

**💁管理员**

> PS：内容涉及 user 表 

- 添加管理员用户（已完善）
- 查询管理员用户（已完善）
- 修改管理员用户（已完善）
- 删除管理员用户（已完善）

> PS：grade 年级信息表

- 添加年级（已完善）
- 修改年级信息（已完善）
- 删除年级（已完善）
- 查看年级信息（已完善）

> PS 班级表

- 添加班级

- 修改班级信息

- 删除班级

- 查看班级信息

> PS 学生表

- 添加学生

- 上传学生头像  

- 修改学生信息

- 删除学生

- 查看学生信息

**💆学生**

- 查看自己的学生信息
- 修改自己学生头像
- 查看年级信息
- 查看班级信息