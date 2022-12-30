package cn.batim.common.model.msg.impl;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import lombok.Getter;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:46
 */
@Getter
public class BatHttpProxyMsg extends BatMsg {
    private String token;

    public BatHttpProxyMsg() {
    }

    public BatHttpProxyMsg(BatConst.Cmd cmd) {
        super(cmd);
    }

    public static BatHttpProxyMsg getInstance(BatConst.Cmd cmd) {
        return new BatHttpProxyMsg(cmd);
    }

    public BatHttpProxyMsg setToken(String token) {
        this.token = token;
        return this;
    }
}
