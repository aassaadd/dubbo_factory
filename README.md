# 内网测试环境服务器说明
## 项目目录结构
```
JAVA
|_ snake_3.0
  |_MainBranch
    |_XXX
      |_XXX-service
      |_XXX-facade
      |_XXX-api
    |_common
    |_pom-parent
  |_Release
    |_XXX
      |_XXX-service
      |_XXX-facade
      |_XXX-api
    |_common
    |_pom-parent   
  |_Trunk
    |_XXX
      |_XXX-service
      |_XXX-facade
      |_XXX-api
    |_common
    |_pom-parent
html
|_ sanke_3.0
  |_MainBranch
    |_XXX
      |_ dist 
  |_Release
    |_XXX
      |_ dist 
  |_Trunk
    |_XXX
      |_ dist 
```
注：XXX-，表示多个不同子项目名称

## 服务器
> 编号
```
snake_1
```
> 名称
```
基础服务工具
```
> 地址
```
root@10.0.0.21
```
> 系统版本
```
CentOS 7.x
```
> 部署内容
```
1.zookeeper
docker run --name some-zookeeper  -d -p 2181:2181 zookeeper:latest
2.dubbo-admin
docker run --name some-tomcat7 --link some-zookeeper:zookeeper  -it -d -v /docker/tomcat7/webapps:/usr/local/tomcat/webapps -p 8081:8080 tomcat:7
地址：ip:8081/dubbo-admin
```
> 文件路径
```
|_ docker
  |_ images------------myimages
  |_XXX-----------------images
    |_ var
```


## 服务器
> 编号
```
snake_2
```
> 名称
```
html 服务
```
> 地址
```
root@10.0.0.22
```
> 系统版本
```
CentOS 7.x
```
> 部署内容
```
1.nginx
docker run --name some-nginx -itd -v /docker/nginx/html:/html -v /docker/nginx/conf.d:/etc/nginx/conf.d -p 80:80 nginx
```
> 文件路径
```
|_ docker
  |_ images------------myimages
  |_XXX-----------------images
    |_ var
```


## 服务器
> 编号 
```
snake_3
```
> 名称
```
java 服务
```
> 地址
```
root@10.0.0.23
```
> 系统版本
```
CentOS 7.x
```
> 部署内容
```
1.docker
```
> 文件路径
```
|_ docker
  |_ images------------myimages
  |_XXX-----------------images
    |_ var
```


## 服务器
> 编号
```
snake_4
```
> 名称
```
数据库 服务
```
> 地址
```
root@10.0.0.24
```
> 系统版本
```
CentOS 7.x
```
> 部署内容
```
1.mysql
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql
地址:ip:3306
```
> 文件路径
```
|_ docker
  |_ images------------myimages
  |_XXX-----------------images
    |_ var
```

