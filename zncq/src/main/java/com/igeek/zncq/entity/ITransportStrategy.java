package com.igeek.zncq.entity;

import com.igeek.zncq.vo.TransportDto;

/**
 * @author liuyi
 */
public interface ITransportStrategy {
    Object doTransport(TransportDto transportDto);
}
