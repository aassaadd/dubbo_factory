package {{groupid}}.common;

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
