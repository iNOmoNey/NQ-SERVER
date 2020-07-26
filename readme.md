# 应用层服务器

#### 部署的应用

[GET请求](http://nq.theanything.top)        [POST请求](http://nq.theanything.top/login)      [短链接服务器](http://nq.theanything.top/shortUrl)

#### 介绍

该项目为一个轻量级，高性能的应用层服务器。以Netty4为基础，结合网络编程、Http、SpringMVC、前缀树以及多种设计模式实现了以下功能：

1. 实现GET、POST请求
2. 支持RESTFul风格的URL
3. 实现过滤器拦截器
4. 集成自实现的简易版SpringMVC。并使用了基于前缀树的动态路由来实现对SpringMVC动态路由
   匹配的优化，性能提高60%
5. 实现Cookie

#### 代码架构
core模块为服务器核心模块，其余部分为对业务逻辑的封装。基于外观模式与约定即配置的原则，使用者只需实现非core目录即可使用。

``` 
.
├── /config  (服务配置类)
├── /utils   (utils帮助类)
└── /core    (核心基础模块)
    ├── /action     (处理器的父类)
    ├── /anno       (注解相关)
    ├── /exception  (异常基本类)
    ├── /base       (处理器映射器)
    ├── /enums      (HTTP部分方法枚举)
    ├── /error      (自定义异常)
    ├── /filter     (过滤器相关)
    ├── /handler    (Netty底层handler)
    ├── /http       (底层实体类)
    └── /config     (基本服务器配置)
    ├── /server     (Netty主配置类)
├── /web            (在NQ-Server基础上开发的Web页面)
    ├── /controller (处理器)
    ├── /filters    (过滤器)
    ├── /pojo       (实体类)
    ├── /response   (通用返回类型)
    ├── /service    (业务层代码)
    ├── /utils      (工具类)
```

#### 涉及的设计模式
1. 模版模式
2. 代理模式
4. 工厂模式
5. 单例模式
6. 策略模式
7. 组合模式

#### 注解

1. @Controller
  标记一个类为处理器

2. @RequestMapping

  标记一个方法作为处理器；

  value：映射路径。method：对应的请求方法

3. @Filter
  标记一个Controller处理自身业务逻辑前将会被过滤器前置处理。可以配置多个，按先后顺序过滤。

  

#### 挂载的Web页面

- [登录页面](http://nq.theanything.top/login)：登录成功会使用[JWT]( https://jwt.io/ )生成Token存进Cookie。对于没有登录的浏览器访问[查看用户信息](http://nq.theanything.top/getUser)请求会被拦截。
- [短链接服务器](http://nq.theanything.top/shortUrl)：长链接地址变短链接。（域名较长...所以生成的短链接也不是很短...



