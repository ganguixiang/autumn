package com.ggxspace.autumn.vo;

import java.io.Serializable;

/**
 * Created by ganguixiang on 2017/9/29.
 */
public class Result<T> implements Serializable {

    /**
     * 请求成功
     */
    public static final int SUCCESS = 0;
    /**
     * 请求失败
     */
    public static final int FAIL = 1;
    /**
     * 没有权限
     */
    public static final int NO_PERMISSION = 2;

    /**
     * 状态码
     */
    private int code = SUCCESS;

    /**
     * 消息
     */
    private String message = "success";

    /**
     * 要返回的数据
     */
    private T data;


    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static <T> Result ok(T data) {
        return new Result(data);
    }

    public static <T> Result ok() {
        return new Result(null);
    }

//    public Result(Throwable e) {
//        this.message = e.toString();
//        this.code = FAIL;
//    }

    public static Result error() {
        return error("error");
    }

    public static Result error(String message) {
        Result result = new Result();
        result.setCode(1);
        result.setMessage(message);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
