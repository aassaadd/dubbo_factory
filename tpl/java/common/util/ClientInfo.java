package {{groupid}}.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

public class ClientInfo {

    UserAgent userAgent ;
    /*  
     * 构造函数  
     * 参数：String request.getHeader("user-agent")  
     *   
     * IE7:Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C)  
     * IE8:Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C)  
     * Maxthon:Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; Maxthon 2.0)  
     * firefox:Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.11) Gecko/20101012 Firefox/3.6.11  
     * Chrome:Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Chrome/7.0.517.44 Safari/534.7  
     * Opera:Opera/9.80 (Windows NT 6.1; U; zh-cn) Presto/2.6.30 Version/10.63  
     *   
     * 操作系统：  
     * Win7 : Windows NT 6.1  
     * WinXP : Windows NT 5.1  
     */   
    public ClientInfo(String info){   
        userAgent= UserAgent.parseUserAgentString(info);
    }   
  
    /*  
     * 获取核心浏览器名称  
     */   
    public String getExplorerName(){   
        return userAgent.getBrowser().getName();   
    }   
  
    /*  
     * 获取核心浏览器版本  
     */   
    public String getExplorerVer(){   
    	try {
    		return userAgent.getBrowserVersion().getVersion();   
		} catch (Exception e) {
			// TODO: handle exception
		}
        return "";   
    }   
  
    /*  
     * 获取浏览器插件名称（例如：遨游、世界之窗等）  
     */   
    public String getExplorerPlug(){   
    	
        return userAgent.getBrowser().getRenderingEngine().name();    
    }   
       
    /*  
     * 获取操作系统名称  
     */   
    public String getOSName(){   
       return userAgent.getOperatingSystem().getName();   
    }   
  

}
