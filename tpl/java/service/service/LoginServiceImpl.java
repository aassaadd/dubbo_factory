package {{groupid}}.{{name}}_service.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import {{groupid}}.common.util.BeanUtils;
import {{groupid}}.common.util.EhcacheUtil;
import {{groupid}}.common.util.MD5Util;
import {{groupid}}.common.util.UUIDUtil;
import {{groupid}}.{{name}}_facade.model.User;
import {{groupid}}.{{name}}_facade.service.UserService;
import {{groupid}}.{{name}}_facade.service.LoginService;
import {{groupid}}.{{name}}_service.mapper.UserMapper;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private {{bean}}Service {{tf bean}}Service;
	@Autowired
	private TokenManager tokenManager;

	public Map<String, Object> login(String userName, String userPassword) {
		// TODO Auto-generated method stub
		if (userName == null || userName.equals("") || userPassword == null
				|| userPassword.equals("")) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", userName);
		map.put("enabled", 1);
		map.put("deleted", 0);
		// .andUserPasswordEqualTo(MD5Util.getMD5Lower(userPassword));
		List<MyWxUser> list = {{tf bean}}Service.getByList(map);
		if (list.size() > 0) {
			{{bean}} user = list.get(0);

			// 判断用户名密码是否相同
			if (!MD5Util.getMD5Lower(userPassword).equals(user.getPassword())) {
				return null;
			}
			String token = user.getId()+'_'+tokenManager.createToken(user.getId()).getToken();
			Map<String, Object> rmap = BeanUtils.Bean2Map(user);
			rmap.put("token", token);
			rmap.remove("password");
			return rmap;
		}
		return null;
	}


	@Override
	public Map<String, Object> getUserForToken(String token) {
		// TODO Auto-generated method stub

		TokenModel tokenModel=tokenManager.getToken(token);
		if (tokenModel != null && tokenManager.checkToken(tokenModel)) {

			Map<String, Object> map = BeanUtils.Bean2Map({{tf bean}}Service
					.getById(tokenModel.getUserId()));
			return map;
		}
		return null;
	}


	@Override
	public Map<String, Object> logout(Integer id) {
		// TODO Auto-generated method stub
		tokenManager.deleteToken(id);
		return null;
	}



}