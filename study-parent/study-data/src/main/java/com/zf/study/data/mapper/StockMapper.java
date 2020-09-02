package com.zf.study.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.study.core.entity.Stock;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zf
 * @since 2020-08-28
 */
public interface StockMapper extends BaseMapper<Stock> {

    int updateStock(Stock stock);
}
