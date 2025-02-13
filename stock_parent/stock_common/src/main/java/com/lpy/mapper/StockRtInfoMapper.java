package com.lpy.mapper;

import com.lpy.pojo.domain.StockUpdownDomain;
import com.lpy.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author gugu
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2025-02-11 20:05:48
* @Entity com.lpy.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpdownDomain> getNewestStockInfo(@Param("timePoint") Date curDate);

    List<StockUpdownDomain> getNewestStockInfoLimit(@Param("timePoint") Date curDate);

    List<Map> getStockUpdownCount(@Param("openTime") Date openTime,@Param("curTime") Date curTime,@Param("flag") int i);
}
