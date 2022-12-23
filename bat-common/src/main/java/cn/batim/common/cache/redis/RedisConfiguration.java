package cn.batim.common.cache.redis;

import cn.batim.common.config.BatConfigUtil;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * @author bob
 */
@ToString
@Data
public class RedisConfiguration {

    private int retryNum = 100;
    private int database = 0;
    private int maxActive = 100;
    private int maxIdle = 20;
    private long maxWait = 5000L;
    private int timeout = 2000;
    private String auth;
    private String host = "";
    private int port = 0;

    public RedisConfiguration() {
        this.database = BatConfigUtil.getInt("redis.database", 0);
        this.retryNum = BatConfigUtil.getInt("redis.retrynum", 100);
        this.maxActive = BatConfigUtil.getInt("redis.maxactive", 100);
        this.maxIdle = BatConfigUtil.getInt("redis.maxidle", 20);
        this.maxWait = BatConfigUtil.getLong("redis.maxwait", 5000L);
        this.timeout = BatConfigUtil.getInt("redis.timeout", 2000);
        this.auth = BatConfigUtil.get("redis.auth", null);
        if (StringUtils.isEmpty(auth)) {
            this.auth = null;
        }
        this.host = BatConfigUtil.get("redis.host", "");
        this.port = BatConfigUtil.getInt("redis.port", 0);
    }
}
