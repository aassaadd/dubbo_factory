package {{groupid}}.common.enums;

public enum ReturnEnum {
	
	SUCCESS(200, "操作成功", true), 
	NOT_FOUND(400, "没有该资源", false),
	FAILURE(500, "系统错误", false),
	ARTICLECATEGORYDELETE(4010, "如果想删除该话题，请先把该话题下所有主题移动后再删除该话题", false),
	B2CCUSTOMERMOBILE(4011, "该手机号已存在", false),
	B2CCUSTOMERMOBILEISNULL(4012, "手机号不能为空", false),
	B2CCUSTOMERPASSWORDISNULL(4013, "密码不能为空", false),
	B2CCUSTOMERISNULL(4014, "用户不存在", false);
	private int code;
	private String message;
	private boolean status;
	
	
	private ReturnEnum (int code, String message, boolean status){
		this.code = code;
		this.message = message;
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
