package cn.batim.server.listener.event.impl;

import cn.batim.common.model.group.BatGroupInfo;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.service.BatGroupKit;
import cn.batim.common.service.BatUserGroupKit;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.BatEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 *  删除群组
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgGroupRemoveEvent extends BatEvent {
    @Override
    protected void act(BatSession session, BatMsg msg) {
        log.info("删除群组:{}",msg);
        String groupId = msg.getTo();
        BatGroupInfo batGroupInfo = BatGroupKit.get(msg.getMe(), groupId);
        if (batGroupInfo == null){
            // 不是自己创建的群组不能删除
            BatChannelKit.pub(session, "删除失败：无权限");
            return;
        }
        Set<String> joinedUserList = BatUserGroupKit.getGroupUserList(groupId);
        if (CollectionUtils.isNotEmpty(joinedUserList)){
            // 群组内有成员不能删除
            BatChannelKit.pub(session, "删除失败：成员非空");
            return;
        }
        BatGroupKit.remove(msg.getMe(), groupId);
    }
}
