package com.lpy.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lpy
 * @date 2025/2/13
 * @Description
 */
public class OuterMarketDomain {
    /**
     * 大盘名称
     */
    private String name;
    /**
     * 当前点
     */
    private BigDecimal curPoint;

    /**
     * 大盘涨跌值
     */
    private BigDecimal updown;

    /**
     * 大盘涨幅
     */
    private BigDecimal rose;

    /**
     * 当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curTime;
}
