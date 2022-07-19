package org.apache.dolphinscheduler.api.configuration.config;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Willam
 * @date 2022/7/12 10:40 下午
 */
@Component
public class CasUserDetailService implements AuthenticationUserDetailsService {
    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
        CasAssertionAuthenticationToken casAssertionAuthenticationToken = (CasAssertionAuthenticationToken) authentication;
        AttributePrincipal principal = casAssertionAuthenticationToken.getAssertion().getPrincipal();
        Map attributes = principal.getAttributes();
        UserVO userVO = new UserVO();
        userVO.setIsFromNewLogin(Boolean.parseBoolean((String) attributes.get("isFromNewLogin")));
        userVO.setGender(Integer.parseInt((String) attributes.get("gender")) );
        userVO.setAuthenticationDate((String) attributes.get("authenticationDate"));
        userVO.setSuccessfulAuthenticationHandlers((String) attributes.get("successfulAuthenticationHandlers"));
        userVO.setUserId(Long.parseLong((String) attributes.get("userId")));
        userVO.setCredentialType((String) attributes.get("credentialType"));
        userVO.setAccountId(Long.parseLong((String) attributes.get("accountId")));
        userVO.setSerialVersionUID(Long.parseLong((String) attributes.get("serialVersionUID")));
        userVO.setPhone((String) attributes.get("phone"));
        userVO.setAuthenticationMethod((String) attributes.get("authenticationMethod"));
        userVO.setNickName((String) attributes.get("nickname"));
        userVO.setLongTermAuthenticationRequestTokenUsed(Boolean.parseBoolean((String) attributes.get("longTermAuthenticationRequestTokenUsed")));
        userVO.setEmail((String) attributes.get("email"));
        userVO.setAccount((String) attributes.get("account"));
        userVO.setStatus(Integer.parseInt((String) attributes.get("status")));
        userVO.setUsername(principal.getName());
        userVO.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_user"));
        return userVO;
    }
}
