package top.theanything.core.enums;

/**
 * @author zhou
 * @Description
 * @createTime 2020-04-12
 */
public enum HttpMethod {

	GET("GET"),
	POST("POST")
	;
	private String method;
	HttpMethod(String method) {
		this.method = method;
	}

	public String getMethod(){
		return method;
	}
}
