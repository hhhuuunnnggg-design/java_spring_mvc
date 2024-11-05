package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    // Cấu hình ViewResolver để xử lý các trang JSP
    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class); // Sử dụng lớp JstlView để hỗ trợ JSTL trong JSP
        bean.setPrefix("/WEB-INF/view/"); // Định nghĩa đường dẫn đến thư mục chứa các trang JSP
        bean.setSuffix(".jsp"); // Định nghĩa đuôi mở rộng của các trang là .jsp
        return bean;
    }

    // Đăng ký ViewResolver để Spring sử dụng
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }

    // Cấu hình đường dẫn các tài nguyên tĩnh như CSS, JavaScript, hình ảnh
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");
        registry.addResourceHandler("/client/**").addResourceLocations("/resources/client/");
    }
}
