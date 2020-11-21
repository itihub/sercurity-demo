package xyz.itihub.security.core.authentication.mobile.userdetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 扩展UserDetailsService 接口
 */
public interface MobileUserDetailsService {

    MobileUserDetails loadUserByMobile(String var1) throws UsernameNotFoundException;
}
