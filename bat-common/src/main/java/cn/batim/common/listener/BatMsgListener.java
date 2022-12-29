package cn.batim.common.listener;

import cn.batim.common.consts.BatConst;
import cn.batim.common.model.BatCallBack;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.queue.BatQueue;
import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.compress.utils.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息命令监听
 *
 * @author bob
 * @version 1.0
 * @date 2022/12/28 11:21
 */
public class BatMsgListener implements BatConst {
    public static Map<Cmd, List<BatCallBack<BatMsg>>> msgListeners = new HashMap<>(5);
    private static BatQueue<BatMsg> batQueue = BatQueue.getInstance("event");
    static {
        batQueue.start(msg -> {
            List<BatCallBack<BatMsg>> batCallBacks = msgListeners.get(msg.getCmd());
            if (CollectionUtil.isNotEmpty(batCallBacks)) {
                for (BatCallBack<BatMsg> batCallBack : batCallBacks) {
                    batCallBack.call(msg);
                }
            }
        });
    }
    public static BatMsgListener getInstance() {
        return new BatMsgListener();
    }

    public void add(Cmd cmd, BatCallBack<BatMsg> callBack) {
        List<BatCallBack<BatMsg>> batCallBacks = msgListeners.computeIfAbsent(cmd, k -> Lists.newArrayList());
        batCallBacks.add(callBack);
    }

    public void push(BatMsg msg) {
        batQueue.put(msg);
    }
}
