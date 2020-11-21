package xyz.itihub.securith.app.social.openid;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @description: 自定义openid认证token
 * @author: Administrator
 * @date: 2018/10/23 0023
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 用来存储openID即第三方社交用户唯一标识
     */
    private final Object principal;
    /**
     * 服务商ID
     */
    private String providerId;

    /**
     * 构建未认证token
     * @param openId
     * @param providerId
     */
    public OpenIdAuthenticationToken(String openId, String providerId) {
        super(null);
        this.principal = openId;
        this.providerId = providerId;
        setAuthenticated(false);
    }


    /**
     * 构建已认证token
     * @param authorities
     * @param principal
     * @param providerId
     */
    public OpenIdAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, String providerId) {
        super(authorities);
        this.principal = principal;
        this.providerId = providerId;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
