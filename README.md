快速使用
===================
> 执行命令
```
node ./index -g com.snake -n snake -d snake -s /Users/zhaohaochen/git/dubbo_factory -b MyWxUser
```
> 参数:
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

#### 通过mysql数据库表结构，生成dubbo的项目结构
 - dubbo_facade
 - dubbo_service
 - dubbo_api
 - common
 - pom_parent

## MyBatis Example类使用说明:
[MyBatis Example类使用说明](http://mbg.cndocs.tk/generatedobjects/exampleClassUsage.html)


## 调试说明
调用自己的本机消费者服务，需要两步骤：
>第一步：本机提供者服务不想Zookeeper注册服务（这样做使得他人条用不了自己的本机服务）
![alt](https://raw.githubusercontent.com/aassaadd/dubbo_factory/master/public/6.png)

>第二步：在消费者服务指定条用本地服务（直连调试）
![alt](https://raw.githubusercontent.com/aassaadd/dubbo_factory/master/public/7.png)


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

项目结构说明
===================
## common
![alt](https://raw.githubusercontent.com/aassaadd/dubbo_factory/master/public/1.png)
#### 说明
```
/common                       通用工具
/common/enums                枚举
/common/util                   工具
/resources/conf/ehcache.xml     ehcache配置文件
```
## pom-parent
 ![alt](https://raw.githubusercontent.com/aassaadd/dubbo_factory/master/public/2.png)
#### 说明：
```
/ pom.xml                       通用pom文件
```
## xxxx-api
 ![alt](https://raw.githubusercontent.com/aassaadd/dubbo_factory/master/public/3.png)
#### 说明：
```
/common/SnakeSystem.java              全局系统文件
/filter/LoginInterceptor.java              登陆验证过滤类，需要根据具体情况调试
/filter/SimpleCORSFilter.java              跨域访问通用过滤类
/jetty/Main.java                         jetty 启动类
/restful                                 restfulController类
/resources/conf/dubbo-consumer.xml    dubbo 消费者配置文件
```
## xxxx-facade
 ![alt](https://raw.githubusercontent.com/aassaadd/dubbo_factory/master/public/4.png)
#### 说明：
```
/model                                 数据库实体类
/service                                service 接口定义
```
## xxxx-service
 ![alt](https://raw.githubusercontent.com/aassaadd/dubbo_factory/master/public/5.png)
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
## 如何开发
### service开发

> 1.先在facade中定义interface

```
public interface MyWxUserService extends BaseService<MyWxUser> {
	
}

```

> 2.在service中开发interface实现

```
@Service("myWxUserService")
public class MyWxUserServiceImpl implements MyWxUserService {

	@Autowired
	private MyWxUserMapper mapper;

	public MyWxUser add(MyWxUser t) {
		// TODO Auto-generated method stub
		Long d = System.currentTimeMillis();
		mapper.insertSelective(t);
		return t;
	}

	public MyWxUser delete(MyWxUser t) {
		// TODO Auto-generated method stub
		Long d = System.currentTimeMillis();
		mapper.updateByPrimaryKeySelective(t);
		return t;
	}

	public MyWxUser update(MyWxUser t) {
		// TODO Auto-generated method stub
		Long d = System.currentTimeMillis();
		mapper.updateByPrimaryKeySelective(t);
		return t;
	}

	public MyWxUser getById(int id) {
		// TODO Auto-generated method stub
		MyWxUser t = new MyWxUser();
		t.setId(id);
		return mapper.selectOne(t);
	}

	public ReturnPage<MyWxUser> getByPage(Long pageNumber, Long pageSize,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNumber.intValue(), pageSize.intValue());
		List<MyWxUser> list = getByList(params);
		PageInfo<MyWxUser> page = new PageInfo<MyWxUser>(list);
		return new ReturnPage<MyWxUser>(page.getTotal(), pageNumber, pageSize,
				list);
	}

	public List<MyWxUser> getByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		Map<String, Class<?>> returnType = BeanUtils
				.getBeanMethodsReturnType(MyWxUser.class);
		Example example = new Example(MyWxUser.class);
		//
		Criteria or = example.or();
		for (String key : params.keySet()) {
			if (key.indexOf("_like") > -1) {
				or.andLike(key.substring(0, key.indexOf("_like")),
						"%" + params.get(key) + "%");
			}
			if (!key.equals("pageSize") && !key.equals("page")
					&& !params.get(key).equals("") && key.indexOf("_in") < 0
					&& key.indexOf("_like") < 0) {
				if (returnType.containsKey(key)) {
					or.andEqualTo(key, returnType.get(key)
							.cast(params.get(key)));
				} else {
					or.andEqualTo(key, params.get(key));
				}
			}

		}
//		or.andEqualTo("deleted", 0);
//		example.setOrderByClause("create_date DESC");
		return mapper.selectByExample(example);
	}


}
```

> 3.在service的dubbo-provider中暴露服务

```
	<dubbo:service interface="com.snake.snake_facade.service.LoginService"
		ref="loginService" />
```

### api开发
> 1.注入dubbo服务

```
	<dubbo:reference interface="com.snake.snake_facade.service.LoginService"
		id="loginService" check="false" />
```

> 2.实现restfulcontroller

```
@RestController
@RequestMapping(value = "/api/v1/myWxUser")
public class MyWxUserController extends BaseController {

	@Autowired
	private MyWxUserService myWxUserService;

	public MyWxUserController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Map<String, Object> add(@RequestBody MyWxUser myWxUser) {
		Integer currentUserId = (Integer) SnakeSystem.getCurrentUserId();
		try {
			myWxUser = myWxUserService.add(myWxUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return getReturnMapFailure();
		}
		return getReturnMapSuccess(myWxUser);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Map<String, Object> update(@PathVariable(value = "id") Integer id,
			@RequestBody MyWxUser myWxUser) {
		Integer currentUserId = (Integer) SnakeSystem.getCurrentUserId();
		myWxUser.setId(id);
		try {
			myWxUser = myWxUserService.update(myWxUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return getReturnMapFailure();
		}
		return getReturnMapSuccess(myWxUser);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable(value = "id") Integer id) {
		Integer currentUserId = (Integer) SnakeSystem.getCurrentUserId();
		MyWxUser myWxUser = new MyWxUser();
		myWxUser.setId(id);
		try {
			myWxUser = myWxUserService.delete(myWxUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return getReturnMapFailure();
		}
		return getReturnMapSuccess(myWxUser);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Map<String, Object> getById(@PathVariable(value = "id") Integer id) {
		MyWxUser myWxUser = myWxUserService.getById(id);

		if (myWxUser == null) {
			return getReturnMapFailure();
		}
		return getReturnMapSuccess(myWxUser);

	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Map<String, Object> getByPage(HttpServletRequest request) {
		Map<String, Object> params = getParameterMap(request);
		if (!params.containsKey("page")) {
			return getReturnMapSuccess(myWxUserService.getByList(params));
		}
		Long pageNumber = Long.parseLong((String) params.get("page"));
		Long pageSize = Long.parseLong((String) params.get("pageSize"));
		return getReturnMapSuccess(myWxUserService.getByPage(pageNumber,
				pageSize, params));

	}
}
```

```
SnakeSystem.getCurrentUserId()                                 活的当前登陆用户
getReturnMapFailure()/getReturnMapFailure(xxx)                 错误返回
getReturnMapSuccess(xxx)                                       成功返回
Map<String, Object> params = getParameterMap(request)          获得当前问号参数
```



