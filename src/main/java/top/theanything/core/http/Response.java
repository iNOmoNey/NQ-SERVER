package top.theanything.core.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.concurrent.Future;
import top.theanything.core.action.AbstractAction;
import top.theanything.core.enums.HttpMethod;
import top.theanything.util.ActionUtil;
import java.io.*;
import java.lang.reflect.Method;


/**
 * @author zhou
 * @Description
 * @createTime 2020-03-26
 */
public class Response {

	private final HttpVersion HTTP_VERSION = HttpVersion.HTTP_1_1;
	private HttpHeaders headers = new DefaultHttpHeaders();
	private final HttpResponseStatus STATUS = HttpResponseStatus.OK;
	private File file ;
	private ChannelHandlerContext ctx ;
	private Request request;
	private final int CHUNK_SIZE = 8192;

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

		AbstractAction action = ActionUtil.getAction(method.getDeclaringClass());
		//TODO 调用方法
	}

	public void sendStatic(){
        String uri = request.getUri();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("/");
        String path = this.getClass().getClassLoader().getResource("").getPath().substring(1) + "../../src/main/resources"+uri;
		try {
			sendFile(path);
		} catch (IOException e) {
			System.out.println("文件发送遇到异常");
			System.out.println("==============");
			e.printStackTrace();
		}
	}
	private void setContext(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	private void sendFile(String path) throws IOException {
		//构造Response,设置状态码 和 Http版本
		HttpResponse response =
				new DefaultHttpResponse(HTTP_VERSION, STATUS);
		setFile(path);
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			//TODO 返回404 文件找不到
		}
		//设置header
		// 1、MimeType
		// 2、Length

		setContentType(fileToContentType(path));

		setHeader(HttpHeaderNames.CONTENT_LENGTH.toString(), raf.length());


		response.headers().set(headers);

		////先发送头部
		ctx.write(response);

		////发送数据部分
		ChunkedFile chunkedFile = new ChunkedFile(raf, 0, raf.length(), CHUNK_SIZE);
		Future future = ctx.writeAndFlush(new ChunkedFile(raf, 0, raf.length(), CHUNK_SIZE)
				, ctx.newProgressivePromise());

		sendEmptyLast();
	}


	private void sendEmptyLast() {
		ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

	}

	public void setFile(String path) {
		File file = new File(path);
		this.file = file;
	}
	public void setHeader(String headerNames , Object value){
		headers.set(headerNames,value);
	}
	public void setContentType(String contentType){
		headers.set(HttpHeaderNames.CONTENT_TYPE,contentType);
	}

	public String fileToContentType(String fileName){
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
		throw new NullPointerException("没有对应的MimeType:"+fileName);
	}
}
