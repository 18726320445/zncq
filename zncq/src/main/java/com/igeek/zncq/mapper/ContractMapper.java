package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Contract;
import com.igeek.zncq.entity.ContractExample;
import java.util.List;

import com.igeek.zncq.vo.ContractDto;
import com.igeek.zncq.vo.ContractQueryVo;
import com.igeek.zncq.vo.ContractVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ContractMapper {
    int countByExample(ContractExample example);

    int deleteByExample(ContractExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Contract record);

    int insertSelective(Contract record);

    List<Contract> selectByExampleWithBLOBs(ContractExample example);

    List<Contract> selectByExample(ContractExample example);

    Contract selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByExampleWithBLOBs(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByExample(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKeyWithBLOBs(Contract record);

    int updateByPrimaryKey(Contract record);

    List<ContractVo> selectContractVoAllByPage();

    ContractVo selectContractVo(@Param("contractNo") String contractNo);

    Integer updateGoodNum(@Param("orderNo") String orderNo,@Param("goodId") Integer goodId,@Param("num") Long num);

    Integer deleteGoodById(@Param("orderNo") String orderNo,@Param("goodId") Integer goodId);

    List<ContractVo> selectContractVoAllByQueryPage(ContractDto contractDto);

    List<ContractVo> selectContractVoByStates(Integer[] states);

    List<ContractVo> selectContractVoByStatePageQuery(@Param("queryVo") ContractQueryVo queryVo, @Param("states")Integer[] states);
}