package com.zf.study.web.controller;


import com.zf.study.core.annotation.RateLimiter;
import com.zf.study.core.constant.RedisKeyConstants;
import com.zf.study.core.entity.Stock;
import com.zf.study.core.exception.StudyErrorCode;
import com.zf.study.core.exception.StudyException;
import com.zf.study.core.utils.RedisUtils;
import com.zf.study.core.web.BaseController;
import com.zf.study.core.web.vo.StudyEcho;
import com.zf.study.web.service.IStockOrderService;
import com.zf.study.web.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/stock")
@Slf4j
public class StockController extends BaseController {

    @Autowired
    private IStockService stockService;

    @Autowired
    private IStockOrderService orderService;

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


    /**
     * 秒杀
     * 更新库存增加乐观锁
     * @param sid
     * @return
     */
    @RateLimiter(max = 10,timeout = 100)
    @RequestMapping("/create")
    public StudyEcho createOrder(@RequestParam Integer sid){
        try {
            orderService.createOrder(sid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StudyException(StudyErrorCode.ORDER_ERR);
        }
        return new StudyEcho(null,"秒杀成功",0);
    }

    /**
     * 秒杀优化
     * 1、库存存放在redis中
     * 2、分布式限流
     * @param sid
     * @return
     */

    @RequestMapping("/create2")
    public StudyEcho createOrder2(@RequestParam Integer sid){
        try {
            orderService.createOrder2(sid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StudyException(StudyErrorCode.ORDER_ERR);
        }
        return new StudyEcho(null,"秒杀成功",0);
    }


    /**
     * 每个人10秒内允许通过5个请求
     * @return
     */
    @RateLimiter(max = 5,timeout = 10)
    @RequestMapping("/test")
    public StudyEcho test(){
        return new StudyEcho(null,"执行成功",0);
    }

}

