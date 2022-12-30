package cn.batim.server.listener.event.impl;

import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.reponse.R;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatKit;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.BatEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 广播
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgPubEvent extends BatEvent {
    @Override
    protected void act(BatSession session, BatMsg msg) {
        log.info("广播：{}", msg);
        R<String> ret = BatKit.sendPub(msg);
        if (!ret.success()) {
            log.error("推送失败:{}", ret.getMsg());
        }
    }
}
