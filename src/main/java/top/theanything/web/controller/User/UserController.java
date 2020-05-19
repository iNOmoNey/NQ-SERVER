package top.theanything.web.controller.User;

import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import top.theanything.anno.Controller;
import top.theanything.anno.Filter;
import top.theanything.anno.RequestMapping;
import top.theanything.core.action.AbstractAction;
import top.theanything.core.enums.HttpMethod;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;
import top.theanything.util.JsonUtil;
import top.theanything.util.JwtUtil;
import top.theanything.web.filters.DefalutFilter;
import top.theanything.web.filters.LoginFilter;
import top.theanything.web.pojo.User;
import top.theanything.web.response.Result;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhou
 * @Description  测试子包扫描情况
 * @createTime 2020-04-13
 */
@Controller
public class UserController extends AbstractAction {
	private Map<String,String> user = new HashMap<>();
	{
		//模拟数据库的登录数据
		user.put("111","111");
		user.put("222","222");
		user.put("333","333");
	}
	@RequestMapping(value = "/getUser",method = HttpMethod.GET)
	@Filter({DefalutFilter.class,LoginFilter.class})
	public String getUser(Request req , Response response){
		User user = new User("周斌", 21, "男", "敲代码");
		return new Result<User>(user, JsonUtil.ResultGenerator.OK).toString();
	}

	@RequestMapping(value = "/login",method = HttpMethod.POST)
	public String login(Request req , Response response){
		String username = (String) req.getParams().get("username");
		String password = (String) req.getParams().get("password");

		if(user.getOrDefault(username , null).equals(password) ){
			String token = JwtUtil.create(username);
			Cookie cookie = new DefaultCookie("loginInfo", token);
			response.addCookie(cookie);
			return "redirect:http://localhost:8080/getUser";
		}
		return "登录失败";
	}

}
