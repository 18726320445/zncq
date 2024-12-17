package com.igeek.zncq.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.igeek.zncq.entity.Transport;
import com.igeek.zncq.entity.TransportExample;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.mapper.OrderMapper;
import com.igeek.zncq.mapper.TransportMapper;
import com.igeek.zncq.vo.OrderVo;
import com.igeek.zncq.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/10 19:51
 * @email liuyia2022@163.com
 */
@RestController
@RequestMapping("/map")
@Slf4j
public class MapController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    TransportMapper transportMapper;
    @Autowired
    JedisPool jedisPool;
    @GetMapping()
    public ResultData getPoint(@RequestParam("orderNo") String orderNo){
        ResultData<Object> resultData = new ResultData<>();
        OrderVo orderVo = orderMapper.selectOneVehicleByOrderNo(orderNo);
        if (orderVo == null){
            throw new AddException("该订单不存在");
        }
        Integer id = orderVo.getVehicle().getId();
        TransportExample transportExample = new TransportExample();
        transportExample.createCriteria().andVehicleIdEqualTo(id).andStateEqualTo(1);
        List<Transport> transports = transportMapper.selectByExample(transportExample);
        if (transports == null || transports.size() == 0){
            throw new AddException("该订单不在运输中");
        }
        Transport transport = transports.get(0);
        String startAddress = transport.getStartAddress();
        String endAddress = transport.getEndAddress();
        Jedis jedis = jedisPool.getResource();
        String midAddress = null;
        try {
            midAddress = jedis.get("transport_" + transport.getId() + "_midAddress");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        Map<String,Double[]> res = new HashMap<>();
        res.put("position", getMap(startAddress));
        res.put("endPosition",getMap(endAddress));
        res.put("midPosition",getMap(midAddress));
        resultData.setMessage("查询成功");
        resultData.setCode(200);
        resultData.setData(res);
        return resultData;
    }

    public Double[] getMap(String address){
        String ak = "wOqur5cmGvoks3eGnnHl4j3IdHwtmFt2";
        String url = "https://api.map.baidu.com/place/v2/suggestion?&city_limit=true&output=json&";
        String[] split = address.split(",");
        String body = HttpRequest.get(url)
                .form("ak", ak)
                .form("query", split[1])
                .form("region", split[0])
                .execute().body();
        Double[] location = new Double[2];
        JSONObject entries = JSONUtil.parseObj(body);
        List<JSONObject> list = (List<JSONObject>) entries.get("result");
        for (JSONObject jsonObject : list) {
            JSONObject res = (JSONObject)jsonObject.get("location");
            if (res != null){
                BigDecimal lat = (BigDecimal) res.get("lat");
                BigDecimal lng = (BigDecimal) res.get("lng");
                location[0] = lng.doubleValue();
                location[1] = lat.doubleValue();
            }
        }
        return location;
    }

}
