package com.ztth.api.path.config;

import com.ztth.core.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(getServiceAuthRestInterceptor()).addPathPatterns("/**");
        //registry.addInterceptor(getUserAuthRestInterceptor()).addPathPatterns("/**").excludePathPatterns("/api/user/username/**");
        super.addInterceptors(registry);
    }

  /*  @Bean
    ServiceAuthRestInterceptor getServiceAuthRestInterceptor(){
        return new ServiceAuthRestInterceptor();
    }

    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor(){
        return new UserAuthRestInterceptor();
    }*/
}
