package com.lpy.service.impl;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lpy.mapper.StockBlockRtInfoMapper;
import com.lpy.mapper.StockBusinessMapper;
import com.lpy.mapper.StockMarketIndexInfoMapper;
import com.lpy.mapper.StockRtInfoMapper;
import com.lpy.pojo.domain.InnerMarketDomain;
import com.lpy.pojo.domain.StockBlockDomain;
import com.lpy.pojo.domain.StockUpdownDomain;
import com.lpy.pojo.entity.StockBusiness;
import com.lpy.pojo.vo.StockInfoConfig;
import com.lpy.service.StockService;
import com.lpy.utils.DateTimeUtil;
import com.lpy.utils.RequestInfoUtil;
import com.lpy.vo.resp.PageResult;
import com.lpy.vo.resp.R;
import com.lpy.vo.resp.ResponseCode;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lpy
 * @date 2025/2/13
 * @Description
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;


    @Override
    public R<List<InnerMarketDomain>> innerIndexAll() {
        //1.获取国内A股大盘的id集合
        List<String> inners = stockInfoConfig.getInner();
        //2.获取最近股票交易日期
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();

        //TODO mock测试数据，后期数据通过第三方接口动态获取实时数据 可删除
        lastDate=DateTime.parse("2022-01-02 09:32:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //3.将获取的java Date传入接口
        List<InnerMarketDomain> list= stockMarketIndexInfoMapper.getMarketInfo(inners,lastDate);
        //4.返回查询结果
        return R.ok(list);

    }

    @Override
    public R<List<StockBlockDomain>> sectorAll() {
        //获取股票最新交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock数据,后续删除
        lastDate=DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.调用mapper接口获取数据
        List<StockBlockDomain> infos=stockBlockRtInfoMapper.sectorAll(lastDate);
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }

    @Override
    public R<PageResult> getStockPageInfo(Integer page, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(page,pageSize);

        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        curDate = DateTime.parse("2022-06-07 15:00:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //查询
        List<StockUpdownDomain> infos = stockRtInfoMapper.getNewestStockInfo(curDate);
        if(CollectionUtils.isEmpty(infos)){
            return R.error(ResponseCode.NO_RESPONSE_DATA);
        }

        //组装PageInfo对象
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(new PageInfo<>(infos));

        return R.ok(pageResult);
    }

    @Override
    public R<List<StockUpdownDomain>> getStockInfo() {
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        curDate = DateTime.parse("2022-06-07 15:00:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //查询
        List<StockUpdownDomain> infos = stockRtInfoMapper.getNewestStockInfoLimit(curDate);
        if(CollectionUtils.isEmpty(infos)){
            return R.error(ResponseCode.NO_RESPONSE_DATA);
        }

        return R.ok(infos);
    }

    @Override
    public R<Map> getStockUpdownCount() {
        //1.获取最新的交易时间范围 openTime  curTime
        //1.1 获取最新股票交易时间点
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curTime = curDateTime.toDate();
        //TODO
        curTime= DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2 获取最新交易时间对应的开盘时间
        DateTime openDate = DateTimeUtil.getOpenDate(curDateTime);
        Date openTime = openDate.toDate();
        //TODO
        openTime= DateTime.parse("2022-01-06 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.查询涨停数据
        //约定mapper中flag入参： 1-》涨停数据 0：跌停
        List<Map> upCounts=stockRtInfoMapper.getStockUpdownCount(openTime,curTime,1);
        //3.查询跌停数据
        List<Map> dwCounts=stockRtInfoMapper.getStockUpdownCount(openTime,curTime,0);
        //4.组装数据
        HashMap<String, List> mapInfo = new HashMap<>();
        mapInfo.put("upList",upCounts);
        mapInfo.put("downList",dwCounts);
        //5.返回结果
        return R.ok(mapInfo);
    }

    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        try {
            //1.获取最近最新的一次股票有效交易时间点（精确分钟）
            Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
            //因为对于当前来说，我们没有实现股票信息实时采集的功能，所以最新时间点下的数据
            //在数据库中是没有的，所以，先临时指定一个假数据,后续注释掉该代码即可
            curDate=DateTime.parse("2022-01-05 09:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
            //2.设置分页参数 底层会拦截mybatis发送的sql，并动态追加limit语句实现分页
            PageHelper.startPage(page,pageSize);
            //3.查询
            List<StockUpdownDomain> infos=stockRtInfoMapper.getNewestStockInfo(curDate);
            //如果集合为空，响应错误提示信息
            if (CollectionUtils.isEmpty(infos)) {
                //响应提示信息
                RequestInfoUtil.setUtf8(response);
                R<Object> r = R.error(ResponseCode.NO_RESPONSE_DATA);
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(r));
                return;
            }
            //设置响应excel文件格式类型
            response.setContentType("application/vnd.ms-excel");
            //2.设置响应数据的编码格式
            response.setCharacterEncoding("utf-8");
            //3.设置默认的文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8");
            //设置默认文件名称：兼容一些特殊浏览器
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //4.响应excel流
            EasyExcel
                    .write(response.getOutputStream(),StockUpdownDomain.class)
                    .sheet("股票信息")
                    .doWrite(infos);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("当前导出数据异常，当前页：{},每页大小：{},异常信息：{}"+page+pageSize+e.getMessage());
        }
    }
}
