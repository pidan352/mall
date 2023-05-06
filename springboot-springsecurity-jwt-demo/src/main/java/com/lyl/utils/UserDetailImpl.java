package com.lyl.utils;

import com.lyl.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 建议不要对pojo类进行修改，因为pojo是一个独立模块，否则 pojo要依赖于SpringSecurity，在分布式应用中其他模块都依赖于pojo
 */
public class UserDetailImpl implements UserDetails {

    private SysUser sysUser;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailImpl(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        this.sysUser = sysUser;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsercode();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
