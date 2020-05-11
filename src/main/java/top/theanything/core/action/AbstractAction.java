package top.theanything.core.action;

import top.theanything.core.http.Request;
import top.theanything.core.http.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhou
 * @Description
 * Action 的 公共父类 , 方便进行反射调用。
 * 使用模板设计模式 ， 抽取action通用方法
 * @createTime 2020-04-12
 */
public  class AbstractAction  {

	public void doAction(Request req, Response response , Method method , AbstractAction action){
		try {
			String result = (String) method.invoke(action, req,response);
			if(result.startsWith("redirect:")){  //如果是跳转 后面的流程不用管了
				response.doRedirect( result.substring( result.indexOf(":")+1) );
				return;
			}
			response.setContent(result);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public void doErr(){

	}
}
