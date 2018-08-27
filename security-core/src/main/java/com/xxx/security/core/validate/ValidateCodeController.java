package com.xxx.security.core.validate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @Description:
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

    private static final String SEEEION_KEY = "SEEION_KEY_IMAGE_CODE";

    @GetMapping("code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode(request, response);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SEEEION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    private ImageCode createImageCode(HttpServletRequest request, HttpServletResponse response) {

        //定义生成图片的尺寸
        int width = 80; //宽 80
        int height = 32; //高 32
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取BufferedImage的Graphics  用来画对象
        Graphics g = image.getGraphics();
        // 设置背景颜色
        g.setColor(new Color(0xDCDCDC));
        //背景颜色填充
        g.fillRect(0, 0, width, height);
        // 绘制边框
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // 生成验证码
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //计算数学公式
        int rnd = calc(verifyCode);

        return new ImageCode(image, rnd, 60);
    }

    //算数运算符
    private static char[] ops = new char[] {'+', '-', '*'};

    //生成数学公式
    private String generateVerifyCode(Random rdm) {
        //生成随机数
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        //随机拿出算术运算符
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        //拼接
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    //计算公式
    private static int calc(String exp) {
        try {
            //js引擎
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
