package cn.batim.server.demo;

import cn.batim.common.model.BatCallBack;
import cn.batim.common.model.msg.BatMsg;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 11:26
 */
@Slf4j
public class DemoMsgListener implements BatCallBack<BatMsg> {

    /**
     * 回调
     *
     * @param msg
     */
    @Override
    public void call(BatMsg msg) {
        log.info("msg:{}" , msg);
    }
}
