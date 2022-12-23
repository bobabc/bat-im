package cn.batim.common.model.reponse;

import cn.batim.common.consts.GlobalCode;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 返回结果类
 *
 * @param <T>
 * @author bob
 * @version V1.0
 * @date 2019年4月8日 下午7:04:17
 */
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 全局响应码
     */
    private int code;
    /**
     * 全局响应码说明
     */
    private String msg;
    /**
     * 响应数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public R() {
    }

    public R(T data) {
        this(GlobalCode.SUCCESS.getCode(), GlobalCode.SUCCESS.getMsg(), null);
    }

    public R(int code, String msg) {
        this(code, msg, null);
    }

    public R(int code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean success() {
        return this.code == GlobalCode.SUCCESS.getCode();
    }

    public boolean fail() {
        return !this.success();
    }

    public static <T> R<T> ok() {
        RBuilder builder = new RBuilder();
        return builder.ok();
    }

    public static <T> R<T> ok(T body) {
        RBuilder builder = new RBuilder();
        return builder.ok(body);
    }

    public static <T> R<T> failure(int code, String msg) {
        RBuilder builder = new RBuilder();
        return builder.failure(code, msg, null);
    }

    public static <T> R<T> failure(GlobalCode globalCode, String msg) {
        RBuilder builder = new RBuilder();
        return builder.failure(globalCode.getCode(), msg, null);
    }

    public static <T> R<T> failure(GlobalCode globalCode) {
        RBuilder builder = new RBuilder();
        return builder.failure(globalCode.getCode(), globalCode.getMsg(), null);
    }

    public static <T> R<T> failure(int code, String msg, T body) {
        RBuilder builder = new RBuilder();
        return builder.failure(code, msg, body);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    private static class RBuilder {

        public <T> R<T> ok() {
            return ok(null);
        }

        public <T> R<T> ok(T body) {
            return new R<T>(GlobalCode.SUCCESS.getCode(), GlobalCode.SUCCESS.getMsg(), body);
        }

        public <T> R<T> failure(int code, String msg, T body) {
            return new R<T>(code, msg, body);
        }

        public <T> R<T> failure(GlobalCode globalCode, String msg, T body) {
            return new R<T>(globalCode.getCode(), msg, body);
        }
    }

    public String toJson() {
        return JSONUtil.toJsonStr(this);
    }
}
