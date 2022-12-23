package cn.batim.common.thread;

import java.util.concurrent.*;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/23 12:17
 */
public class BatThreadPool {
    private static final Object LOCK = new Object();
    private static ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            10,
            1000,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new BatThreadFactory(),
            new ThreadPoolExecutor.DiscardPolicy());

    public static void execute(Runnable runnable) {
        synchronized (LOCK) {
            THREAD_POOL_EXECUTOR.execute(runnable);
        }
    }
}
