项目结构说明
===================
## common
![alt](https://coding.net/u/aaassaadd/p/dubbo_factory/git/raw/master/public/1.png)
#### 说明
```
/common                       通用工具
/common/enums                枚举
/common/util                   工具
/resources/conf/ehcache.xml     ehcache配置文件
```
## pom-parent
 ![alt](https://coding.net/u/aaassaadd/p/dubbo_factory/git/raw/master/public/2.png)
#### 说明：
```
/ pom.xml                       通用pom文件
```
## snake-api
 ![alt](https://coding.net/u/aaassaadd/p/dubbo_factory/git/raw/master/public/3.png)
#### 说明：
```
/common/SnakeSystem.java              全局系统文件
/filter/LoginInterceptor.java              登陆验证过滤类，需要根据具体情况调试
/filter/SimpleCORSFilter.java              跨域访问通用过滤类
/jetty/Main.java                         jetty 启动类
/restful                                 restfulController类
/resources/conf/dubbo-consumer.xml    dubbo 消费者配置文件
```
## snake-facade
 ![alt](https://coding.net/u/aaassaadd/p/dubbo_factory/git/raw/master/public/4.png)
#### 说明：
```
/model                                 数据库实体类
/service                                service 接口定义
```
## snake-service
 ![alt](https://coding.net/u/aaassaadd/p/dubbo_factory/git/raw/master/public/5.png)
#### 说明：
```
/mapper                              mybatis 用的mappe映射类
/service                               实现类
/token/TokenModel.java                Token模型
/token/TokenManager.java              Token工具接口类
/token/RedisTokenManager.java         redis生成Token工具接口类
/resources/conf/apllicationContext-redis.xml      redis使用配置
/resources/conf/dubbo-provider.xml    dubbo 提供者配置文件
```

开发说明
===================

## 层次说明
```
façade：暴露接口，需要暴露给服务消费者使用的接口和实体类等放在这。
Service：服务提供者
Api：服务消费者，封装service服务提供可被调用的restful接口层，默认封装简单的token接口验证服务
```
## MyBatis Example类使用说明:
[MyBatis Example类使用说明](http://mbg.cndocs.tk/generatedobjects/exampleClassUsage.html)

## 调试说明
调用自己的本机消费者服务，需要两步骤：
>第一步：本机提供者服务不想Zookeeper注册服务（这样做使得他人条用不了自己的本机服务）
![alt](https://coding.net/u/aaassaadd/p/dubbo_factory/git/raw/master/public/6.png)

>第二步：在消费者服务指定条用本地服务（直连调试）
![alt](https://coding.net/u/aaassaadd/p/dubbo_factory/git/raw/master/public/7.png)


项目调试／打包
===================

+ facade
```
facade：mvn package
打包说明：target下的jar 包
```
+ service
```
Service：mvn package
调试说明：/test/xxxxx/DubboProvider.java 执行这个文件
打包说明：target下的jar 包和lib文件夹
打包执行：java –jar xxxx.jar
```
+ api
```
api：mvn package -P standalone
调试说明：mvn  jetty:run
打包说明：target下的xxxx-api-standalone.war
打包执行：java –jar xxxx.war
```

自动化工具
===================

#### 通过mysql数据库表结构，生成dubbo的项目结构
 - dubbo_facade
 - dubbo_service
 - dubbo_api
 - common
 - pom_parent

#### 参数:
```
-h, --help                    output usage information
-V, --version                 output the version number
-g, --groupid <n>             groupId
-n, --name <n>                项目名称
-p, --parent <value>          是否需要通用pom
-c, --common <value>          是否需要通用common
-o, --mysql_host <value>      mysql参数:host
-u, --mysql_user <value>      mysql参数:user
-a, --mysql_password <value>  mysql参数:password
-d, --mysql_database <value>  mysql参数:database
-s, --src <value>             存储路径
-b, --bean <value>            用户类
```


