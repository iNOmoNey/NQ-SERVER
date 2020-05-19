package top.theanything.config;

/**
 * @author zhou
 * @Description
 * @createTime 2020-04-11
 */
public class BasicConfig {
	public static int port = 8080;
	public static String path = "top/theanything/web/controller";
	public static String packagePath = "top.theanything.web.controller.";
	public static String signingKey = "inOmOney";
	public static String cookieName = "loginInfo";

	public static String notFoundPath = "/static/error.html";
	public static String forbiddenPath = "/static/forbidden.html";
	public static String badRequestPath = "/static/badRequest.html";


}
