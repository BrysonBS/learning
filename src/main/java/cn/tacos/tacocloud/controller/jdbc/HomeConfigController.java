package cn.tacos.tacocloud.controller.jdbc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 直接通过实现 WebMvcConfigurer 接口方式实现Controller
 * addViewControllers方法添加一个Controller
 * ViewControllerRegistry Controller注册请求路径和对应页面
 */
@Configuration
public class HomeConfigController implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //注册Controller求路径和对应页面
        registry.addViewController("/home").setViewName("jdbc/home");
    }
}
