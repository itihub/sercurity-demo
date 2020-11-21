package xyz.itihub.security.core.support;

import lombok.Data;

/**
 * @description: 返回对象
 * @author: Administrator
 * @date: 2018/08/27 0027
 */
@Data
public class SimpleResponse {

    private Object content;

    public SimpleResponse() {
    }

    public SimpleResponse(Object content) {
        this.content = content;
    }
}
