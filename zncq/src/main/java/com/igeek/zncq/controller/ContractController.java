package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Contract;
import com.igeek.zncq.log.LogSys;
import com.igeek.zncq.service.IContractService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/25 13:26
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/contract")
@PreAuthorize("hasRole('ROLE_ROOT')")
public class ContractController {
    @Autowired
    IContractService contractService;

    @PostMapping
    public ResultData addContract(@RequestBody Contract contract) {
        ResultData<Object> resultData = new ResultData<>();
        contractService.insertContract(contract);
        resultData.setCode(200);
        resultData.setMessage("创建成功");
        return resultData;
    }
    @GetMapping("/{pageNum}")
    public ResultData<PageVo<ContractVo>> findAllByPage(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<ContractVo>> resultData = new ResultData<>();
        PageVo<ContractVo> pageVo = contractService.selectAllByPage(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/query/{contractNo}")
    public ResultData<ContractVo> findContractGoods(@PathVariable("contractNo") String contractNo){
        ResultData<ContractVo> resultData = new ResultData<>();
        ContractVo contractVo = contractService.selectContractVo(contractNo);
        if (contractVo == null || contractVo.getGoods() == null){
            resultData.setCode(201);
            resultData.setMessage("没有数据");
        }else{
            resultData.setCode(200);
            resultData.setData(contractVo);
            resultData.setMessage("查询成功");
        }
        return resultData;
    }
    @PostMapping("/good")
    public ResultData addGoodByContractNo(@RequestBody ContractDto contractDto){
        ResultData<Object> resultData = new ResultData<>();
        contractService.insertGoodByContractNo(contractDto);
        resultData.setCode(200);
        resultData.setMessage("添加成功");
        return resultData;
    }
    @PutMapping("/good")
    public ResultData updateGoodByContractNo(@RequestBody ContractDto contractDto){
        ResultData<Object> resultData = new ResultData<>();
        contractService.updateGoodByContractNo(contractDto);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return resultData;
    }
    @DeleteMapping("/good")
    public ResultData deleteGoodByContractNo(@RequestBody ContractDto contractDto){
        ResultData<Object> resultData = new ResultData<>();
        contractService.deleteGoodByContractNo(contractDto);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return resultData;
    }

    @GetMapping("/query")
    public ResultData<PageVo<ContractVo>> findByQueryPage(ContractDto contractDto){
        ResultData<PageVo<ContractVo>> resultData = new ResultData<>();
        PageVo<ContractVo> pageVo = contractService.selectAllByQueryPage(contractDto);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @LogSys("合同提交")
    @PutMapping("/commit/{contractNo}")
    public ResultData commitContact(@PathVariable("contractNo") String contractNo){
        ResultData<Object> resultData = new ResultData<>();
        contractService.commitContractByContractNo(contractNo);
        resultData.setCode(200);
        resultData.setMessage("提交成功,等待签署中");
        return resultData;
    }
    @GetMapping("/state/{pageNum}")
    public ResultData<PageVo<ContractVo>> findContactVoSign(@PathVariable("pageNum")Integer pageNum){
        ResultData<PageVo<ContractVo>> resultData = new ResultData<>();
        PageVo<ContractVo> pageVo = contractService.selectContractVoByStatePage(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @LogSys("合同签署")
    @PutMapping("/good/agree")
    public ResultData goodAgree(@RequestBody ContractDto contractDto){
        ResultData<Object> resultData = new ResultData<>();
        contractService.goodAgree(contractDto.getContractNo(),contractDto.getVehicleId(),contractDto.getProcessName());
        resultData.setCode(200);
        resultData.setMessage("合约签署成功,已发出出库通知，等待产品出库");
        return resultData;
    }
    @LogSys("合同签署")
    @PutMapping("/equip/agree")
    public ResultData equipAgree(@RequestBody ContractDto contractDto){
        ResultData<Object> resultData = new ResultData<>();
        contractService.equipAgree(contractDto.getContractNo(),contractDto.getProcessName());
        resultData.setCode(200);
        resultData.setMessage("合约签署成功,已发出出库通知，等待产品出库");
        return resultData;
    }
    @LogSys("合同签署")
    @DeleteMapping("/pass")
    public ResultData passContract(@RequestBody ContractDto contractDto){
        ResultData<Object> resultData = new ResultData<>();
        contractService.passContract(contractDto.getContractNo(),contractDto.getProcessName());
        resultData.setCode(200);
        resultData.setMessage("合约拒签成功");
        return resultData;
    }

    @GetMapping("/state/query")
    public ResultData<PageVo<ContractVo>> findContactVoSign(ContractQueryVo queryVo){
        ResultData<PageVo<ContractVo>> resultData = new ResultData<>();
        PageVo<ContractVo> pageVo = contractService.selectContractVoByStatePageQuery(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
}
