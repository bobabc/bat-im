package cn.batim.server.listener.event.impl;

import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.service.BatClusterKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.BatEvent;
import lombok.extern.slf4j.Slf4j;

/**
 *  用户退出
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgUserLoginOutEvent extends BatEvent {
    @Override
    protected void act(BatSession session, BatMsg msg) {
        log.info("用户下线：{}", msg);
        String userId = msg.getMe();
        // 推送好友&已加入群组消息

        // 集群通知
        if (!(msg instanceof BatClusterMsg)) {
            BatClusterMsg batClusterMsg = BatClusterMsg.getInstance(msg.getCmd());
            batClusterMsg.setMe(userId);
            BatClusterKit.send(batClusterMsg);
        }
    }
}
