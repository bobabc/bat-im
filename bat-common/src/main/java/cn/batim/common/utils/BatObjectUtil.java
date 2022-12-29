package cn.batim.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 对象操作
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/27 16:40
 */
public class BatObjectUtil {
    /**
     * json字符串转换对象
     *
     * @param s
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T jsonConvertObject(String s, Class<T> t) {
        return JSONObject.parseObject(s, t);
    }

    /**
     * json字符串数组转换对象
     *
     * @param list
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonConvertObject(List<String> list, Class<T> t) {
        List<T> result = Lists.newArrayList();
        for (String s : list) {
            result.add(jsonConvertObject(s, t));
        }
        return result;
    }
}
