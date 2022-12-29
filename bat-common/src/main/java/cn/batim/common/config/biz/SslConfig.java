package cn.batim.common.config.biz;

/**
 * 证书配置
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 10:29
 */
public abstract class SslConfig {
    /**
     * 证书路径
     *
     * @return
     */
    abstract String certPath();

    /**
     * 密码
     *
     * @return
     */
    abstract String pwd();
}
