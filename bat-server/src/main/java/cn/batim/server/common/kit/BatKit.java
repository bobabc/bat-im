package cn.batim.server.common.kit;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.server.listener.event.BatEventParser;

/**
 * 对外公共工具
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 15:00
 */
public class BatKit implements BatConst {

    /**
     * 用于接收集群消息
     *
     * @param batMsg
     */
    public static void onMessage(BatClusterMsg batMsg) {
        if (!batMsg.getNodeId().equals(BAT_ID)) {
            BatEventParser.parse(null, batMsg);
        }
    }
}
