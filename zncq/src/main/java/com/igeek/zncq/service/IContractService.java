package com.igeek.zncq.service;

import com.igeek.zncq.entity.Contract;
import com.igeek.zncq.vo.ContractDto;
import com.igeek.zncq.vo.ContractQueryVo;
import com.igeek.zncq.vo.ContractVo;
import com.igeek.zncq.vo.PageVo;

/**
 * @author liuyi
 */
public interface IContractService {
    /**
     * 创建合同
     * @param contract
     */
    void insertContract(Contract contract);

    /**
     * 根据页码查询对应的合同信息
     * @param pageNum
     * @return
     */
    PageVo<ContractVo> selectAllByPage(Integer pageNum);

    /**
     * 根据合同号查询所有的货物
     * @param contractNo
     * @return
     */
    ContractVo selectContractVo(String contractNo);

    /**
     * 给合同添加货物
     * @param contractDto
     */
    void insertGoodByContractNo(ContractDto contractDto);

    /**
     * 更新合同数量
     * @param contractDto
     */
    void updateGoodByContractNo(ContractDto contractDto);

    /**
     * 删除合同物品
     * @param contractDto
     */
    void deleteGoodByContractNo(ContractDto contractDto);

    /**
     * 根据条件查询对应的合同信息
     * @param contractDto
     * @return
     */
    PageVo<ContractVo> selectAllByQueryPage(ContractDto contractDto);

    /**
     * 提交合约 更改合约状态
     * @param contractNo
     */
    void commitContractByContractNo(String contractNo);

    /**
     * 找出所有已提交，审核通过，以及审核为通过
     * @param pageNum
     * @return
     */
    PageVo<ContractVo> selectContractVoByStatePage(Integer pageNum);

    /**
     * 签署产品合约
     */
    void goodAgree(String contractNo,Integer vehicleId,String processName);

    /**
     * 签署设备合约
     * @param contractNo
     */
    void equipAgree(String contractNo,String processName);

    /**
     * 拒签合约
     * @param contractNo
     */
    void passContract(String contractNo,String processName);

    /**
     * 根据条件查询合约
     * @param queryVo
     * @return
     */
    PageVo<ContractVo> selectContractVoByStatePageQuery(ContractQueryVo queryVo);

    /**
     * 根据状态统计订单数
     * @param i
     * @return
     */
    Integer countByState(int i);
}
