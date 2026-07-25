package com.phoenix.zsfast.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenix.zsfast.common.vo.Result;
import com.phoenix.zsfast.entity.LogisticsOrder;
import com.phoenix.zsfast.entity.LogisticsTrack;
import com.phoenix.zsfast.service.LogisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "管理员操作", description = "管理员操作") // 接口分组描述
public class AdminController {

    private final LogisticsService logisticsService;


    @PostMapping("/login")
    public void login(@RequestParam String username,
                      @RequestParam String password,
                      HttpSession session,
                      HttpServletResponse response) throws IOException {
        // ⚠️ 实际生产用 BCryptPasswordEncoder 匹配数据库密码
        if ("admin".equals(username) && "123456".equals(password)) {
            session.setAttribute("IS_ADMIN", true);
            response.sendRedirect("/admin.html");
        } else {
            response.setContentType("text/plain;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("用户名或密码错误");
        }
    }


    @PostMapping("/saveOrder")
    @Operation(summary = "新增或更新物流订单", description = "如果单号已存在则更新基础信息，不存在则新增")
    public Result<String> saveOrder(@RequestBody LogisticsOrder order) {
        try {
            logisticsService.saveOrUpdateOrder(order);
            return Result.success("订单保存成功");
        } catch (Exception e) {
            return Result.error("订单保存失败: " + e.getMessage());
        }
    }

    @PostMapping("/track")
    @Operation(summary = "新增物流轨迹", description = "为指定的客户单号添加一条新的轨迹明细")
    public Result<String> saveTrack(@RequestBody LogisticsTrack track) {
        try {
            logisticsService.saveOrUpdateTrack(track);
            return Result.success("轨迹添加成功");
        } catch (Exception e) {
            return Result.error("轨迹添加失败: " + e.getMessage());
        }
    }

    @GetMapping("/orders")
    @Operation(summary = "分页查询订单列表")
    public Result<Page<LogisticsOrder>> pageOrders(
            @Parameter(description = "页码", example = "1") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页条数", example = "10") @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "搜索关键字") @RequestParam(required = false) String keyword) {
        Page<LogisticsOrder> page = logisticsService.pageOrders(pageNum, pageSize, keyword);
        return Result.success(page);
    }



}