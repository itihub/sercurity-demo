package xyz.itihub.demo.async.controller;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 10:40
 */
@Data
@Component
public class DeferredResultHolder {

    /**
     * Map<String, DeferredResult<String>>
     * String K 订单号
     * DeferredResult<String> V 代表处理结果
     */
    private Map<String, DeferredResult<String>> map = new HashMap<>();


}
