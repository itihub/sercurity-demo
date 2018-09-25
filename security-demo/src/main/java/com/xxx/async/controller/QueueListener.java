package com.xxx.async.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.descriptor.web.ContextResourceLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

/**
 * @Description: 模拟队列监听器
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 10:48
 */
@Slf4j
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        new Thread(() -> {
            while (true) {
                //判断是否有消息处理完成
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String orderId = mockQueue.getCompleteOrder();
                    log.info("返回订单处理结果");
                    deferredResultHolder.getMap().get(orderId).setResult("Success");

                    mockQueue.setCompleteOrder(null);
                } else {
                    //没有消息，休息100毫秒再次监听
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
