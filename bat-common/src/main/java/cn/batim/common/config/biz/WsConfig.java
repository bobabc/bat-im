package cn.batim.common.config.biz;

/**
 * WebSocket 配置
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 10:22
 */
public interface WsConfig {
    /**
     * Host
     *
     * @return
     */
    String host();

    /**
     * Post
     *
     * @return
     */
    int port();
}
