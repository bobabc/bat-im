package cn.batim.common.model.msg;

import cn.batim.common.consts.BatConst;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 消息
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/23 17:30
 */
@AllArgsConstructor
@ToString
@Getter
public class BatMsg {
    private BatConst.Cmd cmd;
    private String me;
    private String to;
    private String id;
    private long time;
    private String body;

    public BatMsg() {
        this.id = RandomUtil.simpleUUID();
        time = System.currentTimeMillis();
    }
    public BatMsg(BatConst.Cmd cmd) {
        this.cmd = cmd;
        this.id = RandomUtil.simpleUUID();
        time = System.currentTimeMillis();
    }

    public BatMsg setCmd(BatConst.Cmd cmd) {
        this.cmd = cmd;
        return this;
    }

    public BatMsg setMe(String me) {
        this.me = me;
        return this;
    }

    public BatMsg setTo(String to) {
        this.to = to;
        return this;
    }

    public BatMsg setId(String id) {
        this.id = id;
        return this;
    }

    public BatMsg setTime(long time) {
        this.time = time;
        return this;
    }

    public BatMsg setBody(String body) {
        this.body = body;
        return this;
    }

    public BatMsg convert() {
        BatMsg batMsg = new BatMsg(this.getCmd());
        BeanUtil.copyProperties(this, batMsg);
        return batMsg;
    }
}
