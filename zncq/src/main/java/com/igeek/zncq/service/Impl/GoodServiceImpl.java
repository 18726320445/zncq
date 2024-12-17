package com.igeek.zncq.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.GoodExample;
import com.igeek.zncq.entity.StockExample;
import com.igeek.zncq.entity.WarehouseTransferExample;
import com.igeek.zncq.excel.GoodListener;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.mapper.GoodMapper;
import com.igeek.zncq.mapper.StockMapper;
import com.igeek.zncq.mapper.WarehouseTransferMapper;
import com.igeek.zncq.service.IGoodService;
import com.igeek.zncq.service.IPurchaseService;
import com.igeek.zncq.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/8 18:08
 * @email liuyia2022@163.com
 */
@Transactional(rollbackFor = {})
@Service
public class GoodServiceImpl implements IGoodService {
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    WarehouseTransferMapper warehouseTransferMapper;
    @Autowired
    IPurchaseService purchaseService;

    @Override
    public PageVo<GoodRawVo> goodRawDetail(Integer pageNum) {
        PageVo<GoodRawVo> pageVo = new PageVo<>();
        //开启分页
        PageHelper.startPage(pageNum, 7);
        List<GoodRawVo> goodVos = goodMapper.selectGoodForRawVoAll();
        //得到分页后的信息。
        PageInfo<GoodRawVo> pageInfo = new PageInfo<>(goodVos, 5);
        //组装需要的信息
        pageVo.setCurrentPage(pageNum);
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public Boolean addGoodRaw(Good good) {
        int result = goodMapper.insertSelective(good);
        return result == 1;
    }

    @Override
    public Integer deleteGoodRawByIds(Integer[] ids) {
        int count = goodMapper.countByGoodIdAndOrder(ids);
        if (count != 0) {
            throw new DeleteException("该货物有订单在计划中，无法删除");
        }
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andGoodIdIn(Arrays.asList(ids));
        if (stockMapper.countByExample(stockExample) != 0) {
            throw new DeleteException("该货物被容器占用，无法删除");
        }
        //从库存中删除
        stockMapper.deleteByExample(stockExample);
        //从移库中删除
        WarehouseTransferExample warehouseTransferExample = new WarehouseTransferExample();
        warehouseTransferExample.createCriteria().andGoodIdIn(Arrays.asList(ids));
        warehouseTransferMapper.deleteByExample(warehouseTransferExample);
        //从货物表中删除
        GoodExample goodExample = new GoodExample();
        goodExample.createCriteria().andGoodIdIn(Arrays.asList(ids));
        int res = goodMapper.deleteByExample(goodExample);
        return res;
    }

    @Override
    public Good selectGoodRawById(Integer id) {
        Good good = goodMapper.selectByPrimaryKey(id);
        return good;
    }

    @Override
    public PageVo<GoodRawVo> selectGoodRawByGoodQuery(GoodQuery goodQuery) {
        PageVo<GoodRawVo> pageVo = new PageVo<>();
        PageHelper.startPage(goodQuery.getPageNum(), 7);
        List<GoodRawVo> goodRawVos = goodMapper.selectGoodRawByGoodQuery(goodQuery);
        PageInfo<GoodRawVo> pageInfo = new PageInfo<>(goodRawVos, 5);
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setData(pageInfo.getList());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public Boolean updateGoodRawById(Good good) {
        int res = goodMapper.updateByPrimaryKeySelective(good);
        return res == 1;
    }

    @Override
    public List<Good> selectRawAll() {
        GoodExample goodExample = new GoodExample();
        goodExample.createCriteria().andGoodTypeIdEqualTo(1);
        List<Good> goods = goodMapper.selectByExample(goodExample);
        return goods;
    }

    @Override
    public List<Good> selectRawAllByPurchaseNo(String purchaseNo) {
        PurchaseVo purchaseVo = purchaseService.selectPurchaseVoByNo(purchaseNo);
        if (purchaseVo != null) {
            List<GoodRawVo> goods = purchaseVo.getGoods();
            List<Integer> collect = goods.stream().map(goodRawVo -> goodRawVo.getGoodId()).collect(Collectors.toList());
            GoodExample goodExample = new GoodExample();
            goodExample.createCriteria().andGoodIdNotIn(collect);
            List<Good> goods1 = goodMapper.selectByExample(goodExample);
            return goods1;
        } else {
            return selectRawAll();
        }

    }

    @Override
    public List<Good> selectAllByTypeId(Integer[] typeIds) {
        GoodExample goodExample = new GoodExample();
        goodExample.createCriteria().andGoodTypeIdIn(Arrays.asList(typeIds));
        return goodMapper.selectByExampleWithBLOBs(goodExample);
    }

    @Override
    public PageVo<GoodRawVo> selectGoodRawVoByTypeId(Integer pageNum, int typeId) {
        PageVo<GoodRawVo> pageVo = new PageVo<>();
        //开启分页
        PageHelper.startPage(pageNum, 7);
        List<GoodRawVo> goodVos = goodMapper.selectGoodRawVoByTypeId(typeId);
        for (GoodRawVo goodVo : goodVos) {
            System.out.println(goodVo.getPic());
        }
        //得到分页后的信息。
        PageInfo<GoodRawVo> pageInfo = new PageInfo<>(goodVos, 5);
        //组装需要的信息
        pageVo.setCurrentPage(pageNum);
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public List<Good> selectByGoodTypeIdAndIsNotInGoodIds(Integer goodTypeId, Integer[] ids) {
        if (goodTypeId == null || goodTypeId < 1 || goodTypeId > 3) {
            throw new RuntimeException("请输入正确的货物Id");
        }
        GoodExample goodExample = new GoodExample();
        GoodExample.Criteria criteria = goodExample.createCriteria();
        criteria.andGoodTypeIdEqualTo(goodTypeId);
        if (ids != null) {
            criteria.andGoodIdNotIn(Arrays.asList(ids));
        }
        List<Good> goods = goodMapper.selectByExample(goodExample);
        return goods;
    }

    @Override
    public List<Good> selectAll() {
        return goodMapper.selectByExample(new GoodExample());
    }

    @Override
    public PageVo<StockVo> selectGoodRaw(Integer pageNum) {
        PageVo<StockVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<StockVo> stockVos = stockMapper.selectAllEquipByWC();
        PageInfo<StockVo> pageInfo = new PageInfo<>(stockVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public PageVo<StockVo> selectStockByEquipQuery(Integer pageNum, String goodName, String warehouseName) {
        PageVo<StockVo> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<StockVo> stockVos = stockMapper.selectAllEquipByWCAndQuery(goodName,warehouseName);
        PageInfo<StockVo> pageInfo = new PageInfo<>(stockVos, 5);
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public PageVo<StockVo> warning(Integer pageNum) {
        return null;
    }

    @Override
    public void inExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Good.class, new GoodListener(goodMapper)).sheet().doRead();
    }

}
