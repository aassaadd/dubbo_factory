package {{groupid}}.{{name}}_service.token;

public class TokenModel {

	// 用户id
	private Integer userId;

	// 随机生成的uuid
	private String token;

	public TokenModel(Integer userId, String token) {
		this.userId = userId;
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}