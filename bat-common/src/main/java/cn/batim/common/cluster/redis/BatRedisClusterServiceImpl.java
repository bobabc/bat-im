package cn.batim.common.cluster.redis;

import cn.batim.common.cache.redis.JedisTemplate;
import cn.batim.common.cluster.BatClusterService;
import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.service.BatClusterKit;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 集群消息实现(基于Redis)
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 15:06
 */
@Slf4j
public class BatRedisClusterServiceImpl implements BatClusterService {
    /**
     * 发送消息
     *
     * @param batClusterMsg
     */
    @Override
    public void send(BatClusterMsg batClusterMsg) {
        JedisTemplate.getInstance().publish(BatConst.ClusterChannel.PUBLIC_MSG.name(), JSONObject.toJSONString(batClusterMsg));
    }
}
