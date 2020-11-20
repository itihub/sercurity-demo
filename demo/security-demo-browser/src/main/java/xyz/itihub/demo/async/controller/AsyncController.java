package xyz.itihub.demo.async.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @Description: 异步请求处理
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 10:04
 */
@RestController
@Slf4j
public class AsyncController {


    /**
     * 同步处理case
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("order")
    @ApiOperation(value = "同步处理CASE")
    public String order() throws InterruptedException {
        log.info("主线程开始");
        //模拟线程处理下单 睡眠1秒
        Thread.sleep(1000);
        log.info("主线程返回");
        return "Success";
    }

    /**
     * 异步处理case
     * @return
     */
    @GetMapping("asyncOrder")
    @ApiOperation(value = "Callable异步处理CASE")
    public Callable<String> asynOrder() {
        log.info("主线程开始");
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("副线程开始");
                Thread.sleep(1000);
                log.info("副线程返回");
                return "Success";
            }
        };
        log.info("主线程返回");
        return callable;
    }

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @GetMapping("deferredOrder")
    @ApiOperation(value = "DeferredResult异步处理CASE")
    public DeferredResult<String> deferredOrder() throws InterruptedException {
        log.info("主线程开始");
        //生成8位随机订单号
        String orderId = RandomStringUtils.randomNumeric(8);
        //消息放入队列
        mockQueue.setPlaceOrder(orderId);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderId, result);
        log.info("主线程返回");
        return result;
    }

}
