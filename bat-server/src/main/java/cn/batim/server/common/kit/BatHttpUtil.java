package cn.batim.server.common.kit;

import cn.batim.common.consts.BatConst;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 14:35
 */
public class BatHttpUtil {
    /**
     * 获取参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParams(FullHttpRequest request) {
        Map<String, String> headerMap = new HashMap<>(10);
        String uri = request.uri();
        if (uri.contains(BatConst.Symbol.QM)) {
            String p = uri.split("\\" + BatConst.Symbol.QM)[1];
            String[] params = p.split("&");
            for (String param : params) {
                String[] strings = param.split("=");
                headerMap.put(strings[0], strings[1]);
            }
        }
        return headerMap;
    }

    /**
     * 获取头文件
     *
     * @param request
     * @return
     */
    public static Map<String, String> getHeader(HttpMessage request) {
        Map<String, String> headerMap = new HashMap<>(10);
        HttpHeaders headers = request.headers();
        for (Map.Entry<String, String> header : headers) {
            headerMap.put(header.getKey(), header.getValue());
        }
        return headerMap;
    }

    public static boolean isWebSocket(HttpMessage request) {
        String upgrade = getHeader(request).get("Upgrade");
        if (StringUtils.isNotEmpty(upgrade) && upgrade.equals(BatConst.Symbol.WEBSOCKET)) {
            return true;
        }
        return false;
    }
    public static boolean isWebSocket(Map<String, String> headers) {
        String upgrade = headers.get("Upgrade");
        if (StringUtils.isNotEmpty(upgrade) && upgrade.equals(BatConst.Symbol.WEBSOCKET)) {
            return true;
        }
        return false;
    }
}
