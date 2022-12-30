package cn.batim.server.listener.redis;

import cn.batim.common.cache.redis.JedisTemplate;
import cn.batim.common.consts.BatConst;
import cn.batim.server.listener.redis.BatRedisMessageListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 集群处理
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 15:10
 */
@Slf4j
public class BatRedisMessageKit {

    /**
     * 开启队列
     *
     * @param clusterChannel
     */
    public static void sub(BatConst.ClusterChannel clusterChannel) {
        log.info("开始监听集群:{}",clusterChannel);
        JedisTemplate.getInstance().subscribe(new BatRedisMessageListener(), clusterChannel.name());
    }
}
