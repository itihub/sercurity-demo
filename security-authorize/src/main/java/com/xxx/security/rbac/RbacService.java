package com.xxx.security.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:RABC权限管理接口
 * @Author: JiZhe
 * @CreateDate: 2018/10/31 15:07
 */
public interface RbacService {

    /**
     *查询用户是否有当前请求路径的权限
     * @param request 当前请求的信息
     * @param authentication    当前用户的信息
     * @return 是否有权限
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
