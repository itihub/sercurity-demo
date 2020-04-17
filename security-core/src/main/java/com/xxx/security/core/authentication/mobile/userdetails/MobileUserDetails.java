package com.xxx.security.core.authentication.mobile.userdetails;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 扩展UserDetails 接口
 */
public interface MobileUserDetails extends UserDetails {

    String getMobile();
}
