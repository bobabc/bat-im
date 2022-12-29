package cn.batim.server.demo;

import cn.batim.common.config.biz.WsConfig;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 11:24
 */
public class MyWsConfig implements WsConfig {
    /**
     * Host
     *
     * @return
     */
    @Override
    public String host() {
        return "127.0.0.1";
    }

    /**
     * Post
     *
     * @return
     */
    @Override
    public int port() {
        return 9091;
    }
}
