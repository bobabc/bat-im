package cn.batim.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/23 18:08
 */
public class SslServerHandlerInitializer extends ServerHandlerInitializer {
    private final SslContext context;

    public SslServerHandlerInitializer(SslContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //调用父类的initChannel（）方法
        super.initChannel(channel);
        SSLEngine engine = context.newEngine(channel.alloc());
        engine.setUseClientMode(false);
        //将SslHandler添加到ChannelPipeline中
        channel.pipeline().addFirst(new SslHandler(engine));
    }
}
