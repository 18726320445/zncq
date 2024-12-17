package com.igeek.zncq.entity;

import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.InStorageMapper;
import com.igeek.zncq.mapper.OrderMapper;
import com.igeek.zncq.vo.TransportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/13 18:28
 * @email liuyia2022@163.com
 */
@Component
public class TranferStrategy implements ITransportStrategy{
    @Autowired
    InStorageMapper inStorageMapper;
    @Autowired
    OrderMapper orderMapper;
    @Override
    public Object doTransport(TransportDto transportDto) {
        //更改入库通知数量
        InStorageExample inStorageExample = new InStorageExample();
        inStorageExample.createCriteria().andOrderNoEqualTo(transportDto.getOrderNo()).andGoodIdEqualTo(transportDto.getGoodId());
        List<InStorage> inStorages = inStorageMapper.selectByExample(inStorageExample);
        InStorage inStorage = inStorages.get(0);
        int i = inStorageMapper.updateByExampleSelective(inStorage, inStorageExample);
        if (i != 1) {
            throw new UpdateException("回单异常,重新操作");
        }
        int i1 = orderMapper.updateStateByOrderNoInt(transportDto.getOrderNo(), 3);
        if (i1 != 1) {
            throw new UpdateException("回单异常,重新操作");
        }
        return (Object) inStorage;
    }
}
