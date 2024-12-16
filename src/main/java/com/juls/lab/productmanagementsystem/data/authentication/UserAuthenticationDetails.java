package com.juls.lab.productmanagementsystem.data.authentication;

import com.juls.lab.productmanagementsystem.data.model.Role;
import com.juls.lab.productmanagementsystem.data.model.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserAuthenticationDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isEnabled;
    private Collection <? extends  GrantedAuthority> authorities;


    public UserAuthenticationDetails(User user){
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isEnabled = user.isEnabled();

        this.authorities = mapRolesToAuthorities(user.getRole());
    }

    /**
     * Maps the user's role to Spring Security's GrantedAuthority format.
     * @param role The role of the user.
     * @return A collection of GrantedAuthority representing the user's role.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role){
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Indicates whether the user's account has not expired.
     * @return Always returns true, indicating the account never expires.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is not locked.
     * @return Always returns true, indicating the user is never locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) are not expired.
     * @return Always returns true, indicating the credentials never expire.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user account is currently enabled.
     * @return true if the user account is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
