package {{groupid}}.{{name}}_service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @描述: 启动Dubbo服务用的MainClass.
 * @作者: zhc .
 * @创建时间: 2013-11-5,下午9:47:55 .
 * @版本: 1.0 .
 */
public class DubboProvider {

	private static final Log log = LogFactory.getLog(DubboProvider.class);

	public static void main(String[] args) {
		try {
			PropertyConfigurator
					.configure("conf/log4j.properties");
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"conf/applicationContext.xml");
			context.start();
			//密码
//			String password = "root";
//			String[] arr = com.alibaba.druid.filter.config.ConfigTools
//					.genKeyPair(512);
//			log.info("数据库密码："
//					+ com.alibaba.druid.filter.config.ConfigTools.encrypt(
//							arr[0], password));
		} catch (Exception e) {
			log.error("== DubboProvider context start error:", e);
		}
		synchronized (DubboProvider.class) {
			while (true) {
				try {
					DubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:", e);
				}
			}
		}
	}

}