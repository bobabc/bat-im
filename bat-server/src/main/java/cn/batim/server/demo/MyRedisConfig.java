package cn.batim.server.demo;

import cn.batim.common.config.biz.RedisConfig;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 11:13
 */
public class MyRedisConfig implements RedisConfig {
    /**
     * Host
     *
     * @return
     */
    @Override
    public String host() {
        return "101.34.48.99";
    }

    /**
     * Port
     *
     * @return
     */
    @Override
    public int port() {
        return 6379;
    }

    /**
     * 密码
     *
     * @return
     */
    @Override
    public String auth() {
        return "Xtgjfge_1234";
    }

    @Override
    public int retryNum() {
        return 100;
    }

    @Override
    public int database() {
        return 0;
    }

    @Override
    public int maxActive() {
        return 100;
    }

    @Override
    public int maxIdle() {
        return 20;
    }

    @Override
    public long maxWait() {
        return 5000L;
    }

    @Override
    public int timeout() {
        return 2000;
    }
}
