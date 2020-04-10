package top.theanything.core.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import top.theanything.core.init.ServerInit;
import top.theanything.util.CommonUtils;

/**
 * @param
 * @author zhou
 * @Description
 * @createTime 2020-03-20
 */
public class NQ_Server {


    public void run()  {
        CommonUtils utils = CommonUtils.getInstance();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInit());
            ChannelFuture future = serverBootstrap.bind(Integer.valueOf(String.valueOf(utils.getValue("port")))).sync();
            future.addListener(new GenericFutureListener<Future<? super Void>>() {

                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                        utils.printSuccess();
                }
            });

        }catch (Exception e){
            System.out.println("有异常");
            e.printStackTrace();
        } finally{

        }
    }
}
