package top.theanything.core.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.concurrent.Future;
import top.theanything.config.BasicConfig;
import top.theanything.core.action.AbstractAction;
import top.theanything.core.enums.HttpMethod;
import top.theanything.core.filter.AbstractFilter;
import top.theanything.util.ActionUtil;
import top.theanything.util.FilterUtil;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.lang.reflect.Method;

import static com.sun.media.jfxmedia.locator.Locator.DEFAULT_CONTENT_TYPE;


/**
 * @author zhou
 * @Description
 * @createTime 2020-03-26
 */
public class Response {

	private final HttpVersion HTTP_VERSION = HttpVersion.HTTP_1_1;
	private final int CHUNK_SIZE = 8192;
	private final String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";
	private HttpHeaders headers = new DefaultHttpHeaders();
	private HttpResponseStatus STATUS = HttpResponseStatus.OK;   //没有异常就是200的状态码
	private File file ;
	private ChannelHandlerContext ctx ;
	private Request request;
	private ByteBuf content = Unpooled.EMPTY_BUFFER;
	public  static String PREFIX_PATH ;
	{
		PREFIX_PATH  = this.getClass().getClassLoader().getResource("")
														.getPath().substring(1) + "../../src/main/resources";
	}


	public Response(ChannelHandlerContext ctx,Request request) {
		this.ctx = ctx;
		this.request = request;
	}

	/**
	 * 在路由信息表中存在与否判断动静态请求
	 * 然后在分别处理
	 */
	public void send(){
		Method method ;
		//在actionMap 中判断静态 还是 动态请求
		if( (method =isDynamic(request.getMethod() , request.getUri())) != null)
			 sendDynamic(method);
		else
			sendStatic();
	}

	private Method isDynamic(HttpMethod method , String uri) {
		return ActionUtil.getMethod(method,uri);
	}
	public void sendDynamic(Method method){

		AbstractFilter chain = FilterUtil.getFilterChain(method);
		if (chain != null){
			chain.doChain(request , this);
		}

		AbstractAction action = ActionUtil.getAction(method.getDeclaringClass());
		//TODO
		action.doAction( request, this , method , action);
		sendText();
	}

	/**
	 * 静态资源的发送
	 */
	public void sendStatic(){
		String uri ;
		try {
			uri = request.getUri();
			sendFile(PREFIX_PATH+uri);
		} catch (IOException e) {
			uri = BasicConfig.notFoundPath;
			try {
				sendFile(PREFIX_PATH+uri);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}



	private void setContext(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	private void sendEmptyLast() {
		ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
	}
	public void setFile(String path) {
		this.file = new File(path);
	}
	public void setHeader(String headerNames , Object value){
		headers.set(headerNames,value);
	}
	public void setContentType(String contentType){
		headers.set(HttpHeaderNames.CONTENT_TYPE,contentType);
	}
	public void setContent(String content){
		this.content = Unpooled.copiedBuffer(content.getBytes());
	}
	public void setStatus(HttpResponseStatus status){
		this.STATUS = status;
	}
	/**
	 *  浏览器跳转
	 *  将状态码改成302
	 */
	public void doRedirect(String url){
		setStatus(HttpResponseStatus.FOUND);  //302状态
		setHeader(HttpHeaderNames.LOCATION.toString(), url);
		sendText();
	}
	public String fileToContentType(String fileName) throws FileNotFoundException {
		if (fileName.endsWith(".html")) {
			return "text/html;charset=utf8";
		} else if (fileName.endsWith(".png")) {
			return "image/png";
		} else if (fileName.endsWith(".jpg")) {
			return "image/jpg";
		} else if (fileName.endsWith(".css")) {
			return "text/html;charset=utf8";
		} else if (fileName.endsWith(".js")) {
			return "application/javascript";
		}else if(fileName.endsWith(".ico")){
			return "image/x-icon";
		}
		throw new FileNotFoundException("路径错误");
	}
	public void sendFile(String path) throws IOException {
		//构造Response,设置状态码 和 Http版本
		//不要用成DefaultFullHttpResponse
		HttpResponse back = new DefaultHttpResponse(HTTP_VERSION, STATUS);
		setFile(path);
		RandomAccessFile raf = null;
		raf = new RandomAccessFile(file, "r");
		//设置header
		// 1、MimeType
		// 2、Length
		setContentType(fileToContentType(path));
		setHeader(HttpHeaderNames.CONTENT_LENGTH.toString(), raf.length());
		back.headers().set(headers);
		////先发送头部
		ctx.write( back );
		////发送数据部分
		ChunkedFile chunkedFile = new ChunkedFile(raf, 0, raf.length(), CHUNK_SIZE);
		Future future = ctx.writeAndFlush(new ChunkedFile(raf, 0, raf.length(), CHUNK_SIZE)
				, ctx.newProgressivePromise());
		sendEmptyLast();
	}

	/**
	 * 发送文本内容或者是无内容的跳转
	 */
	private void sendText(){
		HttpResponse back =
				new DefaultFullHttpResponse(HTTP_VERSION, STATUS , content);
		setHeader(HttpHeaderNames.CONTENT_LENGTH.toString(), content.readableBytes());
		setContentType(DEFAULT_CONTENT_TYPE);
		back.headers().set(headers);
		ctx.writeAndFlush(back);
	}
	public  void addCookie(Cookie cookie){
		setHeader("Set-Cookie",cookie);
	}
}
