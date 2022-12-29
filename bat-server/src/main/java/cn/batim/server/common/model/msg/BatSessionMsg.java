package cn.batim.server.common.model.msg;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.server.common.model.BatSession;
import lombok.Getter;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 14:26
 */
@Getter
public class BatSessionMsg extends BatMsg {
    private BatSession batSession;
    private BatConst.Client client;

    private BatSessionMsg(BatConst.Client client, BatConst.Cmd cmd) {
        super(cmd);
        setClient(client);
    }

    public static BatSessionMsg getInstance(BatConst.Client client, BatConst.Cmd cmd) {
        return new BatSessionMsg(client, cmd);
    }

    public BatSessionMsg setBatSession(BatSession batSession) {
        this.batSession = batSession;
        return this;
    }

    public BatSessionMsg setClient(BatConst.Client client) {
        this.client = client;
        return this;
    }
}
