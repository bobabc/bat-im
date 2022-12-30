package cn.batim.server.handler;

import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.reponse.R;
import cn.batim.common.model.user.BatChannelUser;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatMsgKit;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.BatEventParser;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息处理
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/23 18:15
 */
@Slf4j
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel channel = ctx.channel();
        BatChannelUser batSessionUser = BatChannelKit.getSession(channel);
        if (batSessionUser == null) {
            BatChannelKit.pub(channel, "鉴权失败！");
            return;
        }
        //增加消息的引用计数，并将它写到ChannelGroup中所有已经连接的客户端
        String text = msg.text();
        log.info("text:{}", text);
        try {
            BatSession batSession = BatSessionKit.getByChannelId(batSessionUser.getUserId(), channel);
            if (batSession == null) {
                BatChannelKit.pub(channel, "请成重新登录！");
                return;
            }
            BatMsg batMsg = JSONObject.parseObject(text, BatMsg.class);
            batMsg.setMe(batSessionUser.getUserId());
            // 消息格式检查
            R<String> ret = BatMsgKit.checkMsg(batMsg);
            if (!ret.success()) {
                BatChannelKit.pub(channel, ret.getMsg());
            } else {
                BatEventParser.parse(batSession, batMsg);
            }
        } catch (Exception e) {
            BatChannelKit.pub(channel, "消息体格式错误！");
        }
    }

}
