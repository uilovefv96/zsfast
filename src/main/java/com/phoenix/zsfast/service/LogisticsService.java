package com.phoenix.zsfast.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenix.zsfast.entity.LogisticsOrder;
import com.phoenix.zsfast.entity.LogisticsTrack;
import com.phoenix.zsfast.mapper.LogisticsOrderMapper;
import com.phoenix.zsfast.mapper.LogisticsTrackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogisticsService {

    private final LogisticsOrderMapper orderMapper;
    private final LogisticsTrackMapper trackMapper;

    // 根据单号查询订单详情
    public LogisticsOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<LogisticsOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LogisticsOrder::getOrderNo, orderNo);
        return orderMapper.selectOne(wrapper);
    }

    // 根据单号查询轨迹列表（按时间倒序）
    public List<LogisticsTrack> getTracksByOrderNo(String orderNo) {
        LambdaQueryWrapper<LogisticsTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LogisticsTrack::getOrderNo, orderNo)
               .orderByDesc(LogisticsTrack::getTrackTime);
        return trackMapper.selectList(wrapper);
    }


    /**
     * 保存或更新订单（先查询，再决定新增或更新）
     */
    public void saveOrUpdateOrder(LogisticsOrder order) {
        // 1. 根据客户单号去数据库查询
        LambdaQueryWrapper<LogisticsOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LogisticsOrder::getOrderNo, order.getOrderNo());
        LogisticsOrder existOrder = orderMapper.selectOne(wrapper);

        if (existOrder != null) {
            // 2. 如果查到了，说明是更新操作，带上原来的主键 ID
            order.setId(existOrder.getId());
            orderMapper.updateById(order);
        } else {
            // 3. 如果没查到，说明是新增操作
            orderMapper.insert(order);
        }
    }

    // 新增轨迹
    public void saveTrack(LogisticsTrack track) {
        trackMapper.insert(track);
    }

    /**
     * 保存或更新轨迹（先查询，再决定新增或更新）
     */
    public void saveOrUpdateTrack(LogisticsTrack track) {
        // 1. 如果轨迹有ID，根据ID去数据库查询
        if (track.getId() != null) {
            LogisticsTrack existTrack = trackMapper.selectById(track.getId());
            if (existTrack != null) {
                // 2. 如果查到了，说明是更新操作
                trackMapper.updateById(track);
                return;
            }
        }

        // 3. 如果没查到或没有ID，说明是新增操作
        trackMapper.insert(track);
    }


    /**
     * 分页查询订单
     */
    public Page<LogisticsOrder> pageOrders(int pageNum, int pageSize, String keyword) {
        Page<LogisticsOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<LogisticsOrder> wrapper = new LambdaQueryWrapper<>();

        // 支持按单号、收件人模糊搜索
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.like(LogisticsOrder::getOrderNo, keyword)
                    .or()
                    .like(LogisticsOrder::getReceiverName, keyword);
        }
        // 按创建时间倒序
        wrapper.orderByDesc(LogisticsOrder::getCreateTime);

        return orderMapper.selectPage(page, wrapper);
    }

    /**
     * 更新订单信息
     */
    public boolean updateOrder(LogisticsOrder order) {
        return orderMapper.updateById(order) > 0;
    }
}