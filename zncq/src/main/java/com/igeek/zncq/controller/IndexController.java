package com.igeek.zncq.controller;

import cn.hutool.core.date.DateUtil;
import com.igeek.zncq.entity.InStorage;
import com.igeek.zncq.entity.OutStorage;
import com.igeek.zncq.service.*;
import com.igeek.zncq.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/8 09:24
 * @email liuyia2022@163.com
 */
@RestController
@Slf4j
@RequestMapping("/index")
public class IndexController {
    @Autowired
    IOutStorageService outStorageService;
    @Autowired
    IInStorageService inStorageService;
    @Autowired
    IStockService stockService;
    @Autowired
    IPurchaseService purchaseService;
    @Autowired
    IContractService contractService;
    @Autowired
    IWarehouseService warehouseService;
    @Autowired
    JedisPool jedisPool;
    @GetMapping("/count")
    public ResultData getCount(){
        Map<String,Integer> map = new HashMap<>(5);
        List<OutStorage> outStorages = outStorageService.findByNullDate();
        map.put("outStorages",outStorages.size());
        List<InStorage> inStorages = inStorageService.findByNullDate();
        map.put("inStorages",inStorages.size());
        Integer count1 = purchaseService.countByState(1);
        map.put("purchases",count1);
        Integer count2 = contractService.countByState(1);
        map.put("contracts",count2);
        Integer count3 = warehouseService.countByWarn();
        map.put("warn",count3);
        ResultData<Map> resultData = new ResultData<>();
        resultData.setCode(200);
        resultData.setData(map);
        return resultData;
    }

    @GetMapping("/goodTopSeven")
    public ResultData<Map> findByGoodTopSeven(){
        Jedis jedis = jedisPool.getResource();
        Map<String, Integer> map = new HashMap<>(7);
        try {
            int i = DateUtil.weekOfYear(new Date());
            String key = "Week_" + i;
            Set<Tuple> tuples = jedis.zrangeWithScores(key, 0, -1);
            if (tuples == null || jedis.ttl(key) < 0){
                map = outStorageService.findPreWeekGoodSumTopSeven();
                String key1 = "Week_" + DateUtil.weekOfYear(new Date());
                jedis.pexpire(key1,1000*60*60*24*7);
                try {
                    map.forEach((k,v)->jedis.zadd(key,v.doubleValue(),k));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jedis.close();
                }
            }else{
                Map<String, Integer> finalMap = map;
                tuples.stream().forEach(tuple -> finalMap.put(tuple.getElement(), (int) tuple.getScore()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return new ResultData<>(200,null,map);
    }

    @GetMapping("/raw")
    public ResultData<Map<String,Integer>> findAllRaw(){
        ResultData<Map<String, Integer>> resultData = new ResultData<>();
        Map<String, Integer> map = stockService.findAllRawByGoodType(1);
        resultData.setCode(200);
        resultData.setData(map);
        return resultData;
    }

}
