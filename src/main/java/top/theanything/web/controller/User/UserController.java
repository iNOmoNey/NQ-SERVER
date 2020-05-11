package top.theanything.web.controller.User;

import org.junit.Test;
import top.theanything.anno.Controller;
import top.theanything.anno.RequestMapping;
import top.theanything.core.action.AbstractAction;
import top.theanything.core.enums.HttpMethod;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;
import top.theanything.util.JsonUtil;
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
		user.put("111","111");
		user.put("222","222");
		user.put("222","222");
	}
	@RequestMapping(value = "/getUser",method = HttpMethod.GET)
	public String getUser(Request req , Response response){
		User user = new User("周斌", 21, "男", "敲代码");
		return new Result<User>(user, JsonUtil.ResultGenerator.OK).toString();
	}

	@RequestMapping(value = "/login",method = HttpMethod.POST)
	public void login(Request req , Response response){

	}

}
