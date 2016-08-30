package {{groupid}}.common.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {

	/**
	 * Bean2Map:属性必须存在getter并且属性必须满足骆驼命名法，并且不能出现getORxxx的形式.
	 * 
	 * @param javaBean
	 * @return
	 */
	public static <K, V> Map<K, V> Bean2Map(Object javaBean) {
		Map<K, V> ret = new HashMap<K, V>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					ret.put((K) field, (V) (null == value ? "" : value));
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 获得javabean下的所有方法明以及对应传参
	 * 
	 * @param javaBean
	 * @return
	 */
	public static Map<String, Class<?>[]> getBeanMethodsParameterTypes(
			Object javaBean) {
		Map<String, Class<?>[]> ret = new HashMap<String, Class<?>[]>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				String metName = method.getName(); // 取得方法名称
				Class<?>[] param = method.getParameterTypes(); // 得到全部的参数类型
				ret.put(metName, param);
			}
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 获得javabean下的所有方法明以及get对应返回值
	 * 
	 * @param javaBean
	 * @return
	 */
	public static Map<String, Class<?>> getBeanMethodsReturnType(Object javaBean) {
		Map<String, Class<?>> ret = new HashMap<String, Class<?>>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
//					String metName = method.getName(); // 取得方法名称
					Class<?> re = method.getReturnType();
					ret.put(field, re);
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}

}
