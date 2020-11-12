package com.ssm.lv.realm;


import com.ssm.lv.entity.User;
import com.ssm.lv.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lv
 * @date 2020/8/12 - 17:09
 */
//自定义的relam
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录的用户信息
        Subject subject=SecurityUtils.getSubject();
        User user= (User) subject.getPrincipal();

        User user2=userService.userByName(user.getUsername());
        //设置角色
        Set<String> roles=new HashSet<>();
        roles.add(user2.getRole());
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo(roles);
        //设置权限
        info.addStringPermission(user2.getPermission());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
       User user=userService.userByName(token.getUsername());
        if(user !=null){
            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),getName());
            return info;
         }
        return null;
    }
}
