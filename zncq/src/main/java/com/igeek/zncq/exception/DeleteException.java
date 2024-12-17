package com.igeek.zncq.exception;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/14 18:45
 * @email liuyia2022@163.com
 */
public class DeleteException extends RuntimeException{
    public DeleteException() {
        super();
    }

    public DeleteException(String message) {
        super(message);
    }
}
