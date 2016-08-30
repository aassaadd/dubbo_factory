---
dubbo_factory
---


通过mysql数据库表结构，生成dubbo的项目结构

 - dubbo_facade
 -  dubbo_service
 - dubbo_api
 - common
 - pom_parent


----------


  **参数:**

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
