package {{groupid}}.common.util;

import java.util.Random;

/**
 * @author  zhc E-mail:zhc@beimingsmart.com
 * @date 创建时间：2015年8月31日 下午7:07:51
 * @version 1.0
 * @parameter
 * @since
 * @return 
 */
public class NoTools {

	/**
	 * 获得以时间戳为基础的12位编码
	 * 
	 * @param topString
	 * @return
	 */
	public final static String getNo(String topString) {
		String re = topString
				+ toHex(System.currentTimeMillis()).toUpperCase();
		if(re.length()<9){
			re+=random(0,9);
		}
		re+=random(0,9);
		return re;
	}
	/**
	 * 转16进制
	 * 
	 * @param topString
	 * @return
	 */
	public final static String toHex(long time) {
		return Integer.toHexString((int) time);
	}

	public final static int random(int min,int max){
		 Random random = new Random();
		 int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
}
