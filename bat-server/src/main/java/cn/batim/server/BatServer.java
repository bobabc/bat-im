package cn.batim.server;

import cn.batim.common.config.BatConfig;
import cn.batim.common.config.biz.WsConfig;
import cn.batim.common.consts.BatConst;
import cn.batim.server.handler.ServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/22 18:19
 */
@Slf4j
public class BatServer implements BatConst {
    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public BatServer() {
    }

    public ChannelFuture start() {
        BatConfig batServerConfig = BatConfig.me();
        WsConfig wsConfig = batServerConfig.getWsConfig();
        InetSocketAddress address = new InetSocketAddress(wsConfig.host(), wsConfig.port());
        //引导服务器
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer());
        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        log.info("Web Socket started on host:{}, port:{}", address.getHostString(), address.getPort());
        channel = future.channel();
        return future;
    }

    /**
     * 创建ChatServerInitializer
     *
     * @return
     */
    protected ChannelInitializer<Channel> createInitializer() {
        return new ServerHandlerInitializer();
    }

    /**
     * 处理服务器关闭，并释放所有的资源
     */
    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        group.shutdownGracefully();
    }

}
