package com.igeek.zncq.entity;

import com.igeek.zncq.vo.TransportDto;
import org.springframework.stereotype.Component;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/13 18:18
 * @email liuyia2022@163.com
 */
@Component
public class TransportContext {
    private TransportDto transportDto;
    private ITransportStrategy ITransportStrategy;

    public TransportContext() {
    }

    public TransportDto getTransportDto() {
        return transportDto;
    }

    public void setTransportDto(TransportDto transportDto) {
        this.transportDto = transportDto;
    }

    public ITransportStrategy getITransportStrategy() {
        return ITransportStrategy;
    }

    public void setAbstractTransportStrategy(ITransportStrategy ITransportStrategy) {
        this.ITransportStrategy = ITransportStrategy;
    }

    public Object execute() {
      return  ITransportStrategy.doTransport(transportDto);
    }
}
