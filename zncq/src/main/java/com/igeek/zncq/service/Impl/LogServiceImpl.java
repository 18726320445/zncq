package com.igeek.zncq.service.Impl;

import com.igeek.zncq.entity.Log;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.mapper.LogMapper;
import com.igeek.zncq.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/3 20:42
 * @email liuyia2022@163.com
 */
@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    LogMapper logMapper;

    @Override
    public void save(Log log) {
        int insert = logMapper.insert(log);
        if (insert != 1){
            throw new AddException("日志生成失败");
        }
    }
}
