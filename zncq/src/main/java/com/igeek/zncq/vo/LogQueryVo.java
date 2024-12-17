package com.igeek.zncq.vo;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/10 18:33
 * @email liuyia2022@163.com
 */

import lombok.Data;
import sun.dc.pr.PRError;

@Data
public class LogQueryVo {
    private Integer pageNum;
    private Integer userId;
    private String ip;
}
