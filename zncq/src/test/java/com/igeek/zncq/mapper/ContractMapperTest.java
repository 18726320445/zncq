package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.ContractDto;
import com.igeek.zncq.vo.ContractQueryVo;
import com.igeek.zncq.vo.ContractVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ContractMapperTest {
    @Autowired
    ContractMapper contractMapper;
    @Test
    void selectContractVoAllByPage() {
        List<ContractVo> contractVos = contractMapper.selectContractVoAllByPage();
        contractVos.forEach(contractVo -> System.out.println(contractVo.getIntroduction()));
    }

    @Test
    void selectContractVo() {
        contractMapper.selectContractVo("D0144F75");
    }

    @Test
    void selectContractVoAllByQueryPage() {
        ContractDto contractDto = new ContractDto();
        contractDto.setContractNo("D");
        contractDto.setPageNum(1);
        contractMapper.selectContractVoAllByQueryPage(contractDto);
    }

    @Test
    void selectContractVoByStates() {
        contractMapper.selectContractVoByStates(new Integer[]{1});
    }

    @Test
    void selectContractVoByStatePageQuery() {
        ContractQueryVo contractQueryVo = new ContractQueryVo();
        contractQueryVo.setPageNum(1);
        contractQueryVo.setContractNo("21");
        contractMapper.selectContractVoByStatePageQuery(contractQueryVo,new Integer[]{-1,1,2});
    }
}