package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.ProduceConsume;
import com.igeek.zncq.entity.Stock;
import com.igeek.zncq.entity.StockExample;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.ContainerMapper;
import com.igeek.zncq.mapper.ProduceConsumeMapper;
import com.igeek.zncq.mapper.StockMapper;
import com.igeek.zncq.mapper.WarehouseMapper;
import com.igeek.zncq.service.IStockService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.StockQueryVo;
import com.igeek.zncq.vo.StockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 15:30
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class StockServiceImpl implements IStockService {
    @Autowired
    StockMapper stockMapper;
    @Autowired
    ProduceConsumeMapper produceConsumeMapper;
    @Autowired
    WarehouseMapper warehouseMapper;
    @Autowired
    ContainerMapper containerMapper;

    @Override
    public PageVo<StockVo> findAllByPage(Integer pageNum) {
        PageVo<StockVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<StockVo> stockVos = stockMapper.selectAllByPage();
        PageInfo<StockVo> pageInfo = new PageInfo<>(stockVos,5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;

    }

    @Override
    public PageVo<StockVo> findAllByQueryPage(StockQueryVo queryVo) {
        PageVo<StockVo> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(),7);
        List<StockVo> stockVos = stockMapper.selectAllByQuery(queryVo);
        PageInfo<StockVo> pageInfo = new PageInfo<>(stockVos,5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public void produce(ProduceConsume produceConsume) {
        //查看原料数量是否符合库存以及产品容器是否可以装下
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andWarehouseIdEqualTo(produceConsume.getWarehouseId()).andContainerIdEqualTo(produceConsume.getRawContainerId());
        List<Stock> stocks = stockMapper.selectByExample(stockExample);
        Stock stock = stocks.get(0);
        if (stock.getNum() < produceConsume.getRawNum()){
            throw new UpdateException("库存原料数量比输入的消耗数量小");
        }
        stockExample.clear();
        stockExample.createCriteria().andWarehouseIdEqualTo(produceConsume.getWarehouseId()).andContainerIdEqualTo(produceConsume.getGoodContainerId());
        List<Stock> stocks1 = stockMapper.selectByExample(stockExample);
        Stock stock1 = stocks1.get(0);
        Long num = warehouseMapper.selectContainerNumByWarehouseId(produceConsume.getGoodContainerId(), produceConsume.getWarehouseId());
        Container container = containerMapper.selectByPrimaryKey(produceConsume.getGoodContainerId());
        if (stock1.getNum() + produceConsume.getGoodNum() > num * container.getMaxCapacity()){
            throw new UpdateException("你所选的产品容器无法存储该数量的产品");
        }
        //都满足库存变化
        //原料减少
        Stock stock2 = new Stock();
        stock2.setNum(stock.getNum() - produceConsume.getRawNum());
        stockExample.clear();
        stockExample.createCriteria().andWarehouseIdEqualTo(produceConsume.getWarehouseId()).andContainerIdEqualTo(produceConsume.getRawContainerId());
        int i1 = stockMapper.updateByExampleSelective(stock2, stockExample);
        if (i1 != 1){
            throw new UpdateException("库存更新异常,重新操作");
        }
        //产品增多
        Stock stock3 = new Stock();
        stock3.setNum(stock1.getNum() + produceConsume.getGoodNum());
        stockExample.clear();
        stockExample.createCriteria().andWarehouseIdEqualTo(produceConsume.getWarehouseId()).andContainerIdEqualTo(produceConsume.getGoodContainerId());
        int i = stockMapper.updateByExampleSelective(stock3, stockExample);
        if (i != 1){
            throw new UpdateException("库存更新异常,重新操作");
        }
        //创建生产记录
        int res = produceConsumeMapper.insertSelective(produceConsume);
        if (res != 1){
            throw new AddException("生产记录异常,重新操作");
        }
    }

    @Override
    public Map<String, Integer> findAllRawByGoodType(Integer typeId) {
        List<Map<String, Object>> maps = stockMapper.findAllByGoodType(typeId);
        HashMap<String, Integer> map = new HashMap<>();
        maps.stream().forEach(stringIntegerMap -> {
            map.put(String.valueOf((String)stringIntegerMap.get("goodName")),((BigDecimal)stringIntegerMap.get("num")).intValue());
        });
        return map;
    }

    @Override
    public List<StockVo> warn() {
        List<StockVo> stockVos = stockMapper.warn();
        return stockVos;
    }

}
