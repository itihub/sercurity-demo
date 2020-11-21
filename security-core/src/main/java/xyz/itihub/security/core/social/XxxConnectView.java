package xyz.itihub.security.core.social;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: 自定义连接视图
 * @Author: JiZhe
 * @CreateDate: 2018/10/14 10:57
 */
public class XxxConnectView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=UTF-8");

        //绑定与解绑返回数据区别  绑定返回数据包含connection
        if (model.get("connection") == null) {
            response.getWriter().write("<h3>解绑成功</h3>");
        } else {
            response.getWriter().write("<h3>绑定成功</h3>");
        }
    }
}
