package com.lpy.mapper;

import com.lpy.pojo.entity.StockBusiness;

/**
* @author gugu
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2025-02-11 20:05:48
* @Entity com.lpy.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

}
