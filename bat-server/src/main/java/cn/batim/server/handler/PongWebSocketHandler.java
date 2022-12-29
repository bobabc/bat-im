package cn.batim.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * Ping Handler
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 10:52
 */
@Slf4j
public class PongWebSocketHandler extends SimpleChannelInboundHandler<PongWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PongWebSocketFrame frame) {
        log.info("client pong");
        ctx.write(new PongWebSocketFrame(frame.content().retain()));
    }

}
