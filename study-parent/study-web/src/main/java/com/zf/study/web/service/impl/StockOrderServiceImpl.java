package com.zf.study.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.study.core.constant.RedisKeyConstants;
import com.zf.study.core.entity.Stock;
import com.zf.study.core.entity.StockOrder;
import com.zf.study.core.exception.StudyErrorCode;
import com.zf.study.core.exception.StudyException;
import com.zf.study.core.utils.RedisUtils;
import com.zf.study.data.mapper.StockMapper;
import com.zf.study.data.mapper.StockOrderMapper;
import com.zf.study.web.service.IStockOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zf
 * @since 2020-08-28
 */
@Service
@Slf4j
public class StockOrderServiceImpl extends ServiceImpl<StockOrderMapper, StockOrder> implements IStockOrderService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockOrderMapper orderMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void  createOrder(Integer sid){
        // 校验库存
       Stock stock =  checkStock(sid);
//        // 扣库存
//        saleStock(stock);
        // 乐观锁更新库存
        saleStock2(stock);
        // 创建订单
        creteStockOrder(stock);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrder2(Integer sid) {
        // 校验库存
       Stock stock =  checkStockByRedis(sid);
        // 扣库存
        saleStockByRedis(stock);
        // 创建订单
        creteStockOrder(stock);
    }

    private void saleStockByRedis(Stock stock) {
        saleStock2(stock);
        redisUtils.incrBy(RedisKeyConstants.STOCK_SALE+stock.getId(),1);
        redisUtils.incrBy(RedisKeyConstants.STOCK_VERSION+stock.getId(),1);
    }

    private Stock checkStockByRedis(Integer sid) {
        // redis中获取商品信息
        Integer count = Integer.valueOf(redisUtils.get(RedisKeyConstants.STOCK_COUNT+sid));
        Integer sale = Integer.valueOf(redisUtils.get(RedisKeyConstants.STOCK_SALE+sid));
        Integer version = Integer.valueOf(redisUtils.get(RedisKeyConstants.STOCK_VERSION+sid));
        if (ObjectUtil.hasEmpty(count,sale,version)){
            // 数据库中获取商品信息
            Stock stock = stockMapper.selectById(sid);
            count = stock.getCount();
            sale = stock.getSale();
            version = stock.getVersion();
        }
        if (count.equals(sale)){
            throw new StudyException(StudyErrorCode.STOCK_NONUM);
        }
        Stock stock = new Stock();
        stock.setId(sid);
        stock.setCount(count);
        stock.setSale(sale);
        stock.setVersion(version);
        return stock;
    }

    private void saleStock2(Stock stock) {
       int count =  stockMapper.updateStock(stock);
       if (count != 1){
           throw new StudyException(StudyErrorCode.STOCK_NONUM);
       }
    }

    private void creteStockOrder(Stock stock) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setName(stock.getName());
        stockOrder.setSid(stock.getId());
        stockOrder.setCreateTime(LocalDateTime.now());
        orderMapper.insert(stockOrder);
    }

    private void saleStock(Stock stock) {
        stock.setSale(stock.getSale()+1);
        stockMapper.updateById(stock);
    }

    private Stock checkStock(Integer sid) {
        // 获取商品信息
        Stock stock = stockMapper.selectById(sid);
        if (stock.getSale().equals(stock.getCount())){
            throw new StudyException(StudyErrorCode.STOCK_NONUM);
        }
        return stock;
    }
}
