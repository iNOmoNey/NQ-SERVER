package top.theanything.core.filter;

import top.theanything.config.BasicConfig;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;

import java.io.IOException;

/**
 * @author zhou
 * @Description
 * @createTime 2020-05-15
 */
public abstract class AbstractFilter {
    //过滤链
	private AbstractFilter next;

	 public void doChain(Request request, Response response){
	 	if( doFilter(request,response) == false) {
		    try {
			    response.sendFile(response.PREFIX_PATH+ BasicConfig.badRequestPath);
		    } catch (IOException e) {
			    e.printStackTrace();
		    }
		    return;
	    }
	 	if(next != null)
		    next.doChain(request,response);
	 }

	public AbstractFilter getNext() {
		return next;
	}

	public void setNext(AbstractFilter next) {
		this.next = next;
	}

	/**
	 * 子类自行实现过滤逻辑
	 * @param request
	 * @param response
	 * @return
	 */
	protected abstract boolean doFilter(Request request, Response response);
}
