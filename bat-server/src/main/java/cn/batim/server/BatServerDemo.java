package cn.batim.server;

import cn.batim.common.config.BatConfig;
import cn.batim.common.consts.BatConst;
import cn.batim.common.model.user.BatChannelUser;
import cn.batim.server.demo.DemoMsgListener;
import cn.batim.server.demo.MyRedisConfig;
import cn.batim.server.demo.MyWsConfig;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 10:57
 */
@Slf4j
public class BatServerDemo {
    public static void main(String[] args) {
        BatConfig batConfig = new BatConfig();
        // Token校验，必须
        batConfig.setBatLoginService(token -> new BatChannelUser("123"));

        // 开启Token拦截，默认开启
        batConfig.setCheckToken(true);

        // Redis配置
//        batConfig.setRedisConfig(new MyRedisConfig());
        // WebSocket配置
        batConfig.setWsConfig(new MyWsConfig());

        //---------------------- 事件监听，根据需求添加 ------------------------
        batConfig.batMsgListener.add(BatConst.Cmd.OTO, new DemoMsgListener());
        batConfig.batMsgListener.add(BatConst.Cmd.OTO, new DemoMsgListener());

        // 初始化配置（必须）
        BatConfig.init(batConfig);

        // 创建服务
        BatServer wsServer = new BatServer();
        ChannelFuture channelFuture = wsServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("系统关闭");
            wsServer.destroy();
        }));
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
