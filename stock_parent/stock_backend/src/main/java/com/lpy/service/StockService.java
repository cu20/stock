package com.lpy.service;

import com.lpy.pojo.domain.InnerMarketDomain;
import com.lpy.pojo.domain.StockBlockDomain;
import com.lpy.pojo.domain.StockUpdownDomain;
import com.lpy.vo.resp.PageResult;
import com.lpy.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author lpy
 * @date 2025/2/13
 * @Description
 */
public interface StockService {
    public R<List<InnerMarketDomain>> innerIndexAll();

    public R<List<StockBlockDomain>> sectorAll();

    public R<PageResult> getStockPageInfo(Integer page, Integer pageSize);

    public R<List<StockUpdownDomain>> getStockInfo();

    R<Map> getStockUpdownCount();

    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);
}
