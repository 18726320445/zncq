package com.igeek.zncq.map;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/10 09:47
 * @email liuyia2022@163.com
 */
@SpringBootTest
public class TestMap {


    @Test
    public void test1() {
        String ak = "wOqur5cmGvoks3eGnnHl4j3IdHwtmFt2";
        HttpRequest request = HttpRequest.post("https://yingyan.baidu.com/api/v3/entity/add");
        String body = request.form("ak", ak)
                .form("service_id", 235693)
                .form("entity_name", 37286234)
                .execute().body();
        System.out.println(body);

    }
    @Test
    public void test2(){
        String url = "https://api.map.baidu.com/place/v2/suggestion?query=北京市-东城区-前门东大街11号&region=北京&city_limit=true&output=json&ak=wOqur5cmGvoks3eGnnHl4j3IdHwtmFt2";
        String body = HttpRequest.get(url)
                .execute().body();
        System.out.println(body);
    }
    @Test
    public void test3(){
        String url = "https://api.map.baidu.com/directionlite/v1/driving?origin=40.01116,116.339303&destination=39.936404,116.452562&ak=wOqur5cmGvoks3eGnnHl4j3IdHwtmFt2";
        String body = HttpRequest.get(url)
                .execute().body();
        System.out.println(body);
        JSONObject entries = JSONUtil.parseObj(body);
        Object result = entries.get("result");
    }

    @Test
    public void test4(){
        String url = "https://yingyan.baidu.com/api/v3/track/addpoints";
        ArrayList<Object> points = new ArrayList<>();
        points.add(MapUtil.builder()
                .put("entity_name", "user_1")
                .put("longitude", 116.33964557069)
                .put("latitude", 40.01051873416)
                .put("loc_time", System.currentTimeMillis()/1000)
                        .put("speed",10.3)
                        .put("direction",15)
                .put("coord_type_input", "bd09ll").map());
        points.add(MapUtil.builder()
                .put("entity_name", "user_2")
                .put("longitude", 116.34000633018)
                .put("latitude", 40.010546434696)
                .put("loc_time", System.currentTimeMillis()/1000)
                .put("speed",10.3)
                .put("direction",15)
                .put("coord_type_input", "bd09ll").map());
        points.add(MapUtil.builder().put("entity_name", "user_3")
                .put("longitude", 116.34087804585)
                .put("latitude", 40.010561355679)
                .put("loc_time", System.currentTimeMillis()/1000)
                .put("speed",10.3)
                .put("direction",15)
                .put("coord_type_input", "bd09ll").map());
        String body = HttpRequest.post(url)
                .form("ak", "wOqur5cmGvoks3eGnnHl4j3IdHwtmFt2")
                .form("service_id", 235693)
                //116.33964557069,40.01051873416;116.34000633018,40.010546434696;116.34087804585,40.010561355679
                .form("point_list",JSONUtil.toJsonStr(points))
                .execute().body();
        System.out.println(body);
        System.out.println(JSONUtil.toJsonStr(points));
    }
}
