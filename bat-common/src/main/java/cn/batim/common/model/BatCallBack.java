package cn.batim.common.model;

/**
 * 通用回调
 *
 * @author bob
 * @version 1.0
 * @date 2022/1/21 18:53
 */
public interface BatCallBack<T> {
    /**
     * 回调
     *
     * @param t
     */
    void call(T t);
}
