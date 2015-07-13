package org.jast.mybatis.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhiwen on 15-7-13.
 */
@Configuration
public class ShiroConfig {

    /**
     *
     * 安全管理器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        return shiroFilter;
    }

    /**
     *
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        return new DefaultWebSecurityManager();
    }
}
