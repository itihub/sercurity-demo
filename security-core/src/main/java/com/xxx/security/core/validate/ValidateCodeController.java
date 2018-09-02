package com.xxx.security.core.validate;

import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.validate.image.ImageCode;
import com.xxx.security.core.validate.sms.SmsCode;
import com.xxx.security.core.validate.sms.SmsCodeGenerator;
import com.xxx.security.core.validate.sms.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: 验证码处理控制器
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
    private ValidateCodeGenerator validateCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;


    /**
     * 依赖搜索
     * 收集系统中所有的 {@link ValidateCodeProcessor} 接口的实现
     * map key 为bean的名称
     */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping("code/{type}")
    public void createCode(HttpServletRequest request
            , HttpServletResponse response
            , @PathVariable String type) throws Exception {
        validateCodeProcessors.get(type + "CodeProcessor")
                .create(new ServletWebRequest(request, response));
    }

    /**
     * 图像验证生成控制器
     * @param request
     * @param response
     * @throws IOException
     */
    @Deprecated
    @GetMapping("code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建图形验证码
        ImageCode imageCode = (ImageCode) validateCodeGenerator.generate(new ServletWebRequest(request));
        //写入session
        sessionStrategy.setAttribute(new ServletWebRequest(request), SEEEION_KEY, imageCode);
        //写回 response
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    /**
     * 短信验证生成控制器
     * @param request
     * @param response
     * @throws IOException
     */
    @Deprecated
    @GetMapping("code/sms")
    public void createSMSCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        //创建短信验证码
        // FIXME: 2018/09/02 0002 修复一下
        ValidateCode smsCode = validateCodeGenerator.generate(new ServletWebRequest(request, response));
        //写入session
        sessionStrategy.setAttribute(new ServletWebRequest(request), SEEEION_KEY, smsCode);
        //从请求中拿获取发送手机验证码的手机号码
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        //发送短信验证码
        smsCodeSender.send(mobile,smsCode.getCode());
    }




}
