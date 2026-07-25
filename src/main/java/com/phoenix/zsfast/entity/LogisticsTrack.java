package com.phoenix.zsfast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("logistics_track")
public class LogisticsTrack {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 关联单号 */
    private String orderNo;
    
    /** 轨迹时间 */
    private LocalDateTime trackTime;
    
    /** 地点 */
    private String location;
    
    /** 内容描述 */
    private String description;
}