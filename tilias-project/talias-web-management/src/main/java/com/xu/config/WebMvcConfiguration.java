package com.xu.config;

import com.xu.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 扩展Spring MVC框架的消息转换器
     *
     * @param builder
     */
    public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
        log.info("扩展消息转换器...");
        builder.withJsonConverter(
                new JacksonJsonHttpMessageConverter(JacksonObjectMapper.jacksonObjectMapper())
        );
    }
}
