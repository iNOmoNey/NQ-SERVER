package top.theanything.core.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import top.theanything.core.handler.HttpServerHandler;
import top.theanything.utils.ServerScanner;

public class ServerInit extends ChannelInitializer<SocketChannel> {
    static{
        System.err.println("服务器正在初始化...");
        ServerScanner.scaner();
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new HttpServerCodec())
                        .addLast(new HttpObjectAggregator(65536)) //压缩HTTP请求
                        .addLast(new ChunkedWriteHandler())  //添加大文件支持
                        .addLast(new HttpServerHandler());
    }
}
