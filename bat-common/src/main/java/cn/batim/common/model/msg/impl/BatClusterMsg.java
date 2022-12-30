package cn.batim.common.model.msg.impl;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import lombok.Getter;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:35
 */
@Getter
public class BatClusterMsg extends BatMsg {
    /**
     * 节点ID
     */
    private String nodeId;

    public BatClusterMsg() {
    }

    public BatClusterMsg(BatConst.Cmd cmd) {
        super(cmd);
        setNodeId(BatConst.BAT_ID);
    }

    public static BatClusterMsg getInstance(BatConst.Cmd cmd) {
        return new BatClusterMsg(cmd);
    }


    public BatClusterMsg setNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }
}
