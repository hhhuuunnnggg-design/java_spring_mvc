package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.hoidanit.laptopshop.service.CustomUserDetailsService;
import vn.hoidanit.laptopshop.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // đang ghi đè spring config
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    @Bean
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        // authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    // remember me, đang cấu hình là 30 ngày
    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Cấu hình bảo mật HTTP với lambdas (giúp mã cấu hình ngắn gọn hơn trong Spring
        // Security 6+)
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Cho phép tất cả các yêu cầu với các loại Dispatcher FORWARD và INCLUDE
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE)
                        .permitAll() // Cho phép tất cả người dùng truy cập các tài nguyên được chuyển tiếp hoặc bao
                                     // gồm bởi servlet

                        // Các endpoint công khai, không cần xác thực
                        .requestMatchers("/", "/login", "product/**", "/client/**", "/css/**", "/js/**", "/images/**")
                        .permitAll() // Cho phép truy cập không cần đăng nhập vào các đường dẫn này

                        // Chỉ cho phép người dùng có ROLE_ADMIN truy cập các đường dẫn bắt đầu với
                        // "/admin/"
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Yêu cầu xác thực cho tất cả các yêu cầu khác
                        .anyRequest().authenticated()) // Bất kỳ yêu cầu nào khác đều phải xác thực

                // Quản lý phiên đăng nhập
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Tạo phiên đăng nhập cho mỗi yêu cầu mới
                        .invalidSessionUrl("/logout?expired") // Chuyển hướng tới URL này khi phiên hết hạn

                        // Cài đặt số lượng phiên tối đa cho mỗi người dùng
                        .maximumSessions(1) // Mỗi tài khoản chỉ cho phép đăng nhập vào một phiên duy nhất
                        .maxSessionsPreventsLogin(false)) // Khi người dùng đăng nhập ở thiết bị khác, người đăng nhập
                                                          // trước sẽ bị đăng xuất

                // Cấu hình đăng xuất
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID") // Xóa cookie "JSESSIONID" khi người dùng đăng xuất
                        .invalidateHttpSession(true)) // Hủy phiên đăng nhập hiện tại khi đăng xuất

                // Cấu hình ghi nhớ đăng nhập
                .rememberMe(r -> r.rememberMeServices(rememberMeServices())) // Sử dụng dịch vụ ghi nhớ đăng nhập tuỳ
                                                                             // chỉnh

                // Cấu hình trang đăng nhập
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Trang đăng nhập tùy chỉnh
                        .failureUrl("/login?error") // Chuyển hướng đến URL này khi đăng nhập thất bại
                        .successHandler(customSuccessHandler()) // Dùng customSuccessHandler để xử lý khi đăng nhập
                                                                // thành công
                        .permitAll()) // Cho phép truy cập vào trang đăng nhập mà không cần xác thực

                // Xử lý lỗi quyền truy cập
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny")); // Chuyển hướng đến trang này nếu người
                                                                               // dùng cố truy cập trang không có quyền

        return http.build(); // Xây dựng cấu hình bảo mật
    }

}
