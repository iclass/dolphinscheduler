package org.apache.dolphinscheduler.api.configuration.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserVO implements UserDetails {
    private  Boolean isFromNewLogin;

    private Integer gender;

    private String authenticationDate;

    private String successfulAuthenticationHandlers;

    private Long userId;

    private String credentialType;

    private Long accountId;

    private Long serialVersionUID;

    private String phone;

    private String authenticationMethod;

    private String nickName;

    private Boolean longTermAuthenticationRequestTokenUsed;

    private String email;

    private String account;

    private Integer status;

    private Boolean isAccountNonExpired = true;

    private Boolean isAccountNonLocked = true;

    private Boolean isCredentialsNonExpired = true;

    private Boolean isEnabled = true;

    private String username;

    private String password;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
