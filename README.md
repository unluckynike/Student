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

-----src.vip.wulinzeng

​                              ------controller

​                              ------dao

​                              ------entity

​                              ------service

​                                                 -------imp

​                              ------util        

​                              ------config

​                                                 -------log4j.properties (日志)

​                                                 -------db.properties （数据库配置文件）

​                                                 -------spring （spring容器的配置文件applicationConfig.xml）

​                                                 -------springmvc （springmvc前端控制器配置文件springmvc.xml）

​                                                 -------mybatis    (映射文件)

​                                                                     -------mapper

-----WebContent

​                     -------easyui

​                     -------h-hui

​                     -------META-INF

​                     -------WEB-INF

​                                           ---------lib

​                                           ---------views（前端页面）

​                                                               ---------system

​                                                               ---------

​                                                               ---------

​                                           ---------web.xml

​                                           ---------index.jsp

​                     -------README.md     



## :key: 数据库

`user`表设计

| 列名     | 类型       | 备注                   |
| -------- | ---------- | ---------------------- |
| id       | integer    | 自增，主键，不允许为空 |
| username | vachar(32) | 不允许为空             |
| password | vachar(32) | 不允许为空             |



## 🍛功能模块

**🧾系统功能**

- 登录（管理员，学生）功能（已完善）
- 验证码功能（已完善）
- 后台请求拦截器
- 系统错误页面（4xx，5xx）
- 管理员页面

**💁管理员**

- 添加加学生  
- 修改学生信息
- 删除学生
- 查看学生信息
- 添加班级
- 修改班级信息
- 删除班级
- 查看班级信息
- 添加年级
- 修改年级信息
- 删除年级
- 查看年级信息

**💆学生**

- 查看自己的学生信息
- 查看年级信息
- 查看班级信息