package top.theanything.core.handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;

/**
 * @author zhou
 * @Description
 * @createTime 2020-03-26
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            Request request = new Request(ctx,msg);
            Response response = new Response(ctx,request);
            response.send();
    }
}
