package top.theanything.controller.defalut;

import top.theanything.anno.Controller;
import top.theanything.anno.RequestMapping;
import top.theanything.core.action.AbstractAction;
import top.theanything.core.enums.HttpMethod;

/**
 * @author zhou
 * @Description
 * @createTime 2020-04-12
 */
@Controller
public class UserController extends AbstractAction {
	@RequestMapping(value = "/getId",method = HttpMethod.GET)
	public void getId(){

	}
	@RequestMapping(value = "/login",method = HttpMethod.POST)
	public void login(){

	}
}
