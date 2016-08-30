package {{groupid}}.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单相关
 * 
 * @author zhaohaochen
 *
 */
public class OrdersUtils {

	public static String ORDER_NO = "00001000";
	public static String ORDER_REFUND_NO = "00002000";

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	public static String getOrderNo() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmss").format(new Date());

		return date + ORDER_NO + getTwo() + "00" + seconds + getTwo();
	}

	/**
	 * 生成退款订单号
	 * 
	 * @return
	 */
	public static String getRefundOrderNo() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmss").format(new Date());
		for (int i = 0; i < 10000; i++) {
			System.out.println(date + ORDER_REFUND_NO + getTwo() + "00"
					+ seconds + getTwo());
		}
		return "";
	}

	/**
	 * 产生随机的2位数
	 * 
	 * @return
	 */
	private static String getTwo() {
		Random rad = new Random();

		String result = rad.nextInt(100) + "";

		if (result.length() == 1) {
			result = "0" + result;
		}
		return result;
	}

}
