package com.hd.mall.http;

import java.io.Serializable;

public class BaseResp<T> implements Serializable {
    //错误编码（0认为是没有任何错误，其他自定义）
    public Integer code = 0;
    //只有当code 不等于0时，msg必须有值，次值为错误说明
    public String msg;
    //返回结果，只有当code等于0时，此字段必须有值；
    public T result;
    public BaseResp(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
    public BaseResp(Integer code, String msg, T result) {
        super();
        this.code = code;
        this.msg = msg;
        this.result = result;
    }
    public BaseResp() {
        super();
    }
}
