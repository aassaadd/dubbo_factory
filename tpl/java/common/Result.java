package {{groupid}}.common;

/**
 * 返回结果
 */
public abstract class Result {
	private String msg;

	public String getMsg() {
		return msg;
	}

	public Result setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}
