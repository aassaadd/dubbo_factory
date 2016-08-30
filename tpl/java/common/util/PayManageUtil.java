package {{groupid}}.common.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
/**
 * 微信用
 * @author zhaohaochen
 *
 */
public class PayManageUtil {

	public static String createOrderId(int length){
		String timeMillis = String.valueOf(System.currentTimeMillis());
		String str = createNoncestr(length - timeMillis.length());
		return timeMillis + str;
	}
	/**
	 * 	作用：产生随机字符串，不长于32位
	 */
	public static String createNoncestr(int length)
	{
		String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < length; i++ )  {
			int ram = (int)(Math.random()*36);
			if( 36 > ram && ram > 0){
				sb.append(chars.substring(ram - 1 , ram));
			}else if(ram == 0){
				sb.append(chars.substring(ram , ram + 1));
			}else{
				sb.append("s");
			}
		}  
		return sb.toString();
	}

	public String getCharacterAndNumber(int length)  
	{  
	    String val = "";  
	          
	    Random random = new Random();  
	    for(int i = 0; i < length; i++)  
	    {  
	        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字  
	             
	        if("char".equalsIgnoreCase(charOrNum)) // 字符串  
	        {  
	            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母  
	            val += (char) (choice + random.nextInt(26));  
	        }  
	        else if("num".equalsIgnoreCase(charOrNum)) // 数字  
	        {  
	            val += String.valueOf(random.nextInt(10));  
	        }  
	    }  
	          
	    return val;  
	}  

	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<String, String> signParams, String key) {
		StringBuffer sb = new StringBuffer();
		Set es = signParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
			//要采用URLENCODER的原始值！
		}
		String params = sb.substring(0, sb.lastIndexOf("&"));

		String sign = MD5Util.getMD5Lower(params + "&key=" + key);
		return sign.toUpperCase();
	}
	/**
	 * 	作用：生成sign
	 */
	public static String formatSortList(List<String> list, Map<String, Object> map, String key)
	{
		Collections.sort(list);
		StringBuffer param = new StringBuffer();
		String sign = "";
		for ( int i = 0; i < list.size(); i++ )  {
			if(i == 0){
				param.append(list.get(i) + "=" + map.get(list.get(i)));
			}else{
				param.append("&" + list.get(i) + "="  + map.get(list.get(i)));
			}
		}  
		
		sign = MD5Util.getMD5Lower(param.toString() + "&key=" + key);

		return sign.toUpperCase();
	}
	
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
}
