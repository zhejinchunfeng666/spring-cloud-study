package com.zf.study.web.controller;


import com.zf.study.core.entity.Stock;
import com.zf.study.core.exception.StudyErrorCode;
import com.zf.study.core.exception.StudyException;
import com.zf.study.core.utils.RedisUtils;
import com.zf.study.core.web.BaseController;
import com.zf.study.core.web.vo.StudyEcho;
import com.zf.study.web.service.IStockOrderService;
import com.zf.study.web.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zf
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/stock")
public class StockController extends BaseController {

    @Autowired
    private IStockService stockService;

    @Autowired
    private IStockOrderService orderService;

    @Resource
    private RedisUtils redisUtils;


    @RequestMapping("/create")
    public StudyEcho createOrder(@RequestParam Integer sid){
        try {
            Stock stock = new Stock();
            stock.setId(2);
            stock.setName("zf");
            stock.setSale(2);
            stock.setCount(3);
            stock.setVersion(1);
            redisUtils.setObject("stock",stock);
            orderService.createOrder(sid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StudyException(StudyErrorCode.ORDER_ERR);
        }
        return new StudyEcho(null,"秒杀成功",0);
    }




}

