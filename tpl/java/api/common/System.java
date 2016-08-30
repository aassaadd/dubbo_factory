package {{groupid}}.{{name}}_api.common;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class {{tf2 name}}System {

	/**
	 * 日志工具
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(System.class);

	public static final ThreadLocal<Integer> CURRENT_USER_ID_THREADLOCAL= new ThreadLocal<Integer>();

	/**
	 * 登录系统后,将用户信息存入session时对应的key.
	 */
	public static final String USER_SESSION_KEY = "currentUser";

	/**
	 * 系统propoerties配置
	 */
	private static final Properties PROPS;

	static {
		try {
			PROPS = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/conf/{{tf2 name}}System.properties"));
		} catch (IOException e) {
			LOGGER.error("系统初始化失败,错误原因:无法读取系统配置文件 /conf/{{tf2 name}}System.properties", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 本系统缓存的key的统一前缀
	 */
	public static final String SYS_CACHE_PREFIX = PROPS.getProperty("sys_cache_prefix");
	/**
	 * 获取当前系统的系统code.
	 */
	public final static String CODE = PROPS.getProperty("sys_code");

	/**
	 * 获取配置文件的配置信息.
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		return PROPS.getProperty(key);
	}

	/**
	 * 获取配置文件的配置信息.
	 * 
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static String getConfig(String key, String defaultVal) {
		return PROPS.getProperty(key, defaultVal);
	}

	public static Integer getCurrentUserId(){
		return CURRENT_USER_ID_THREADLOCAL.get();
	}
	

	public static int currentTimeMillis() {
		// TODO Auto-generated method stub
		return 0;
	}

}
