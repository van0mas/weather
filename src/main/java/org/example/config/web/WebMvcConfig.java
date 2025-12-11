package org.example.config.web;

import org.example.config.props.AppConstants;
import org.example.interceptor.AuthFormRedirectInterceptor;
import org.example.interceptor.UserContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.example.interceptor.AuthInterceptor;

@Configuration
@EnableWebMvc
@Import(ThymeLeafConfig.class)
@ComponentScan("org.example")
@EnableScheduling
@EnableTransactionManagement
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserContextInterceptor userContextInterceptor;
    @Autowired
    private AuthFormRedirectInterceptor authFormRedirectInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/static/css/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("/static/images/");
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userContextInterceptor)
                .addPathPatterns("/**")
                .order(1);
        registry.addInterceptor(authFormRedirectInterceptor)
                .addPathPatterns("/auth/login")
                .addPathPatterns("/auth/register")
                .order(2);
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(
                        AppConstants.SecuredPaths.HOME,
                        AppConstants.SecuredPaths.LOGOUT,
                        AppConstants.SecuredPaths.WEATHER_ALL
                )
                .order(3);
    }
}
