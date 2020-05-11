package top.theanything.web.controller;

import top.theanything.anno.Controller;
import top.theanything.anno.RequestMapping;
import top.theanything.core.action.AbstractAction;
import top.theanything.core.enums.HttpMethod;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;

/**
 * @author zhou
 * @Description
 * @createTime 2020-04-12
 */
@Controller
public class DefalutAction extends AbstractAction {
	@RequestMapping(value = "/") // Http请求默认设置为GET
	public String redirect(Request req , Response response){
		return "redirect:http://127.0.0.1:8080/static/index.html";
	}

	@RequestMapping(value = "/avc",method = HttpMethod.POST)
	public void test(Request req , Response response){

	}

}
