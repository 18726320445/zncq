package com.igeek.zncq.exception;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/14 18:44
 * @email liuyia2022@163.com
 */
public class AddException extends RuntimeException {
    public AddException() {
        super();
    }

    public AddException(String message) {
        super(message);
    }
}
