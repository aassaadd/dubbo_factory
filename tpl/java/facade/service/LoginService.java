package {{groupid}}.{{name}}_facade.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface LoginService {
	/**
	 * 登录接口
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> login(String userName,String userPassword);
	/**
	 * 登出接口
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> logout(Integer id);

	/**
	 * 通过token 获得哪个用户登录
	 * @param token
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Map<String, Object> getUserForToken(String token) ;



}
