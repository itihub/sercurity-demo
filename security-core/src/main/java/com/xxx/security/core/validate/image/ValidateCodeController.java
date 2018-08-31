package com.xxx.security.core.validate.image;

import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.validate.ValidateCodeGenerator;
import com.xxx.security.core.validate.image.ImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 图形验证码处理控制器
 * @Author: JiZhe
 * @CreateDate: 2018/8/27 21:52
 */
@Slf4j
@RestController
public class ValidateCodeController {

    /**
     * 操作session工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * session key
     */
    public static final String SEEEION_KEY = "SEEION_KEY_IMAGE_CODE";

    /**
     * 引入应用级配置文件
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;




    @GetMapping("code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建图形验证码
        ImageCode imageCode = imageCodeGenerator.generateImageCode(new ServletWebRequest(request));
        //写入session
        sessionStrategy.setAttribute(new ServletWebRequest(request), SEEEION_KEY, imageCode);
        //写回 response
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }




}
