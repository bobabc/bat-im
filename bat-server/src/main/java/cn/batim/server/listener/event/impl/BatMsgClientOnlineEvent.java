package cn.batim.server.listener.event.impl;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.service.BatUserClientKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.common.model.msg.BatSessionMsg;
import cn.batim.server.listener.event.BatEvent;
import cn.batim.server.listener.event.BatEventParser;
import lombok.extern.slf4j.Slf4j;

/**
 * 终端上线
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgClientOnlineEvent extends BatEvent implements BatConst {
    /**
     * 消息处理
     *
     * @param msg
     */
    @Override
    protected void act(BatSession session, BatMsg msg) {
        if (msg instanceof BatSessionMsg) {
            BatSessionMsg batSessionMsg = (BatSessionMsg) msg;
            BatSession batSession = batSessionMsg.getBatSession();

            // 用户是否在线
            boolean onLine = BatUserClientKit.isOnLine(batSession.getUserId());
            // 终端是否在线
            boolean clientOnLine = BatUserClientKit.isOnLine(batSession.getUserId(), batSession.getClient());
            if (clientOnLine) {
                // 断开其他终端
                BatSessionMsg offClientSessionMsg = BatSessionMsg.getInstance(batSession.getClient(), Cmd.CLIENT_OFFLINE);
                offClientSessionMsg.setMe(batSession.getUserId());
                BatEventParser.parse(session, offClientSessionMsg);
            }
            // 存储终端
            BatUserClientKit.save(batSession.getUserId(), batSession.getClient());
            if (!onLine) {
                // 全部终端不在线，触发用户登录事件
                BatMsg batMsg = batSessionMsg.convert();
                batMsg.setCmd(Cmd.USER_LOGIN);
                BatEventParser.parse(session, batMsg);
            }
        }
    }
}
