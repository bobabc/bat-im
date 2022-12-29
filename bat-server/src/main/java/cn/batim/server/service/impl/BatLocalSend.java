package cn.batim.server.service.impl;

import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatSessionKit;
import cn.batim.server.common.model.BatQueueMsg;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.service.BatSend;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 实时发送
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 18:26
 */
public class BatLocalSend implements BatSend {
    /**
     * 消息发送
     *
     * @param msg
     */
    @Override
    public void send(BatQueueMsg msg) {
        // TODO 测试
        List<BatSession> all = BatSessionKit.all();
        if (CollectionUtils.isNotEmpty(all)) {
            for (BatSession batSession : all) {
                BatChannelKit.send(batSession, msg.getBatMsg());
            }
        }
    }
}
