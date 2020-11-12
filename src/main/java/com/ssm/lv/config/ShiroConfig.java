package com.ssm.lv.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import com.ssm.lv.realm.AccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lv
 * @date 2020/8/12 - 21:03
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean=new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        //权限设置
        Map<String,String> map=new HashMap<>();
        map.put("/main","authc");
      map.put("/admin/main","roles[admin]");
        map.put("/admin/main","perms[admin]");
        filterFactoryBean.setFilterChainDefinitionMap(map);
        //设置登录页面
        filterFactoryBean.setLoginUrl("/skip/login");
        //设置未授权页面
        filterFactoryBean.setUnauthorizedUrl("/unauth");
        return filterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("accountRealm") AccountRealm accountRealm){
    DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
    manager.setRealm(accountRealm());
    return manager;
    }

    @Bean
    public AccountRealm accountRealm(){
        return new AccountRealm();
    }
    //配置shiro整合thymeleaf的方言
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
