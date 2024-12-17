package com.igeek.zncq.quartz;

import cn.hutool.core.date.DateUtil;
import com.igeek.zncq.service.IOutStorageService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;


/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/8 13:06
 * @email liuyia2022@163.com
 */
public class UpDateWeekOutGoodRank extends QuartzJobBean {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    IOutStorageService outStorageService;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //获取上一整周出库销量前7的货物和数量;
        Map<String,Integer> map = outStorageService.findPreWeekGoodSumTopSeven();
        //将记录存入Redis 后面访问直接redis中取
        Jedis jedis = jedisPool.getResource();
        String key = "Week_" + DateUtil.weekOfYear(new Date());
        jedis.pexpire(key,1000*60*60*24*7);
        try {
            map.forEach((k,v)->jedis.zadd(key,v.doubleValue(),k));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
