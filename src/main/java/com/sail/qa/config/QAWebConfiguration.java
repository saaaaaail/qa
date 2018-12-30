package com.sail.qa.config;

import com.sail.qa.interceptor.LoginRequiredInterceptor;
import com.sail.qa.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: sail
 * @Date: 2018/12/23 16:36
 * @Version 1.0
 */
@Configuration
public class QAWebConfiguration implements WebMvcConfigurer {

    @Autowired
    PassportInterceptor passportInterceptor = null;

    @Autowired
    LoginRequiredInterceptor requiredInterceptor = null;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(requiredInterceptor).addPathPatterns("/user/*");
    }
}
