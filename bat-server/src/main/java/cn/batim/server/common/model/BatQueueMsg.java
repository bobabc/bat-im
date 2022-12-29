package cn.batim.server.common.model;

import cn.batim.common.model.msg.BatMsg;
import lombok.Data;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 17:28
 */
@Data
public class BatQueueMsg {
    private BatMsg batMsg;

    public BatQueueMsg() {
    }
    public BatQueueMsg(BatMsg batMsg) {
        this.batMsg = batMsg;
    }
}
