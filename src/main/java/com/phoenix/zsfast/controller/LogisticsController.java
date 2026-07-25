package com.phoenix.zsfast.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenix.zsfast.common.vo.Result;
import com.phoenix.zsfast.entity.LogisticsOrder;
import com.phoenix.zsfast.entity.LogisticsTrack;
import com.phoenix.zsfast.service.LogisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/logistics")
@RequiredArgsConstructor
@Tag(name = "物流管理", description = "包含物流订单与轨迹的查询及录入功能") // 接口分组描述
public class LogisticsController {

    private final LogisticsService logisticsService;

    @GetMapping("/detail")
    @Operation(summary = "查询物流详情及轨迹", description = "根据客户单号查询订单基本信息及历史轨迹节点")
    public Result<Map<String, Object>> getDetail(
            @Parameter(description = "客户单号", required = true, example = "HZD2026070622") 
            @RequestParam String orderNo) {
        
        Map<String, Object> data = new HashMap<>();
        LogisticsOrder order = logisticsService.getByOrderNo(orderNo);
        if (order == null) {
            return Result.error("未找到该单号信息");
        }
        data.put("order", order);
        data.put("tracks", logisticsService.getTracksByOrderNo(orderNo));
        return Result.success(data);
    }




}