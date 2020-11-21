package xyz.itihub.security.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 自定义连接状态视图(用于Spring Social默认实现查找用户绑定信息)
 * GET request connect返回视图
 * @Author: JiZhe
 * @CreateDate: 2018/10/14 11:05
 */
@Component("connect/status")
public class XxxConnectionStatusView extends AbstractView {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @see  org.springframework.social.connect.web.ConnectController 这是spring social 默认实现的控制器
     * @param model 响应数据
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");

        //自定义返回参数
        Map<String, Boolean> result = new HashMap<>(16);

        for (String key : connections.keySet()) {
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }

        //设置响应编码
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

}
