package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.demo.controller" })
public class WebMvcConfig implements WebMvcConfigurer{

	@Bean
	public InternalResourceViewResolver resolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/");

        registry
          .addResourceHandler("/images/**")
          .addResourceLocations("/images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // You'll need to make sure AuthenticationInterceptor is in the classpath
        registry.addInterceptor(new AuthenticationInterceptor())
            // Protect all URLs by default
            .addPathPatterns("/**")
            // Exclude the pages that must be accessible without login
            .excludePathPatterns("/userLogin", "/", "/logout", "/resources/**", "/images/**");
    }
}