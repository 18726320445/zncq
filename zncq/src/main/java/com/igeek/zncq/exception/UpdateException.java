package com.igeek.zncq.exception;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/14 18:45
 * @email liuyia2022@163.com
 */
public class UpdateException extends RuntimeException{
    public UpdateException() {
        super();
    }

    public UpdateException(String message) {
        super(message);
    }
}
