package org.jast.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;

/**
 * Created by zhiwen on 15-4-9.
 */
@EnableWebMvc
@Configuration
@ComponentScan({"org.jast.mybatis.*.controller.*"})
public class MvcConfig  extends WebMvcConfigurerAdapter {

    @Bean
    public ContentNegotiatingViewResolver contentViewResolver(){
        ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        contentViewResolver.setViewResolvers(Arrays.<ViewResolver>asList(viewResolver));
        return contentViewResolver;
    }
}
