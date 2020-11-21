package xyz.itihub.security.core.authentication.mobile.userdetails;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 扩展UserDetails 接口
 * 增加mobile属性
 */
public interface MobileUserDetails extends UserDetails {

    String getMobile();
}
