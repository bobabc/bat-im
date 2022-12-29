package cn.batim.common.config;

import cn.batim.common.cache.BatCache;
import cn.batim.common.cache.local.LocalCache;
import cn.batim.common.cache.redis.RedisCache;
import cn.batim.common.config.biz.RedisConfig;
import cn.batim.common.config.biz.SslConfig;
import cn.batim.common.config.biz.WsConfig;
import cn.batim.common.consts.GlobalCode;
import cn.batim.common.exception.BatException;
import cn.batim.common.listener.BatMsgListener;
import cn.batim.common.cluster.BatClusterService;
import cn.batim.common.service.BatLoginService;
import cn.batim.common.cluster.redis.BatRedisClusterKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 配置文件
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/27 17:27
 */
@Slf4j
@Data
public class BatConfig {
    private static BatConfig batConfig;
    private RedisConfig redisConfig;
    private SslConfig sslConfig;
    private WsConfig wsConfig;
    private BatCache batCache;
    private BatClusterService batClusterService;
    /**
     * 登录业务
     */
    private BatLoginService batLoginService;
    /**
     * 事件注册
     */
    public BatMsgListener batMsgListener = BatMsgListener.getInstance();
    /**
     * 是否Token拦截
     */
    private boolean checkToken = true;
    /**
     * 是否集群，集群必须开启Redis
     */
    private boolean cluster = false;

    /**
     * 配置入口
     */
    public BatConfig() {
    }

    private void initConfig() {
        RedisConfig redisConfig = this.getRedisConfig();
        if (redisConfig == null) {
            batCache = new LocalCache();
        } else {
            batCache = new RedisCache();
        }
        if (isCluster()) {
            if (batClusterService == null) {
                if (redisConfig == null) {
                    log.error("集群模式必须配置Redis");
                    System.exit(0);
                }
                // 如果为空，则默认使用Redis集群
                batClusterService = new BatRedisClusterKit();
            }
        }
        log.info("集群模式:{}", isCluster());
        log.info("缓存文件:{}", batCache.getClass().getName());
        log.info("配置文件初始化完成！");
    }

    public static void init(BatConfig config) {
        if (batConfig == null) {
            batConfig = config;
        }
        batConfig.initConfig();
    }

    public static BatConfig me() {
        if (batConfig == null) {
            throw new BatException(GlobalCode.PARAM_3000, "请先初始化配置文件！");
        }
        return batConfig;
    }

}
