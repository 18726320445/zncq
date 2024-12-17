package com.igeek.zncq.exception;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/8 18:02
 * @email liuyia2022@163.com
 */
public class FileUpLoadException extends RuntimeException{
    public FileUpLoadException() {
        super();
    }

    public FileUpLoadException(String message) {
        super(message);
    }

    public FileUpLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUpLoadException(Throwable cause) {
        super(cause);
    }
}
