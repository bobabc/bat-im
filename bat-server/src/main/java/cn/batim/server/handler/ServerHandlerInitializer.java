package cn.batim.server.handler;

import cn.batim.common.config.BatConfig;
import cn.batim.common.consts.BatConst;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/23 18:08
 */
public class ServerHandlerInitializer extends ChannelInitializer<Channel> implements BatConst {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        BatConfig batConfig = BatConfig.me();
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        // http
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        // 支持异步发送大的码流。
        pipeline.addLast(new ChunkedWriteHandler());
        if (batConfig.isCheckToken()) {
            // 权限
            pipeline.addLast(new FilterHandler());
        }
        // Ping
        pipeline.addLast(new PongWebSocketHandler());
        // 关闭
        pipeline.addLast(new CloseWebSocketHandler());
        pipeline.addLast(new TextWebSocketHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(HttpPath.WEB_SOCKET, true));
    }
}
