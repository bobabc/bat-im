package cn.batim.server.listener.event.impl;

import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.model.reponse.R;
import cn.batim.common.service.BatClusterKit;
import cn.batim.common.service.BatUserGroupKit;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.BatEvent;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.login.LoginContext;

/**
 * 加入群组
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgUserGroupJoinEvent extends BatEvent {
    @Override
    protected void act(BatSession session, BatMsg msg) {
        log.info("加入群组：{}", msg);
        R<String> ret = BatKit.joinGroup(msg);
        if (!ret.success()){
            log.info("失败:{}",ret.getMsg());
            BatChannelKit.pub(session, ret.getMsg());
        }
    }
}
