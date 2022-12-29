package cn.batim.server;

import cn.batim.server.handler.SslServerHandlerInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/23 18:21
 */
public class BatSslServer extends BatServer {
    private final SslContext context;

    public BatSslServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer() {
        //返回之前创建的SecureChatServerInitializer以启用加密
        return new SslServerHandlerInitializer(context);
    }

}
