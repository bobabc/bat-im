package cn.batim.common.service;

import cn.batim.common.cluster.BatClusterService;
import cn.batim.common.config.BatConfig;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import lombok.extern.slf4j.Slf4j;

/**
 * 集群处理
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 15:10
 */
@Slf4j
public class BatClusterKit {
    /**
     * 发送集群消息
     *
     * @param batClusterMsg
     */
    public static void send(BatClusterMsg batClusterMsg) {
        if (BatConfig.me().isCluster()) {
            log.info("发送集群消息:{}", batClusterMsg);
            BatClusterService batClusterService = BatConfig.me().getBatClusterService();
            if (batClusterService == null) {
                log.warn("集群消息发送失败：集群未启用！");
            } else {
                batClusterService.send(batClusterMsg);
            }
        }
    }

}
