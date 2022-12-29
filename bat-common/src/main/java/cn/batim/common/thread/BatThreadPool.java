package cn.batim.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/23 12:17
 */
@Slf4j
public class BatThreadPool {
    private static final Object LOCK = new Object();
    private static BatThreadFactory batThreadFactory = new BatThreadFactory("task");
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(), batThreadFactory);
    private static ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            1000,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            batThreadFactory,
            new ThreadPoolExecutor.DiscardPolicy());

    public static void execute(Runnable runnable) {
        synchronized (LOCK) {
            THREAD_POOL_EXECUTOR.execute(runnable);
        }
    }

    public static void execute(Runnable runnable, long delay, TimeUnit unit) {
        synchronized (LOCK) {
            executorService.schedule(runnable, delay, unit);
        }
    }
}
