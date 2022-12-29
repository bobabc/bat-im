package cn.batim.common.cluster.redis;

import cn.batim.common.cluster.BatClusterService;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import lombok.extern.slf4j.Slf4j;

/**
 * 集群消息实现(基于Redis)
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 15:06
 */
@Slf4j
public class BatRedisClusterKit implements BatClusterService {
    /**
     * 发送消息
     *
     * @param batClusterMsg
     */
    @Override
    public void send(BatClusterMsg batClusterMsg) {
        log.info("发送Redis集群：{}", batClusterMsg);
    }
}
