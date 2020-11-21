package xyz.itihub.securith.app.social;

import xyz.itihub.securith.app.exception.AppSecretException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @description: App注册工具类
 * @author: Administrator
 * @date: 2018/10/24 0024
 */
@Component
public class AppSingUpUtils {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 操作数据库类
     */
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    /**
     * 保存第三方用户信息到缓存
     * @param request
     * @param connectionData 用户信息
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData) {

        redisTemplate.opsForValue().set(getKey(request), connectionData, 10, TimeUnit.MINUTES);
    }

    /**
     *将第三方用户信息绑定持久化的DB
     * @param request
     * @param userId
     */
    public void doPostSingUp(WebRequest request, String userId){
        String key = getKey(request);
        if (!redisTemplate.hasKey(key)){
            throw new AppSecretException("无法找到用户社交账号信息");
        }
        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
                .createConnection(connectionData);
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);

        redisTemplate.delete(key);
    }


    /**
     * 获取存储key
     * @param request
     * @return
     */
    private String getKey(WebRequest request) {
        //从请求头获取设备ID
        String deviceId = request.getHeader("deviceId");

        if (StringUtils.isBlank(deviceId)){
            throw new AppSecretException("请在请求头携带deviceId参数");
        }

        //构建请求key
        return String.format("xxx:security:social.connect.%s", deviceId);
    }
}
