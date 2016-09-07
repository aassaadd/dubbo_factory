package {{groupid}}.{{name}}_api.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import {{groupid}}.common.ErrorResult;
import {{groupid}}.{{name}}_api.common.{{tf2 name}}System;
import {{groupid}}.{{name}}_facade.service.LoginService;
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);  
	@Autowired
	private LoginService loginService;
    /* 
     * 利用正则映射到需要拦截的路径     
      
    private String mappingURL; 
     
    public void setMappingURL(String mappingURL) {     
               this.mappingURL = mappingURL;     
    }    
  */  
    /**  
     * 在业务处理器处理请求之前被调用  
     * 如果返回false  
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     * 如果返回true  
     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
     *    再执行被拦截的Controller  
     *    然后进入拦截器链,  
     *    从最后一个拦截器往回执行所有的postHandle()  
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
     */    
    @Override    
    public boolean preHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler) throws Exception {    
        log.info("==============执行顺序: 1、preHandle================");   
        String token = request.getHeader("token");
		if (StringUtils.isEmpty(token)) {
			token = request.getParameter("token");
		}
	
		if (StringUtils.isEmpty(token)
				|| loginService.getUserForToken(token) == null) {
			ObjectMapper objectMapper = new ObjectMapper();
			// 校验失败.
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(
					objectMapper.writeValueAsString(new ErrorResult("101")
							.setMsg("未登录")));
			  return false;  
		}else{
			Integer currentUserId = Integer.parseInt(loginService.getUserForToken(token).get("id").toString());
			// 为当前线程设置当前操作用户.
			{{tf2 name}}System.CURRENT_USER_ID_THREADLOCAL.set(currentUserId);
			  return true;     
		}
    }    
    
    /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    @Override    
    public void postHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler,    
            ModelAndView modelAndView) throws Exception {     
        log.info("==============执行顺序: 2、postHandle================");    
      
    }    
    
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     *   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */    
    @Override    
    public void afterCompletion(HttpServletRequest request,    
            HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {    
        log.info("==============执行顺序: 3、afterCompletion================");    
    }    
}
