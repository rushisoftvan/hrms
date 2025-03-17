package com.ru.hrms_service.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component

public class CrossOriginFilter implements WebMvcConfigurer {


    public void addCorsMapping(CorsRegistry corsRegistry){

        corsRegistry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedOrigins("http://localhost:3000");
        
    }





}
