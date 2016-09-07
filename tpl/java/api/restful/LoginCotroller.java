package {{groupid}}.{{name}}_api.restful;


import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.common.utils.StringUtils;
import {{groupid}}.{{name}}_facade.model.MyWxUser;
import {{groupid}}.{{name}}_facade.service.LoginService;

@RestController
@RequestMapping(value = "/")
public class LoginCotroller extends BaseController {
	private static Logger LOGGER = LoggerFactory
			.getLogger(LoginCotroller.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Map<String, Object> login(@RequestBody {{bean}} user,
									 HttpServletRequest request) {
		Map<String, Object> userMap = loginService.login(user.getUserName(),
				user.getPassword());
		if (userMap != null) {

			return getReturnMapSuccess(userMap);
		}

		return getReturnMapFailure("登陆失败");
	}
	@RequestMapping(value = "logout", method = RequestMethod.DELETE)
	public Map<String, Object> logout(@RequestBody {{bean}} user,
									  HttpServletRequest request) {
		loginService.logout(user.getId());

		return getReturnMapSuccess("登出成功");
	}

	public static String getRemoteAddrIp(HttpServletRequest request) {
		// proxy_set_header X-Real-IP $remote_addr;

		String ipFromNginx = getHeader(request, "X-Real-IP");
		System.out.println("ipFromNginx:" + ipFromNginx);
		System.out.println("getRemoteAddr:" + request.getRemoteAddr());
		return StringUtils.isEmpty(ipFromNginx) ? request.getRemoteAddr()
				: ipFromNginx;
	}

	private static String getHeader(HttpServletRequest request, String headName) {
		String value = request.getHeader(headName);
		return !StringUtils.isBlank(value)
				&& !"unknown".equalsIgnoreCase(value) ? value : "";
	}
}
