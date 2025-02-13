package com.lpy.controller;

import com.lpy.pojo.domain.InnerMarketDomain;
import com.lpy.pojo.domain.StockBlockDomain;
import com.lpy.pojo.domain.StockUpdownDomain;
import com.lpy.service.StockService;
import com.lpy.vo.resp.PageResult;
import com.lpy.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author lpy
 * @date 2025/2/13
 * @Description
 */
@RestController
@RequestMapping("/api/quot")
public class StockController{

    @Autowired
    private StockService stockService;

    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> innerIndexAll(){
        return stockService.innerIndexAll();
    }

    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAll(){
        return stockService.sectorAll();
    }

    @GetMapping("/stock/all")
    public R<PageResult> getStockPageInfo(@RequestParam(name = "page",required = false,defaultValue = "1") Integer page,
                                          @RequestParam(name = "pageSize",required = false,defaultValue = "20") Integer pageSize){
        return stockService.getStockPageInfo(page,pageSize);
    }

    @GetMapping("/stock/increase")
    public R<List<StockUpdownDomain>> getStockInfo(){
        return stockService.getStockInfo();
    }

    /**
     * 统计最新交易日下股票每分钟涨跌停的数量
     * @return
     */
    @GetMapping("/stock/updown/count")
    public R<Map> getStockUpdownCount(){
        return stockService.getStockUpdownCount();
    }

    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize){
        stockService.stockExport(response,page,pageSize);
    }
}
