package cn.batim.server.listener.event.impl;

import cn.batim.common.model.group.BatGroupInfo;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.reponse.R;
import cn.batim.common.service.BatGroupKit;
import cn.batim.common.service.BatUserGroupKit;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.listener.event.BatEvent;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 群组创建
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:52
 */
@Slf4j
public class BatMsgGroupCreateEvent extends BatEvent {
    /**
     * 消息处理
     *
     * @param msg
     */
    @Override
    protected void act(BatSession session, BatMsg msg) {
        log.info("群组创建：{}", msg);
        String body = msg.getBody();
        if (StringUtils.isNotEmpty(body)) {
            try {
                BatGroupInfo batGroupInfo = JSONObject.parseObject(body, BatGroupInfo.class);
                if (StringUtils.isEmpty(batGroupInfo.getName())) {
                    BatChannelKit.pub(session, "群组名称不能为空");
                    return;
                }
                batGroupInfo.setCreator(msg.getMe());
                R<String> group = BatKit.createGroup(batGroupInfo);
                if (!group.success()) {
                    BatChannelKit.pub(session, group.getMsg());
                }
            } catch (Exception e) {
                log.info("创建群组，消息体格式错误:{}", body);
            }
        }
    }
}
