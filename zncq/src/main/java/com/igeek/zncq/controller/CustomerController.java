package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Customer;
import com.igeek.zncq.service.ICustomerService;
import com.igeek.zncq.vo.CustomerQueryVo;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/25 09:56
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping
    public ResultData<List<Customer>> findAll(){
        ResultData<List<Customer>> resultData = new ResultData<>();
        List<Customer> customers = customerService.selectAll();
        if (customers != null && customers.size() != 0){
            resultData.setCode(200);
            resultData.setData(customers);
        }else{
            resultData.setCode(201);
        }
        return resultData;
    }
    @GetMapping("/{pageNum}")
    public ResultData<PageVo<Customer>> findAllByPage(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<Customer>> resultData = new ResultData<>();
        PageVo<Customer> pageVo = customerService.selectAllByPage(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有数据");
        }
        return resultData;
    }
    @PostMapping
    public ResultData addCustomer(@RequestBody Customer customer){
        ResultData<Object> resultData = new ResultData<>();
        customerService.insertCustomer(customer);
        resultData.setCode(200);
        resultData.setMessage("添加成功");
        return resultData;
    }
    @GetMapping("/findOne/{id}")
    public ResultData<Customer> findOneById(@PathVariable("id") Integer id){
        ResultData<Customer> resultData = new ResultData<>();
        Customer customer = customerService.selectOneById(id);
        if (customer != null){
            resultData.setCode(200);
            resultData.setData(customer);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("不存在该客户,请刷新页面");
        }
        return resultData;
    }
    @PutMapping
    public ResultData updateById(@RequestBody Customer customer){
        ResultData resultData = new ResultData();
        customerService.updateById(customer);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return resultData;
    }
    @DeleteMapping
    public ResultData deleteByIds(@RequestParam("ids") Integer[] ids){
        ResultData resultData = new ResultData();
        customerService.deleteByIds(ids);
        resultData.setCode(200);
        resultData.setMessage("删除成功");
        return resultData;
    }
    @GetMapping("/query")
    public ResultData<PageVo<Customer>> findByQuery(CustomerQueryVo queryVo){
        ResultData<PageVo<Customer>> resultData = new ResultData<>();
        PageVo<Customer> pageVo = customerService.selectAllByPageQuery(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有数据");
        }
        return resultData;
    }
}
