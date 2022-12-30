package cn.batim.server.common.kit;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.user.BatChannelUser;
import cn.batim.server.common.BatServerConsts;
import cn.batim.server.common.model.BatSession;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 15:50
 */
@Slf4j
public class BatChannelKit {
    public static String getId(Channel channel) {
        return channel.id().asShortText();
    }

    /**
     * Session用户
     *
     * @param channel
     * @return
     */
    public static BatChannelUser getSession(Channel channel) {
        return channel.attr(BatServerConsts.BAT_SESSION).get();
    }

    public static void send(BatSession batSession, BatMsg msg) {
        send(batSession.getChannel(), msg);
    }

    public static void send(Channel channel, BatMsg msg) {
        if (channel != null && channel.isOpen()) {
            send(channel, JSONObject.toJSONString(msg));
        }
    }

    public static void send(Channel channel, String msg) {
        if (channel != null && channel.isOpen()) {
            send(channel, new TextWebSocketFrame(msg));
        }
    }

    public static void send(Channel channel, WebSocketFrame webSocketFrame) {
        if (channel != null && channel.isOpen()) {
            channel.writeAndFlush(webSocketFrame);
        }
    }

    public static void sendPing(Channel channel) {
        if (channel != null && channel.isOpen()) {
            channel.writeAndFlush(new PingWebSocketFrame());
        }
    }

    public static void close(Channel channel) {
        try {
            if (channel != null && channel.isOpen() && channel.isActive()) {
                channel.close();
            }
        } catch (Exception e) {
            log.info("关闭Channel异常:",e);
        }
    }

    /**
     * 发送广播消息
     *
     * @param msg
     */
    public static void pub(Channel channel, String msg) {
        BatMsg batMsg = new BatMsg(BatConst.Cmd.PUB);
        batMsg.setBody(msg);
        send(channel, batMsg);
    }

    /**
     * 广播消息
     *
     * @param session
     * @param msg
     */
    public static void pub(BatSession session, String msg) {
        if (session != null){
            pub(session.getChannel(), msg);
        }
    }
}
