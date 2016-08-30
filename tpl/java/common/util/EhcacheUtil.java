package {{groupid}}.common.util;

import java.io.Serializable;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {
	
//	private static Logger logger = LoggerFactory.getLogger(EhcacheUtil.class);
	
	private static CacheManager manager = null;
	
	public static final String CACHE_NAME = "cache";
	
	public static final String SMS_NAME = "sms";
	
	public static final String ORDER_NAME = "order";
	
	static {
		try {
			manager = CacheManager.create(EhcacheUtil.class.getClassLoader().getResourceAsStream("conf/ehcache.xml"));
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}
	
	public static Cache getCache(String arg0) {
		return manager.getCache(arg0);
	} 


	public static void put(String cachename, Serializable key, Serializable value) {
		Cache cache = getCache(cachename);
		if (cache != null) {
			try {
				cache.remove(key);
				Element elem = new Element(key, value);
				cache.put(elem);
			} catch (Exception e) {
//				logger.error("put cache(" + cachename + ") of " + key + " failed.", e);
			}
		}
	}

	public static Object get(String cachename, Serializable key) {
		Cache cache = getCache(cachename);
		if (cache != null) {
			try {
				Element elem = cache.get(key);
				if (elem != null && !cache.isExpired(elem))
					return elem.getObjectValue();
			} catch (Exception e) {
//				logger.error("Get cache(" + cachename + ") of " + key + " failed.", e);
			}
		}
		return null;
	}

	public static void clearCache(String cachename) {
		manager.getCache(cachename).removeAll();
	}

	public static void remove(String cachename, Serializable key) {
		manager.getCache(cachename).remove(key);
	}

}
