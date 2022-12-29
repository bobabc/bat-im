package cn.batim.server.listener.event.impl;

import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.service.BatClusterKit;
import cn.batim.common.service.BatGroupKit;
import cn.batim.common.service.BatUserGroupKit;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.BatEvent;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * 群聊
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgOtgEvent extends BatEvent {
    @Override
    protected void act(BatSession session, BatMsg msg) {
        log.info("群组消息：{}", msg);
        String groupId = msg.getTo();
        if (StringUtils.isNotEmpty(groupId)) {
            Set<String> userList = BatUserGroupKit.getJoinedUserList(groupId);
            if (CollectionUtils.isNotEmpty(userList)) {
                for (String userId : userList) {
                    // 转发消息
                    List<BatSession> sessionList = BatSessionKit.list(userId);
                    for (BatSession batSession : sessionList) {
                        BatChannelKit.send(batSession, msg);
                    }
                }
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
