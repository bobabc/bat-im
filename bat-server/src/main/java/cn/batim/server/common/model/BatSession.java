package cn.batim.server.common.model;

import cn.batim.common.consts.BatConst;
import cn.batim.server.common.kit.BatChannelKit;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.Getter;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 16:02
 */
@Getter
public class BatSession {
    private String userId;
    private String id;
    private Channel channel;
    private BatConst.Client client;

    public BatSession(String userId, Channel channel) {
        this.userId = userId;
        this.channel = channel;
        setId(BatChannelKit.getId(channel));
    }

    public BatSession(Channel channel) {
        this.channel = channel;
    }

    public BatSession setClient(BatConst.Client client) {
        this.client = client;
        return this;
    }

    public BatSession setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public BatSession setId(String id) {
        this.id = id;
        return this;
    }

    public BatSession setChannel(Channel channel) {
        this.channel = channel;
        return this;
    }
}
