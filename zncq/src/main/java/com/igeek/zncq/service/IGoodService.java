package com.igeek.zncq.service;

import com.igeek.zncq.entity.Good;
import com.igeek.zncq.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author liuyi
 */
public interface IGoodService {
    /**
     * 返回所有商品明细（商品和对应供应商的信息）
     * @param pageNum 需要返回的页码
     * @return PageVo
     */
    public PageVo<GoodRawVo> goodRawDetail(Integer pageNum);

    /**
     * 添加原料信息
     * @param good
     * @return
     */
    public Boolean addGoodRaw(Good good);

    /**
     * 根据Id批量删除
     * @param ids
     * @return
     */
    public Integer deleteGoodRawByIds(Integer[] ids);

    /**
     * 根据id值查找货物(原料)记录
     * @param id
     * @return
     */
    public Good selectGoodRawById(Integer id);

    /**
     * 根据查询条件查询对应的记录
     * @param goodQuery
     * @return
     */
    public PageVo<GoodRawVo> selectGoodRawByGoodQuery(GoodQuery goodQuery);

    /**
     * 根据id更改货物(原料)信息
     * @param good
     * @return
     */
    public Boolean updateGoodRawById(Good good);

    /**
     * 查询所有原料信息
     * @return
     */
    List<Good> selectRawAll();

    /**
     * 查询所有不在采购订单里面的货物信息
     * @param purchaseNo
     * @return
     */
    List<Good> selectRawAllByPurchaseNo(String purchaseNo);

    /**
     * 查询原料和设备的信息
     * @return
     */
    List<Good> selectAllByTypeId(Integer[] typeIds);

    /**
     * 根据typeId查询货物信息
     * @param pageNum
     * @param i
     * @return
     */
    PageVo<GoodRawVo> selectGoodRawVoByTypeId(Integer pageNum, int i);

    /**
     * 根据货物类型以及不在ids内条件查询物品
     * @param goodTypeId
     * @param ids
     * @return
     */
    List<Good> selectByGoodTypeIdAndIsNotInGoodIds(Integer goodTypeId, Integer[] ids);

    /**
     * 查询所有的货物信息
     * @return
     */
    List<Good> selectAll();

    /**
     * 查询库存中对应仓库所有的设备
     * @return
     */
    PageVo<StockVo> selectGoodRaw(Integer pageNum);

    /**
     * 根据条件查询库存中对应仓库所有的设备
     * @param pageNum
     * @param goodName
     * @param warehouseName
     * @return
     */
    PageVo<StockVo> selectStockByEquipQuery(Integer pageNum, String goodName, String warehouseName);

    /**
     * 查询出所有货物库存告急的信息
     * @param pageNum
     * @return
     */
    PageVo<StockVo> warning(Integer pageNum);

    void inExcel(MultipartFile file) throws IOException;


}
