package xyz.itihub.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.xxx.exception
 * @Description: 增强处理器
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 20:47
 * @UpdateUser: Revised author
 * @UpdateDate: 2018/8/25 20:47
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerException(Exception e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>();

        if (e instanceof UserNotExistException) {
            UserNotExistException userNotExistException = (UserNotExistException) e;
            return map;
        }
        return map;
    }
}
