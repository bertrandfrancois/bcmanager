package com.frans.bcmanager.config;

import com.frans.bcmanager.format.StructuredCommunicationFormatterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        StructuredCommunicationFormatterFactory factory = new StructuredCommunicationFormatterFactory();
        registry.addFormatterForFieldAnnotation(factory);
    }
}
