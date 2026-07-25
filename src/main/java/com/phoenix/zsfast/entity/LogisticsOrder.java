package com.phoenix.zsfast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("logistics_order")
public class LogisticsOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 客户单号 */
    private String orderNo;
    
    /** 跟踪单号 */
    private String trackingNo;
    
    /** 状态：运输中/已签收 */
    private String status;
    
    /** 目的国家 */
    private String destinationCountry;
    
    /** 时效天数 */
    private Integer transitDays;
    
    /** 收件人 */
    private String receiverName;
    
    /** 邮编 */
    private String receiverZip;
    
    /** 创建时间 */
    private LocalDateTime createTime;
}