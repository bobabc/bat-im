package cn.batim.server.handler;

import cn.batim.common.consts.BatConst;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.common.model.msg.BatSessionMsg;
import cn.batim.server.listener.event.BatEventParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 断开处理 Handler
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 10:52
 */
@Slf4j
public class CloseWebSocketHandler extends SimpleChannelInboundHandler<CloseWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
        Channel channel = ctx.channel();
        BatSession session = BatSessionKit.getByChannelId(channel);
        if (session != null) {
            // 处理连接消息
            BatSessionMsg batSessionMsg = BatSessionMsg.getInstance(session.getClient(), BatConst.Cmd.CLIENT_DISCONNECT);
            batSessionMsg.setBatSession(session).setMe(session.getUserId());
            BatEventParser.parse(session, batSessionMsg);
        }
    }
}
