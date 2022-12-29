package cn.batim.server.listener.event.impl;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.common.model.msg.BatSessionMsg;
import cn.batim.server.listener.event.BatEvent;
import cn.batim.server.listener.event.BatEventParser;
import lombok.extern.slf4j.Slf4j;

/**
 * 打开通道
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgClientConnectedEvent extends BatEvent implements BatConst {
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
            // 保存通道
            BatSessionKit.save(batSession);
            // 触发用户终端上线
            BatMsg batMsg = batSessionMsg.convert();
            batMsg.setCmd(Cmd.CLIENT_ONLINE);
            BatEventParser.parse(session, batMsg);
        }
    }
}
