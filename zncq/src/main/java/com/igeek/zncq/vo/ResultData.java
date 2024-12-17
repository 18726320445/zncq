package com.igeek.zncq.vo;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/7 20:42
 * @email liuyia2022@163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultData<T> {
    private Integer code;
    private String message;
    private T data;
    public ResultData(Integer code , String message){
        this.code = code;
        this.message = message;
    }
}
