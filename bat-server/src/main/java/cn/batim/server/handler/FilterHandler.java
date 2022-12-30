package cn.batim.server.handler;

import cn.batim.common.config.BatConfig;
import cn.batim.common.consts.BatConst;
import cn.batim.common.model.user.BatChannelUser;
import cn.batim.common.service.BatLoginService;
import cn.batim.server.common.BatServerConsts;
import cn.batim.server.common.kit.BatChannelKit;
import cn.batim.server.common.kit.BatHttpUtil;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.common.model.msg.BatSessionMsg;
import cn.batim.server.listener.event.BatEventParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 权限拦截
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 13:54
 */
@Slf4j
public class FilterHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        if (msg instanceof FullHttpRequest) {
            // 开始握手校验
            FullHttpRequest request = (FullHttpRequest) msg;
            log.info("uri:{}", request.uri());
            if (BatHttpUtil.isWebSocket(request)) {
                Map<String, String> params = BatHttpUtil.getParams(request);
                String token = params.get(BatConst.Symbol.TOKEN);
                String clientName = params.get(BatConst.Symbol.CLIENT);
                if (StringUtils.isEmpty(token)) {
                    BatChannelKit.close(channel);
                    return;
                }
                BatChannelUser user = this.getUser(token);
                if (user == null) {
                    BatChannelKit.close(channel);
                    return;
                }
                log.info("登录成功");
                BatConst.Client client = BatConst.Client.of(clientName);
                if (client == null){
                    client = BatConst.Client.WEB;
                }
                channel.attr(BatServerConsts.BAT_SESSION).setIfAbsent(user);
                // 处理连接消息
                BatSessionMsg batSessionMsg = BatSessionMsg.getInstance(client, BatConst.Cmd.CLIENT_ONLINE);
                BatSession batSession = new BatSession(user.getUserId(), channel);
                batSession.setClient(client);
                batSessionMsg
                        .setBatSession(batSession)
                        .setClient(client)
                        .setMe(user.getUserId());
                BatEventParser.parse(batSession, batSessionMsg);
            } else {
                String token = request.headers().get(BatConst.Symbol.TOKEN);
                if (StringUtils.isEmpty(token)) {
                    BatChannelKit.close(channel);
                    return;
                }
                BatChannelUser user = this.getUser(token);
                if (user == null) {
                    BatChannelKit.close(channel);
                    return;
                }
            }
        }
        ctx.fireChannelRead(msg);
    }

    /**
     * 登录
     *
     * @param token
     * @return
     */
    private BatChannelUser getUser(String token) {
        BatLoginService batLoginService = BatConfig.me().getBatLoginService();
        if (batLoginService == null) {
            log.warn("未知注册登录事件");
            return null;
        }
        return batLoginService.login(token);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete:{}", ctx.channel().id());
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught::",cause);
        BatChannelKit.close(ctx.channel());
    }
}
