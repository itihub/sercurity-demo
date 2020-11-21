package xyz.itihub.security.core.validate.image;

import xyz.itihub.security.core.properties.SecurityProperties;
import xyz.itihub.security.core.validate.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @description: 图像验证码默认生成
 * @author: Administrator
 * @date: 2018/08/31 0031
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {


    /**
     * 引入应用级配置文件
     */
    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public ImageCode generate(ServletWebRequest request) {

        //定义生成图片的尺寸
        /**
         * 默认配置：不进行配置则使用默认配置
         * 应用级配置：进行配置文件配置，则覆盖默认配置
         * 请求级配置：请求级配置，会覆盖应用级配置跟默认配置。优先级最高
         */
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width"
                , securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height"
                , securityProperties.getCode().getImage().getHeight());
        int length = securityProperties.getCode().getImage().getLength();
        //过期时间
        int expireIn = securityProperties.getCode().getImage().getExpireIn();

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

        return new ImageCode(image, String.valueOf(rnd), expireIn);
    }


    /**
     * 算数运算符
     */
    private static char[] ops = new char[]{'+', '-'};

    /**
     * 生成数学公式
     *
     * @param rdm
     * @return
     */
    private String generateVerifyCode(Random rdm) {
        //生成随机数
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        //随机拿出算术运算符
        char op1 = ops[rdm.nextInt(ops.length)];
        char op2 = ops[rdm.nextInt(ops.length)];
        //拼接
        String exp = "" + num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    /**
     * 计算公式
     *
     * @param exp g公式
     * @return
     */
    private static int calc(String exp) {
        try {
            //js引擎
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
