package com.mhlevel.config;

import com.mhlevel.service.OrderService;
import com.mhlevel.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author quanbin
 * @date 2021-03-29
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0/3 * * * * ?") //需要在启动类上也添加开启定时任务的注解
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder(){
        orderService.closeOrder();
        System.out.println("执行定时任务，当前时间为：" + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }
}
