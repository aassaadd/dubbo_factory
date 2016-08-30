package {{groupid}}.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 此类是MD5加密算法的实现， 采用了java内置算法，需要引用java.security.MessageDigest
 * 
 * @author
 * 
 */
public class MD5Util {
	// 小写的字符串
	private static char[] DigitLower = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	// 大写的字符串
	private static char[] DigitUpper = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 默认构造函数
	 * 
	 */
	public MD5Util() {
	}

	/**
	 * 加密之后的字符串全为小写
	 * 
	 * @param srcStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NullPointerException
	 */
	public static String getMD5Lower(String srcStr){
		String sign = "lower";
		return processStr(srcStr, sign);

	}

	/**
	 * 加密之后的字符串全为大写
	 * 
	 * @param srcStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NullPointerException
	 */
	public static String getMD5Upper(String srcStr){
		String sign = "upper";
		return processStr(srcStr, sign);

	}

	private static String processStr(String srcStr, String sign){
		if (null == srcStr) {
			throw new java.lang.NullPointerException("需要加密的字符串为Null");
		}

		MessageDigest digest;

		// 定义调用的方法
		String algorithm = "MD5";

		// 结果字符串
		String result = "";
		// 初始化并开始进行计算
		try {
			digest = MessageDigest.getInstance(algorithm);
			digest.update(srcStr.getBytes());

			byte[] byteRes = digest.digest();

			// 计算byte数组的长度
			int length = byteRes.length;

			// 将byte数组转换成字符串
			for (int i = 0; i < length; i++) {
				result = result + byteHEX(byteRes[i], sign);
			}

			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="";
		}
		return result;
	}

	/**
	 * 将btye数组转换成字符串
	 * 
	 * @param bt
	 * @return
	 */
	private static String byteHEX(byte bt, String sign) {

		char[] temp = DigitLower;
		if (sign.equalsIgnoreCase("lower")) {
			temp = DigitLower;
		} else if (sign.equalsIgnoreCase("upper")) {
			temp = DigitUpper;
		} 
		char[] ob = new char[2];

		ob[0] = temp[(bt >>> 4) & 0X0F];

		ob[1] = temp[bt & 0X0F];

		return new String(ob);

	}
//	/*** 
//     * MD5加码 生成32位md5码 
//     */  
//    public static String string2MD5(String inStr){  
//        MessageDigest md5 = null;  
//        try{  
//            md5 = MessageDigest.getInstance("MD5");  
//        }catch (Exception e){  
//            System.out.println(e.toString());  
//            e.printStackTrace();  
//            return "";  
//        }  
//        char[] charArray = inStr.toCharArray();  
//        byte[] byteArray = new byte[charArray.length];  
//  
//        for (int i = 0; i < charArray.length; i++)  
//            byteArray[i] = (byte) charArray[i];  
//        byte[] md5Bytes = md5.digest(byteArray);  
//        StringBuffer hexValue = new StringBuffer();  
//        for (int i = 0; i < md5Bytes.length; i++){  
//            int val = ((int) md5Bytes[i]) & 0xff;  
//            if (val < 16)  
//                hexValue.append("0");  
//            hexValue.append(Integer.toHexString(val));  
//        }  
//        return hexValue.toString();  
//  
//    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
}