package top.theanything.web.filters;

import top.theanything.core.filter.AbstractFilter;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;

/**
 * @author zhou
 * @Description
 * @createTime 2020-05-15
 */
public class LoginFilter extends AbstractFilter {

	@Override
	protected boolean doFilter(Request request, Response response) {
		System.out.println("我是后面的过滤器");
		return false;
	}
}
