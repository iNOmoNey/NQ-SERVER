package top.theanything.core.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import top.theanything.core.enums.HttpMethod;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhou
 * @Description
 * @createTime 2020-03-26
 */
public class Request {
	private String ip ;
	private FullHttpRequest fullrequest;
	private Map<String,String> headers = new HashMap<>();
	private Map<String,Cookie> cookies = new HashMap<>();
	private Map<String,Object> params = new HashMap<>(); //value有可能为List 也有可能为String

	public Request(ChannelHandlerContext ctx, FullHttpRequest req) {

		HttpHeaders headers = req.headers();
		setRequest(req);
		setHeaders(headers);
		setCookies(headers);
		setParams(new QueryStringDecoder(fullrequest.uri()));

		setIp(ctx);
	}

	private void setIp(ChannelHandlerContext ctx) {
		InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
		this.ip = addr.getAddress().toString();
	}

	private void setRequest(FullHttpRequest req) {
		this.fullrequest = req;
	}

	private void setHeaders(HttpHeaders headers) {
		for(Map.Entry<String, String> header :headers){
			this.headers.put(header.getKey(),header.getValue());
		}
	}
	private void setCookies(HttpHeaders headers) {
		String cookieString = this.headers.get("Cookie");
		if (cookieString!=null) {
			Set<Cookie> cookies = ServerCookieDecoder.LAX.decode(cookieString); //使用netty自带的工具对netty进行转换
			for (Cookie cookie : cookies) {
				this.cookies.put(cookie.name(), cookie);
			}
		}
	}
	private void setParams(QueryStringDecoder params) {
		Set<Map.Entry<String, List<String>>> param = params.parameters().entrySet();

		param.forEach(p->{   //如果参数只有一个 则提取为String 否则直接将list直接方式params
			List<String> list = p.getValue();
			this.params.put(p.getKey() , list.size() == 1 ? list.get(0) : list) ;
		});
	}

	public String getUri(){
		return fullrequest.uri();
	}
	public HttpMethod getMethod(){

		switch (fullrequest.method().name()){
			case "GET":
				return HttpMethod.GET;
			case "POST":
				return HttpMethod.POST;
		}
		return null;
	}
}
