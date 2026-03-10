package com.xu.exception;

public class UnsupportedOperationTypeException extends RuntimeException {
    public UnsupportedOperationTypeException(String type) {
        super("注解参数 type 的取值非法：" + type + "。仅支持 insert 或 update。");
    }
}
