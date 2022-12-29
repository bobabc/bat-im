package cn.batim.server.service.impl;

import cn.batim.server.common.model.BatQueueMsg;
import cn.batim.server.service.BatSend;
import lombok.extern.slf4j.Slf4j;

/**
 * 队列发送
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 17:26
 */
@Slf4j
public class BatQueueSend implements BatSend {

    static {
        log.info("初始化发送消息队列");
    }

    /**
     * 消息发送
     *
     * @param msg
     */
    @Override
    public void send(BatQueueMsg msg) {
    }
}
