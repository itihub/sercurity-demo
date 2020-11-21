package xyz.itihub.securith.app.validate.code.impl;

import xyz.itihub.security.core.enums.ValidateCodeType;
import xyz.itihub.security.core.exception.ValidateCodeException;
import xyz.itihub.security.core.validate.ValidateCode;
import xyz.itihub.security.core.validate.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Description: app验证码存储
 * @Author: JiZhe
 * @CreateDate: 2018/10/21 18:03
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    /**
     * redis操作模板
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * redis验证码存储
     * @param request
     * @param validateCode 需要存放的验证码
     * @param validateCodeType  验证码类型
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        Duration duration = Duration.between(LocalDateTime.now(), validateCode.getExpireTime());
        redisTemplate.opsForValue().set(buildKey(request, validateCodeType)
                , validateCode, duration.getSeconds(), TimeUnit.SECONDS);
    }

    /**
     * redis获取验证码
     * @param request
     * @param validateCodeType  验证码类型
     * @return
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
        if (value == null){
            return null;
        }
        return (ValidateCode) value;
    }

    /**
     * redis清除验证码
     * @param request
     * @param validateCodeType 验证码类型
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisTemplate.delete(buildKey(request, validateCodeType));
    }

    /**
     * 构建redis存储key
     * @param request
     * @param type  验证码类型
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type){
        //获取请求头设备ID
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)){
            throw new ValidateCodeException("请在请求头携带deviceId参数");
        }
        return String.format("code:%s:%s", type.toString().toLowerCase(), deviceId);
    }
}
