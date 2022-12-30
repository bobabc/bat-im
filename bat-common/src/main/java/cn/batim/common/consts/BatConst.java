package cn.batim.common.consts;

import cn.hutool.core.util.RandomUtil;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/22 18:08
 */
public interface BatConst {
    /**
     * 本机唯一ID
     */
    String BAT_ID = RandomUtil.simpleUUID().toUpperCase();

    /**
     * 集群频道
     */
    enum ClusterChannel {
        /**
         * 公共频道
         */
        PUBLIC_MSG,
        /**
         * 专线
         */
        SERVER_MSG_ME
    }

    interface Number {
        Integer ONE = 1;
        Integer TWO = 2;
        Integer THREE = 3;
        Integer FOUR = 4;
    }

    /**
     * 请求路径
     */
    interface HttpPath {
        String WEB_SOCKET = "/ws";
    }

    /**
     * 字符串常量
     */
    interface Symbol {
        String QM = "?";
        String WEBSOCKET = "websocket";
        String HTTP = "http";
        String TOKEN = "token";
        String CLIENT = "client";
    }

    /**
     * 缓存Key
     */
    interface CacheKey {
        String BASE_KEY = "bat:";
        String USER = BASE_KEY + "user:";
    }

    /**
     * 时间
     */
    interface Time {
        long SECOND_TIME = 1000;
    }

    /**
     * 用户客户端类型
     */
    enum Client {
        APP("app"),
        WX("wx"),
        WEB("web"),
        ;
        private String name;

        Client(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Client of(String name) {
            Client[] values = Client.values();
            for (Client client : values) {
                if (client.getName().equals(name)) {
                    return client;
                }
            }
            return null;
        }
    }

    /**
     * 命令
     */
    enum Cmd {
        /************用户发起命令************/
        /**
         * 一对一
         */
        OTO,
        /**
         * 群组
         */
        OTG,
        /**
         * 广播
         */
        PUB,

        /************服务器发起命令************/
        /**
         * 用户登录操作
         */
        USER_LOGIN,
        /**
         * 用户退出操作
         */
        USER_LOGIN_OUT,
        /**
         * 终端上线
         */
        CLIENT_ONLINE,
        /**
         * 终端下线
         */
        CLIENT_OFFLINE,
        /**
         * 群组创建
         **/
        GROUP_CREATE,
        /**
         * 群组删除
         **/
        GROUP_REMOVE,
        /**
         * 加入
         **/
        USER_GROUP_JOINED,
        /**
         * 离开
         **/
        USER_GROUP_LEAVE,
    }
}
