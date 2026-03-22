package com.xu.config;

import com.xu.interceptor.LoginInterceptor;
import com.xu.json.JacksonObjectMapper;
import com.xu.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 扩展Spring MVC框架的消息转换器
     * 将LocalDateTime统一为特定格式给前端
     * 或将前端特定格式的日期字符串，转换为 LocalDate系的日期类型
     * @param builder
     */
    public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
        log.info("扩展消息转换器...");
        builder.withJsonConverter(
                new JacksonJsonHttpMessageConverter(JacksonObjectMapper.jacksonObjectMapper())
        );
    }

    /**
     * 注册拦截器
     *
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册拦截器...");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
