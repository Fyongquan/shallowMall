package com.fyq.common.utils;

import org.apache.http.HttpStatus;

import java.io.Serializable;

/**
 * 通用返回结果封装类（支持泛型）
 *
 * @param <T> 数据类型
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    public Result() {}

    public static <T> Result<T> ok() {
        return new Result<>(0, "success");
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(0, "success", data);
    }

    public static <T> Result<T> ok(String msg) {
        return new Result<>(0, msg);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(0, msg, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}