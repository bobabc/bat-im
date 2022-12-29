package cn.batim.server.listener.event.impl;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.service.BatClusterKit;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.BatEvent;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 一对一消息
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgOtoEvent extends BatEvent implements BatConst {
    @Override
    protected void act(BatSession session, BatMsg msg) {
        log.info("单聊消息：{}", msg);
        String to = msg.getTo();
        if (StringUtils.isNotEmpty(to)) {
            // 转发消息
            List<BatSession> sessionList = BatSessionKit.list(to);
            for (BatSession batSession : sessionList) {
                BatChannelKit.send(batSession, msg);
            }
            // 转发集群
            if (!(msg instanceof BatClusterMsg)) {
                BatClusterMsg batClusterMsg = BatClusterMsg.getInstance(msg.getCmd());
                BeanUtil.copyProperties(msg, batClusterMsg);
                BatClusterKit.send(batClusterMsg);
            }
        }
    }
}
