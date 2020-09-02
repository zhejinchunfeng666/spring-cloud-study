package com.zf.study.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.study.core.entity.Stock;
import com.zf.study.data.mapper.StockMapper;
import com.zf.study.web.service.IStockService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zf
 * @since 2020-08-28
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

}
