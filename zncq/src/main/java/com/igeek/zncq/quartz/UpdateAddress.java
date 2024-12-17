package com.igeek.zncq.quartz;

import com.igeek.zncq.entity.Transport;
import com.igeek.zncq.entity.TransportExample;
import com.igeek.zncq.log.LogSys;
import com.igeek.zncq.mapper.TransportMapper;
import com.igeek.zncq.service.Impl.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/7 13:56
 * @email liuyia2022@163.com
 */
@Slf4j
@Transactional(rollbackFor = {})
public class UpdateAddress extends QuartzJobBean {

    private final List<Integer> list = new ArrayList<>();

    @Autowired
    EmailService emailService;
    @Autowired
    JedisPool jedisPool;
    @Autowired
    TransportMapper transportMapper;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Jedis resource = jedisPool.getResource();
        try {
            TransportExample transportExample = new TransportExample();
            transportExample.createCriteria().andStateEqualTo(1);
            List<Transport> transports = transportMapper.selectByExample(transportExample);
            list.clear();
            transports.stream().forEach(transport ->{
                Integer id = transport.getId();
                String midAddressKey = "transport_"+id +"_midAddress";
                String address = resource.get(midAddressKey);
                if (address == null){
                    //如果缓存没有这在数据库中寻找并添加到redis
                    resource.set("transport_" + transport.getId() + "_midAddress",transport.getMidAddress());
                }else{
                    //看是否及时更新
                    if (address.equals(transport.getMidAddress())){
                        list.add(id);
                    }else {
                        transport.setMidAddress(address);
                        transportMapper.updateByPrimaryKey(transport);
                    }
                }
            });
            if (list.size() != 0){
                String content = "运单号为：" + list.toString() + "没有更新地点请注意。建议询问司机缘由";
                emailService.sendMessage("1445211359@qq.com","运输管理",content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
    }
}
