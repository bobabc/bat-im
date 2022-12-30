package cn.batim.server;

import cn.batim.common.config.BatConfig;
import cn.batim.common.config.biz.WsConfig;
import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.thread.BatThreadPool;
import cn.batim.server.common.kit.BatKit;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.common.model.msg.BatSessionMsg;
import cn.batim.server.handler.ServerHandlerInitializer;
import cn.batim.server.listener.event.BatEventParser;
import cn.batim.server.listener.redis.BatRedisMessageKit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;

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
        // 开启集群监听
        if (BatConfig.me().isCluster()){
            BatThreadPool.execute(()->{
                BatRedisMessageKit.sub(ClusterChannel.PUBLIC_MSG);
            });
        }
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
        BatKit.allOffLine();
        if (channel != null) {
            channel.close();
        }
        group.shutdownGracefully();
    }

}
