package cn.batim.common.config.biz;

/**
 * Redis 配置
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 10:07
 */
public interface RedisConfig {
    /**
     * Host
     *
     * @return
     */
    String host();

    /**
     * Port
     *
     * @return
     */
    int port();

    /**
     * 密码
     *
     * @return
     */
    String auth();

    int retryNum();

    int database();

    int maxActive();

    int maxIdle();

    long maxWait();

    int timeout();
}
