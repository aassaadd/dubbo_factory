package {{groupid}}.{{name}}_service.token;

public interface TokenManager {

	/**
	 * 
	 * 
	 * 
	 * 创建一个token关联上指定用户
	 * 
	 * 
	 * 
	 * @param userId
	 *            指定用户的id
	 * 
	 * 
	 * 
	 * @return 生成的token
	 */

	public TokenModel createToken(Integer userId);

	/**
	 * 
	 * 
	 * 
	 * 检查token是否有效
	 * 
	 * 
	 * 
	 * @param model
	 *            token
	 * 
	 * 
	 * 
	 * @return 是否有效
	 */

	public boolean checkToken(TokenModel model);

	/**
	 * 
	 * 
	 * 
	 * 从字符串中解析token
	 * 
	 * 
	 * 
	 * @param authentication
	 *            加密后的字符串
	 * 
	 * 
	 * 
	 * @return
	 */

	public TokenModel getToken(String authentication);

	/**
	 * 
	 * 
	 * 
	 * 清除token
	 * 
	 * 
	 * 
	 * @param userId
	 *            登录用户的id
	 */

	public void deleteToken(Integer userId);

}
