/*
* Copyright 2013-2016 Fenqile  Co ., Ltd. All Rights Reserved.
*
*/

package me.qiancheng.qianworks.meicai.base;

import java.io.Serializable;

/**
 * @author josephyan<千橙> on 6/13/2016.
 */
public class Result<T> implements Serializable {

    protected int code;

    protected String msg;

    protected T data;

    public Result(Code code) {
        this(code, null);
    }

    public Result(Code code, T data) {
        this(code.getCode(), code.getMsg(), data);
    }

    public Result(int code, String msg) {
        this(code, msg, null);
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
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
}
 
