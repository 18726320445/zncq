package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.ContainerExample;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.ContainerMapper;
import com.igeek.zncq.mapper.WarehouseTransferMapper;
import com.igeek.zncq.service.IContainerService;
import com.igeek.zncq.vo.ContainerQueryVo;
import com.igeek.zncq.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/15 19:01
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class ContainerServiceImpl implements IContainerService {
    @Autowired
    ContainerMapper containerMapper;
    @Autowired
    WarehouseTransferMapper warehouseTransferMapper;

    @Override
    public List<Container> selectAll(Integer[] ids) {
        ContainerExample containerExample = new ContainerExample();
        if (ids != null){
            containerExample.createCriteria().andIdNotIn(Arrays.asList(ids));
        }
        return containerMapper.selectByExample(containerExample);
    }

    @Override
    public PageVo<Container> selectContainAll(Integer pageNum) {
        PageVo<Container> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<Container> containers = containerMapper.selectByExample(new ContainerExample());
        PageInfo<Container> pageInfo = new PageInfo<>(containers, 5);
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public void insertContainer(Container container) {
        int res = containerMapper.insertSelective(container);
        if (res != 1){
            throw new AddException("添加失败,发生未知异常(*^_^*)");
        }
    }

    @Override
    public Container selectOne(Integer id) {
        return containerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateById(Container container) {
        int res = containerMapper.updateByPrimaryKey(container);
        if (res != 1){
            throw new UpdateException("修改失败,发生未知异常(*^_^*)");
        }
    }

    @Override
    public void deleteContainerById(Integer[] ids) {
        if (ids.length == 0){
            throw new DeleteException("请不要传入空id");
        }
        //检查仓库中是否有使用该容器
        int count = containerMapper.countByIdWarehouseContainer(ids);
        if (count != 0){
            throw new DeleteException("仓库中有使用该容器无法删除");
        }
        //最终删除
        ContainerExample containerExample = new ContainerExample();
        containerExample.createCriteria().andIdIn(Arrays.asList(ids));
        int res = containerMapper.deleteByExample(containerExample);
        if (res != ids.length){
            throw new DeleteException("删除时异常/(ㄒoㄒ)/~~");
        }
    }

    @Override
    public PageVo<Container> selectByQuery(ContainerQueryVo containerQueryVo) {
        PageVo<Container> pageVo = new PageVo<>();
        PageHelper.startPage(containerQueryVo.getPageNum(),7);
        ContainerExample containerExample = new ContainerExample();
        ContainerExample.Criteria criteria = containerExample.createCriteria();
        if (StringUtils.hasLength(containerQueryVo.getName())){
            criteria.andNameLike("%" + containerQueryVo.getName() +"%");
        }
        if (containerQueryVo.getMaxCapacity() != null ){
            criteria.andMaxCapacityEqualTo(containerQueryVo.getMaxCapacity());
        }
        if (StringUtils.hasLength(containerQueryVo.getType())){
            criteria.andTypeEqualTo(containerQueryVo.getType());
        }
        List<Container> containers = containerMapper.selectByExample(containerExample);
        PageInfo<Container> pageInfo = new PageInfo<>(containers, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }
}
