package com.phoenix.zsfast.common.vo;

import lombok.Data;

/**
 * 统一响应结果封装类
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    private Result() {}

    // 成功返回（带数据）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 成功返回（不带数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 失败返回
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}