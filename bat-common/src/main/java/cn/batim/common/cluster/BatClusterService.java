package cn.batim.common.cluster;

import cn.batim.common.model.msg.impl.BatClusterMsg;

/**
 * 集群消息
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 14:54
 */
public interface BatClusterService {
    /**
     * 发送消息
     *
     * @param batClusterMsg
     */
    void send(BatClusterMsg batClusterMsg);

}
