package org.jast.mybatis.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.jast.mybatis.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import org.jast.mybatis.shiro.realm.UserRealm;
import org.springframework.beans.factory.config.MethodInvokingBean;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhiwen on 15-7-13.
 */
@Configuration
public class ShiroWebConfig {



    /**
     *
     * 缓存管理器 使用Ehcache实现
     */
    @Bean
    public EhCacheManager cacheManager(/*SecurityManager securityManager*/){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }


    /**
     *
     * 凭证匹配器
     */
    @Bean
    public RetryLimitHashedCredentialsMatcher credentialsMatcher(EhCacheManager cacheManager){
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     *
     * Realm实现
     */
    @Bean
    public UserRealm userRealm(CredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(true);
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("authenticationCache");
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        return userRealm;
    }

    /**
     *
     * 会话ID生成器
     */
    @Bean(name = "sessionIdGenerator")
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();
        return sessionIdGenerator;
    }

    /**
     *
     * 会话Cookie模板
     */
    @Bean(name = "sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(180000);
        return simpleCookie;
    }

    /**
     *
     * 会话DAO
     */
    @Bean(name = "sessionDao")
    public SessionDAO sessionDao(JavaUuidSessionIdGenerator sessionIdGenerator){
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return sessionDAO;
    }

    /**
     *
     * 相当于调用SecurityUtils.setSecurityManager(securityManager)
     */
    @Bean(name = "methodInvokingFactoryBean")
    public MethodInvokingBean methodInvokingFactoryBean(SecurityManager securityManager){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(new Object[]{securityManager});
        return methodInvokingFactoryBean;
    }

    /**
     *
     * 会话验证调度器
     */
    @Bean(name = "sessionValidationScheduler")
    public SessionValidationScheduler sessionValidationScheduler(DefaultWebSessionManager sessionManager){
        QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
        sessionValidationScheduler.setSessionValidationInterval(1800000);
        sessionValidationScheduler.setSessionManager(sessionManager);
        return sessionValidationScheduler;
    }



    /**
     *
     * 会话管理器
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(/*SessionValidationScheduler sessionValidationScheduler,*/SessionDAO sessionDAO,Cookie sessionIdCookie){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setGlobalSessionTimeout(180000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        return sessionManager;
    }

    /**
     *
     * 安全管理器
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(SessionManager sessionManager,CacheManager cacheManager,UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setCacheManager(cacheManager);
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;

    }



    /**
     *
     * 基于Form表单的身份验证过滤器
     */
    @Bean(name = "formAuthenticationFilter")
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        formAuthenticationFilter.setUsernameParam("username");
        formAuthenticationFilter.setPasswordParam("password");
        formAuthenticationFilter.setLoginUrl("/login");
        return formAuthenticationFilter;
    }

    /**
     *
     * Shiro的Web过滤器
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager,FormAuthenticationFilter formAuthenticationFilter){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setUnauthorizedUrl("/unauthorized");
        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("authc", formAuthenticationFilter);
        shiroFilter.setFilters(filters);
        Map<String, String> chainDefinitions = new HashMap<String, String>();
        chainDefinitions.put("/index","authc");
        chainDefinitions.put("/unauthorized","anon");
        chainDefinitions.put("/login","authc");
        chainDefinitions.put("/logout","logout");
        chainDefinitions.put("/**","user");
        shiroFilter.setFilterChainDefinitionMap(chainDefinitions);
        return shiroFilter;
    }

    /**
     *
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }
}
