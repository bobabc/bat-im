package cn.batim.common.queue;

import cn.batim.common.consts.GlobalCode;
import cn.batim.common.exception.BatException;
import cn.batim.common.model.BatCallBack;
import cn.batim.common.thread.BatThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 通用队列
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/23 16:50
 */
@Slf4j
public class BatQueue<T> {
    private BlockingQueue<T> queue;
    private String queueName;

    public static <T> BatQueue<T> getInstance(String queueName) {
        return new BatQueue<T>().create(queueName);
    }

    private BatQueue<T> create(String queueName) {
        this.queueName = queueName;
        queue = new LinkedBlockingQueue<>();
        return this;
    }

    public void put(T t) {
        try {
            if (queue != null) {
                queue.put(t);
            } else {
                throw new BatException(GlobalCode.PARAM_3000, "请先创建队列");
            }
        } catch (InterruptedException e) {
            log.info("put queue exception:{} ", e.getMessage(), e);
        }
    }

    public void put(T t, long delay, TimeUnit unit) {
        BatThreadPool.execute(() -> {
            put(t);
        }, delay, unit);
    }

    public BlockingQueue<T> getQueue() {
        return queue;
    }

    public void start(BatCallBack<T> commonCallBack) {
        if (queue == null) {
            throw new BatException(GlobalCode.PARAM_3000, "请先创建队列");
        }
        log.info("开启队列:{}", queueName);
        BatThreadPool.execute(() -> {
            while (true) {
                try {
                    commonCallBack.call(queue.take());
                } catch (InterruptedException e) {
                    log.info("queue error:{}", e.getMessage(), e);
                }
            }
        });
    }
}
