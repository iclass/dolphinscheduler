package org.apache.dolphinscheduler.api.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.dolphinscheduler.api.configuration.config.CASClientProperties;
import org.apache.dolphinscheduler.api.configuration.config.UserVO;
import org.apache.dolphinscheduler.api.service.QueueService;
import org.apache.dolphinscheduler.api.service.SessionService;
import org.apache.dolphinscheduler.api.service.TenantService;
import org.apache.dolphinscheduler.api.service.UsersService;
import org.apache.dolphinscheduler.dao.entity.Queue;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.UserMapper;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

    @Autowired
    private CASClientProperties casClientProperties;

    @Autowired
    private QueueService queueService;

    @GetMapping("/casLogin")
    @ApiOperation(value = "登录跳转页")
    @Transactional(rollbackFor = Exception.class)
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
            //创建队列、租户、用户并登陆
            Queue queue = queueService.createSSOQueue(userVO.getUsername(),userVO.getUsername());
            Integer tenantId = tenantService.createSSOTenant(userVO.getUsername(),queue.getId());
            User createUser = usersService.createSSOUser(userVO.getUsername(),"ab123456",userVO.getEmail(),tenantId,userVO.getPhone(),queue.getQueue(),1,userVO.getUserId());
            sessionService.createSSOSession(jsessionid,createUser.getId(),ip);
        }
        Assertion assertion = authentication.getAssertion();
        if (assertion != null) {
            response.sendRedirect(casClientProperties.getFrontUrl()+"?jsessionid="+jsessionid);
        }
    }
}
