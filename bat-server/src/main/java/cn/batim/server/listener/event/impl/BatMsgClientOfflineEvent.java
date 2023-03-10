package cn.batim.server.listener.event.impl;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.service.BatUserClientKit;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.common.model.msg.BatSessionMsg;
import cn.batim.server.listener.event.BatEvent;
import cn.batim.server.listener.event.BatEventParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 终端下线
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgClientOfflineEvent extends BatEvent implements BatConst {
    /**
     * 消息处理
     *
     * @param msg
     */
    @Override
    protected void act(BatSession session, BatMsg msg) {
        if (msg instanceof BatSessionMsg) {
            BatSessionMsg batSessionMsg = (BatSessionMsg) msg;
            log.info("终端下线:{}",batSessionMsg);
            // 现在的终端Session
            BatSession batSession = batSessionMsg.getBatSession();
            BatConst.Client client = batSessionMsg.getClient();
            String userId = batSessionMsg.getMe();
            // 删除Session
            BatSessionKit.remove(batSession);
            // 删除终端
            BatUserClientKit.remove(userId, client);
            // 断开Session
            List<BatSession> sessions = BatSessionKit.list(userId, client);
            for (BatSession bSession : sessions) {
                // 不能删除当前Session
                if (!bSession.getId().equals(batSession.getId())){
                    BatSessionKit.remove(bSession);
                    BatSessionKit.close(bSession);
                }
            }
            // 用户是否在线
            boolean onLine = BatUserClientKit.isOnLine(userId);
            if (!onLine) {
                // 全部终端下线
                BatMsg batMsg = batSessionMsg.convert();
                batMsg.setCmd(Cmd.USER_LOGIN_OUT);
                BatEventParser.parse(session, batMsg);
            }

        }
    }
}
