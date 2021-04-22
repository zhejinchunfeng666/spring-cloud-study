package com.zf.study.web.controller;


import com.zf.study.core.constant.RedisKeyConstants;
import com.zf.study.core.entity.Stock;
import com.zf.study.core.utils.RedisUtils;
import com.zf.study.web.service.IStockOrderService;
import com.zf.study.web.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zf
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/stockOrder")
@Slf4j
public class StockOrderController {

    @Autowired
    private IStockOrderService stockOrderService;

    @Autowired
    private IStockService stockService;

    @Resource
    private RedisUtils redisUtils;

    @PostConstruct
    public void init(){
        log.info("******初始化方法start******");
        // 查询商品信息
        List<Stock> stockList = stockService.list();
        stockList.stream().forEach(stock -> {
            // 将商品库存存放在redis中
            redisUtils.set(RedisKeyConstants.STOCK_COUNT +stock.getId(),String.valueOf(stock.getCount()));
            // 缓存商品售出数量
            redisUtils.set(RedisKeyConstants.STOCK_SALE+stock.getId(),String.valueOf(stock.getSale()));
            // 缓存商品版本信息
            redisUtils.set(RedisKeyConstants.STOCK_VERSION+stock.getId(),String.valueOf(stock.getVersion()));
        });

    }

    @RequestMapping("/create")
    public Object createOrder(@RequestParam Integer id){
        Object o = stockOrderService.createOrderInfo(id);
        return o;
    }

}

