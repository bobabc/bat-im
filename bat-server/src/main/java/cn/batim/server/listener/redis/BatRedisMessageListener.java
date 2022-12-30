package cn.batim.server.listener.redis;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.server.listener.event.BatEventParser;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPubSub;

/**
 * Redis 队列监听
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 20:36
 */
@Slf4j
public class BatRedisMessageListener extends JedisPubSub implements BatConst {
    @Override
    public void onMessage(String channel, String message) {
        if (channel.equals(ClusterChannel.PUBLIC_MSG.name())) {
            BatClusterMsg batClusterMsg = JSONObject.parseObject(message, BatClusterMsg.class);
            if (!batClusterMsg.getNodeId().equals(BAT_ID)){
                BatEventParser.parse(null, batClusterMsg);
            }else {
                log.info("自己集群消息，不处理！");
            }
        } else if (channel.equals(ClusterChannel.SERVER_MSG_ME.name())) {

        }
    }
}
