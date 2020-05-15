package top.theanything.core.filter;

import top.theanything.core.http.Request;
import top.theanything.core.http.Response;

/**
 * @author zhou
 * @Description
 * @createTime 2020-05-15
 */
public abstract class AbstractFilter {
    //过滤链
	private AbstractFilter next;

	 public void doChain(Request request, Response response){
	 	if( !doFilter(request,response))
			return;
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
