package cn.batim.server.listener.event;

import cn.batim.common.config.BatConfig;
import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.impl.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息处理
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:49
 */
@Slf4j
public class BatEventParser {
    private static Map<BatConst.Cmd, BatEvent> CMD_MAP;

    static {
        CMD_MAP = new HashMap<>(16);
        // 用户命令
        CMD_MAP.put(BatConst.Cmd.OTO, new BatMsgOtoEvent());
        CMD_MAP.put(BatConst.Cmd.OTG, new BatMsgOtgEvent());
        CMD_MAP.put(BatConst.Cmd.PUB, new BatMsgPubEvent());
        // 服务触发命令
        CMD_MAP.put(BatConst.Cmd.CLIENT_CONNECTED, new BatMsgClientConnectedEvent());
        CMD_MAP.put(BatConst.Cmd.CLIENT_DISCONNECT, new BatMsgClientDisconnectEvent());

        CMD_MAP.put(BatConst.Cmd.CLIENT_ONLINE, new BatMsgClientOnlineEvent());
        CMD_MAP.put(BatConst.Cmd.CLIENT_OFFLINE, new BatMsgClientOfflineEvent());

        CMD_MAP.put(BatConst.Cmd.GROUP_CREATE, new BatMsgGroupCreateEvent());
        CMD_MAP.put(BatConst.Cmd.GROUP_REMOVE, new BatMsgGroupRemoveEvent());

        CMD_MAP.put(BatConst.Cmd.USER_GROUP_JOINED, new BatMsgUserGroupJoinEvent());
        CMD_MAP.put(BatConst.Cmd.USER_GROUP_LEAVE, new BatMsgUserGroupLeaveEvent());

        CMD_MAP.put(BatConst.Cmd.USER_LOGIN, new BatMsgUserLoginEvent());
        CMD_MAP.put(BatConst.Cmd.USER_LOGIN_OUT, new BatMsgUserLoginOutEvent());
    }

    /**
     * 消息处理
     *
     * @param session 当前通信Session
     * @param msg
     */
    public static void parse(BatSession session, BatMsg msg) {
        BatEvent batEvent = CMD_MAP.get(msg.getCmd());
        if (batEvent != null) {
            batEvent.act(session, msg);
            // 注册事件消息推送
            BatConfig.me().batMsgListener.push(msg);
        } else {
            log.warn("未知消息事件:{}", msg.getCmd());
        }
    }
}
