package com.igeek.zncq.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.mapper.GoodMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/4/6 15:39
 * @email liuyia2022@163.com
 */

@Slf4j
@Data
public class GoodListener extends AnalysisEventListener<Good> {

    private GoodMapper goodMapper;

    public GoodListener(GoodMapper goodMapper) {
        this.goodMapper = goodMapper;
    }
    @Override
    public void invoke(Good good, AnalysisContext analysisContext) {
        good.setGoodTypeId(1);
        int res = goodMapper.insertSelective(good);
        if (res != 1){
            throw new AddException("添加异常");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
