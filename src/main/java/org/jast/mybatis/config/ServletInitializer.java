package org.jast.mybatis.config;

//import org.apache.logging.log4j.web.Log4jServletContextListener;
//import org.apache.logging.log4j.web.Log4jServletFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by zhiwen on 15-4-8.
 */
public class ServletInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));

        //servletContext.setInitParameter("isLog4jAutoInitializationDisabled", "true");
        //servletContext.setInitParameter("log4jContextName", "myApplication");
        //servletContext.setInitParameter("log4jConfiguration", "classpath:log4jConfiguration.xml");
        //配置Log4j2 http://logging.apache.org/log4j/2.x/manual/webapp.html

        /**
         <listener>
         <listener-class>org.apache.logging.log4j.web.Log4jServletContextListener</listener-class>
         </listener>

         <filter>
         <filter-name>log4jServletFilter</filter-name>
         <filter-class>org.apache.logging.log4j.web.Log4jServletFilter</filter-class>
         </filter>
         <filter-mapping>
         <filter-name>log4jServletFilter</filter-name>
         <url-pattern>/*</url-pattern>
         <dispatcher>REQUEST</dispatcher>
         <dispatcher>FORWARD</dispatcher>
         <dispatcher>INCLUDE</dispatcher>
         <dispatcher>ERROR</dispatcher>
         <dispatcher>ASYNC</dispatcher> //Servlet 3.0 w/ disabled auto-initialization only; not supported in 2.5
         </filter-mapping>
         */
        //servletContext.addListener(Log4jServletContextListener.class);
        //FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("log4jServletFilter", new Log4jServletFilter());
        //filterRegistration.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR), false, "log4jServletFilter");
        //filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR), false, "/*");
        //filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/");

        /**
         * 解决get中文乱码问题，post到不会乱码
         */
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter",characterEncodingFilter);
        encodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");

        /**
         * 配置*.do请求到Spring MVC
         */
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("*.do");

        /**
         * 添加shiro的过滤器
         * DelegatingFilterProxy作用是自动到spring容器查找名字为shiroFilter（filter-name）的bean并把所有Filter的操作委托给它。
         */
        registerProxyFilter(servletContext,"shiroFilter");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("org.jast.mybatis.config");

        //context.scan(ClassUtils.getPackageName(getClass()));
        return context;
    }

    private void registerProxyFilter(ServletContext servletContext, String name) {
        DelegatingFilterProxy filter = new DelegatingFilterProxy(name);
        filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        servletContext.addFilter(name, filter).addMappingForUrlPatterns(null, false, "/*");
    }
}
