package com.robot.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class WebConfiguration {

    private static final String mapping = "/h2/*";

    @Bean
    public ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings(mapping);
        registrationBean.addInitParameter("-webAllowOthers", "");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean shallowEtagHeaderFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ShallowEtagHeaderFilter());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns(mapping);
        return registration;
    }

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("p-name", "-value");
            }
        };
    }
}
