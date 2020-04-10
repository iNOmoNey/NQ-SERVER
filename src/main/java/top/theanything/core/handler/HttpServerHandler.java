package top.theanything.core.handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.concurrent.Future;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @param
 * @author zhou
 * @Description
 * @createTime 2020-03-26
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final HttpVersion HTTP_VERSION = HttpVersion.HTTP_1_1;
    private HttpHeaders headers = new DefaultHttpHeaders();
    private final HttpResponseStatus STATUS = HttpResponseStatus.OK;
    private File file ;
    private ChannelHandlerContext ctx ;
    private final int CHUNK_SIZE = 8192;


    /**
     * @title
     * @description
     * 1、设置请求头
     * 2、图标处理 返回图标文件
     * 3、非图片请求，返回相应文本
     * @updateTime 2020/04/05 17:55
     * @throws
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            Request request = new Request(ctx,msg);
            Response response = new Response(ctx,request);
            response.send();

            /*
            HttpRequest request = (HttpRequest) msg;
            String uri = request.uri();
            if(uri.equals("/favicon.ico") || uri.equals("/logo.png")){
                System.out.println("接收到图片请求");
                InputStream input = this.getClass().getClassLoader().getResourceAsStream("/");
                String path = this.getClass().getClassLoader().getResource("").getPath().substring(1) + "../../src/main/resources"+uri;

                sendFile(path);
                return;

            }
            // 构造消息
            ByteBuf buf = Unpooled.copiedBuffer("我是Netty服务器", CharsetUtil.UTF_8);
            //构造Response
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    HTTP_VERSION,
                    STATUS,
                    buf
            );
//            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

            headers.set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset=utf-8");
            headers.set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());

            response.headers().set(headers);
            //发送消息
            ctx.writeAndFlush(response);
            */

    }

    private void setContext(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }


    private void sendFile(String path) throws IOException {
        //构造Response,设置状态码 和 Http版本
        HttpResponse response =
                new DefaultHttpResponse(HTTP_VERSION, STATUS);

        setFile(path);

        RandomAccessFile raf = new RandomAccessFile(file, "r");
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
        if (fileName.endsWith("ico")){
            return "image/x-icon";
        }else if(fileName.endsWith("png")){
            return "image/png";
        }
        throw new NullPointerException("没有对应的MimeType");
    }

}
