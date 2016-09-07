package {{groupid}}.{{name}}_api.restful;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import {{groupid}}.common.enums.ReturnEnum;
import {{groupid}}.common.util.JacksonUtil;

public class BaseController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	/**
	 * 返回成功格式
	 * 
	 * @param data
	 * @return
	 */
	public final static Map<String, Object> getReturnMapSuccess(Object data) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("message", ReturnEnum.SUCCESS.getMessage());
		map.put("code", ReturnEnum.SUCCESS.getCode());
		map.put("succes", ReturnEnum.SUCCESS.getStatus());
		if (data != null)
			map.put("data", data);
		return map;
	}

	/**
	 * 获得request中的url参数
	 * 
	 * @param r
	 * @return
	 */
	public final static Map<String, Object> getParameterMap(HttpServletRequest r) {
		Map<String, String[]> p = r.getParameterMap();
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		for (Map.Entry<String, String[]> entry : p.entrySet()) {
			params.put(entry.getKey(), entry.getValue()[0]);
		}
		return params;
	}

	/**
	 * 手动设置业务逻辑错误
	 * 
	 * @param obj
	 * @return
	 */
	public final static Map<String, Object> getReturnMapFailure(ReturnEnum obj) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("message", obj.getMessage());
		map.put("code", obj.getCode());
		map.put("succes", obj.getStatus());
		return map;
	}

	/**
	 * 返回失败格式
	 * 
	 * @return
	 */
	public final static Map<String, Object> getReturnMapFailure() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("message", ReturnEnum.FAILURE.getMessage());
		map.put("code", ReturnEnum.FAILURE.getCode());
		map.put("succes", ReturnEnum.FAILURE.getStatus());
		return map;
	}

	/**
	 * 返回失败格式
	 * 
	 * @return
	 */
	public final static Map<String, Object> getReturnMapFailure(String message) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("message", message);
		map.put("code", ReturnEnum.FAILURE.getCode());
		map.put("succes", ReturnEnum.FAILURE.getStatus());
		return map;
	}

	/**
	 * jsonp输出格式
	 * 
	 * @param obj
	 */
	public void jsonpOutput(Object obj) {
		try {
			response.setContentType("text/plain");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			// response.setHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			String jsonpCallback = request.getParameter("jsonpCallback");// 客户端请求参数
			out.println(jsonpCallback + "(" + JacksonUtil.beanToJson(obj) + ")");// 返回jsonp格式数据
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 异常处理
	 * 
	 * @param runtimeException
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public Map<String, Object> runtimeExceptionHandler(Exception exception) {
		return getReturnMapFailure(exception.getLocalizedMessage());
	}

}
