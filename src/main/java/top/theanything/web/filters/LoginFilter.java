package top.theanything.web.filters;

import io.netty.handler.codec.http.cookie.Cookie;
import top.theanything.config.BasicConfig;
import top.theanything.core.filter.AbstractFilter;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;
import top.theanything.util.JwtUtil;

import java.util.Map;

/**
 * @author zhou
 * @Description
 * @createTime 2020-05-15
 */
public class LoginFilter extends AbstractFilter {
	private static String cookieName = BasicConfig.cookieName;
	@Override
	protected boolean doFilter(Request request, Response response) {

		try{
			Map<String, Cookie> cookies ;
			String token ;
			//只有在jwt校验token通过的时候才可以访问
			if( (cookies = request.getCookies()) != null
					&& ( token = cookies.get(cookieName).value()) != null ){
				String userId = null;
				try {
					userId = JwtUtil.parse(token);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(userId!=null)
					return true;
			}
		}catch (Exception e){
			return false;
		}
		return false;
	}
}
