package com.zf.study.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.study.core.entity.StockOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zf
 * @since 2020-08-28
 */
public interface IStockOrderService extends IService<StockOrder> {

    void createOrder(Integer sid);

    void createOrder2(Integer sid);

    Object createOrderInfo(Integer id);
}
