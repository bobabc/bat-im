package cn.batim.server.listener.event.impl;

import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.model.reponse.R;
import cn.batim.common.service.BatClusterKit;
import cn.batim.common.service.BatGroupKit;
import cn.batim.common.service.BatUserGroupKit;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatKit;
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
        R<String> ret = BatKit.sendOtg(msg);
        if (!ret.success()){
            log.info("发送失败:{}",ret.getMsg());
            BatChannelKit.pub(session, ret.getMsg());
        }
    }
}
