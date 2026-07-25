# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.0/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### init sql
```sql

-- 初始化数据库

-- 1. 物流订单主表
CREATE TABLE `logistics_order`
(
    `id`                  bigint      NOT NULL AUTO_INCREMENT,
    `order_no`            varchar(64) NOT NULL COMMENT '客户单号 (如 HZD...)',
    `tracking_no`         varchar(64) DEFAULT NULL COMMENT '跟踪单号 (如 1Z0JH...)',
    `status`              varchar(32) DEFAULT NULL COMMENT '状态 (运输中/已签收)',
    `destination_country` varchar(64) DEFAULT NULL COMMENT '目的国家',
    `transit_days`        int         DEFAULT NULL COMMENT '预计时效(天)',
    `receiver_name`       varchar(64) DEFAULT NULL COMMENT '收件人',
    `receiver_zip`        varchar(32) DEFAULT NULL COMMENT '邮编',
    `latest_update_time`  datetime    DEFAULT NULL COMMENT '最新轨迹时间',
    `create_time`         datetime    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流订单表';

-- 2. 物流轨迹明细表
CREATE TABLE `logistics_track`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `order_no`    varchar(64) NOT NULL COMMENT '关联的客户单号',
    `track_time`  datetime    NOT NULL COMMENT '轨迹发生时间',
    `location`    varchar(64)  DEFAULT NULL COMMENT '地点 (如 NB, YIWU)',
    `description` varchar(512) DEFAULT NULL COMMENT '详细内容',
    PRIMARY KEY (`id`),
    KEY           `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流轨迹表';
```
