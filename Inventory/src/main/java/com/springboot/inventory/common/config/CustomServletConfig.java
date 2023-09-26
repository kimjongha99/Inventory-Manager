package com.springboot.inventory.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@PropertySource("classpath:/application.properties")
public class CustomServletConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/assets/**").
                addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/static/imgs/").
                addResourceLocations("classpath:/imgs/**");

        //오강석추가
        registry.addResourceHandler("/image/**").
                addResourceLocations("classpath:/static/image/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/resources/**").
                addResourceLocations("/resources/");
    }
}
