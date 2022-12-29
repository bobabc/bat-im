package cn.batim.server.service;

import cn.batim.server.common.model.BatQueueMsg;

/**
 * 消息发送
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 17:24
 */
public interface BatSend {
    /**
     * 消息发送
     *
     * @param msg
     */
    void send(BatQueueMsg msg);
}
