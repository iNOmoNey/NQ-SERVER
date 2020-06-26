package top.theanything.web.controller;

import top.theanything.anno.Controller;
import top.theanything.anno.RequestMapping;
import top.theanything.config.BasicConfig;
import top.theanything.core.action.AbstractAction;
import top.theanything.core.enums.HttpMethod;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;

import java.io.IOException;

/**
 * @author zhou
 * @Description
 * @createTime 2020-04-12
 */
@Controller
public class DefalutAction extends AbstractAction {
	@RequestMapping(value = "/") // Http请求默认设置为GET
	public void redirect(Request req , Response response) throws IOException {
		response.sendFile(response.PREFIX_PATH+ BasicConfig.indexPath);
		return;
	}


	@RequestMapping(value = "/zzz",method = HttpMethod.POST)
	public void test(Request req , Response response){

	}

}
