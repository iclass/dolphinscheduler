package org.apache.dolphinscheduler.api.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.dolphinscheduler.api.configuration.config.CASClientProperties;
import org.apache.dolphinscheduler.api.configuration.config.UserVO;
import org.apache.dolphinscheduler.api.service.SessionService;
import org.apache.dolphinscheduler.api.service.TenantService;
import org.apache.dolphinscheduler.api.service.UsersService;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.SessionMapper;
import org.apache.dolphinscheduler.dao.mapper.UserMapper;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.apache.dolphinscheduler.api.enums.Status.IP_IS_EMPTY;

@RestController
@RequestMapping("/sso")
public class SSOController extends BaseController{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/casLogin")
    @ApiOperation(value = "登录跳转页")
    @Transactional(rollbackFor = RuntimeException.class)
    public void casLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CasAuthenticationToken authentication = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserVO userVO = (UserVO)authentication.getPrincipal();
        User user = userMapper.queryByPersonId(userVO.getUserId());
        String jsessionid = request.getSession().getId();
        String ip = getClientIpAddress(request);
        if (user != null){
            //执行登录
            sessionService.createSSOSession(jsessionid,user.getId(),ip);
        }else{
            //创建租户及用户并登陆
            Integer tenantId = tenantService.createSSOTenant(userVO.getUsername());
            User createUser = usersService.createSSOUser(userVO.getUsername(),"",userVO.getEmail(),tenantId,userVO.getPhone(),"default",1,userVO.getUserId());
            sessionService.createSSOSession(jsessionid,createUser.getId(),ip);
        }
        Assertion assertion = authentication.getAssertion();
        if (assertion != null) {
            response.sendRedirect(new CASClientProperties().getFrontUrl()+"?jsessionid="+jsessionid);
        }
    }
}
