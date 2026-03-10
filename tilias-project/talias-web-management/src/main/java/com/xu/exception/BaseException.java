package com.xu.exception;

/**
 * 基础异常，用于作为 RuntimeException 的子异常
 *
 * 作用是防止其它 RuntimeException 的子类异常不能通过方法重载被正确处理
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
