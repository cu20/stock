package com.lpy.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lpy
 * @date 2025/2/13
 * @Description
 */
@Data
public class InnerMarketDomain {
    /**
     * 大盘编码
     */
    private String code;
    /**
     * 大盘名称
     */
    private String name;
    /**
     * 开盘点
     */
    private BigDecimal openPoint;
    /**
     * 当前点
     */
    private BigDecimal curPoint;
    /**
     * 前收盘点
     */
    private BigDecimal preClosePoint;
    /**
     * 交易量
     */
    private Long tradeAmt;
    /**
     * 交易金额
     */
    private Long tradeVol;
    /**
     * 涨跌值
     */
    private BigDecimal upDown;
    /**
     * 涨幅
     */
    private BigDecimal rose;

    /**
     * 振幅
     */
    private BigDecimal amplitude;
    /**
     * 当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curTime;
}
