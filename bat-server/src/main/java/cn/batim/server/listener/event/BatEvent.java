package cn.batim.server.listener.event;

import cn.batim.common.model.msg.BatMsg;
import cn.batim.server.common.model.BatSession;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/3 22:27
 */
public abstract class BatEvent {
    /**
     * 消息处理
     *
     * @param msg
     */
    protected abstract void act(BatSession session, BatMsg msg);
}
