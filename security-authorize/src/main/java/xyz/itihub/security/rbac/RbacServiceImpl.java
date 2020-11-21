package xyz.itihub.security.rbac;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:RBAC实现类
 * @Author: JiZhe
 * @CreateDate: 2018/10/31 15:10
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        boolean hasPermission = false;

        Object principal = authentication.getPrincipal();

        //判断principal是否是 UserDetails类型  匿名用户是一个字符串
        if (principal instanceof UserDetails){
            //获取用户唯一标识
            String username = ((UserDetails) principal).getUsername();
            // TODO: 2018/10/31 根据用户唯一标识去DB查该用户的角色以及该角色的资源信息
            Set<String> urls = new HashSet<>();  //模拟查询到用户url

            for (String url : urls) {
                //进行路径判断 是否有权限
                if (antPathMatcher.match(url, request.getRequestURI())){
                    hasPermission = true;
                    break;
                }
            }

        }
        // FIXME: 2018/11/02 0002 此处应该返回 hasPermission ,为了测试直接返回 true
        return true;
    }

}
