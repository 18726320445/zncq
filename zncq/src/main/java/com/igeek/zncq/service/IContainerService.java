package com.igeek.zncq.service;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.vo.ContainerQueryVo;
import com.igeek.zncq.vo.PageVo;

import java.util.List;

public interface IContainerService {

    /**
     * 查询所有的容器信息
     * @return
     */
    public List<Container> selectAll(Integer[] ids);
    /**
     * 查询对应页码的所有容器信息
     * @param pageNum
     * @return
     */
    public PageVo<Container> selectContainAll(Integer pageNum);

    /**
     * 添加容器
     * @param container
     */
    public void insertContainer(Container container);

    /**
     * 根据id查找对应的容器信息
     * @param id
     * @return
     */
    public Container selectOne(Integer id);

    /**
     * 根据id修改容器信息
     * @param container
     */
    public void updateById(Container container);

    /**
     * 通过id删除容器
     * @param ids
     */
    void deleteContainerById(Integer[] ids);

    /**
     * 根据容器条件查询容器信息
     * @param containerQueryVo
     * @return
     */
    PageVo<Container> selectByQuery(ContainerQueryVo containerQueryVo);


}
