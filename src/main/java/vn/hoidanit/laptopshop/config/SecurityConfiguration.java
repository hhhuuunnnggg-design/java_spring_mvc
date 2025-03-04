package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.hoidanit.laptopshop.service.CustomUserDetailsService;
import vn.hoidanit.laptopshop.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
//@Configuration
//@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    // Định nghĩa bean PasswordEncoder sử dụng thuật toán BCrypt để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Định nghĩa bean UserDetailsService sử dụng CustomUserDetailsService để tải dữ liệu người dùng
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    // Định nghĩa bean DaoAuthenticationProvider sử dụng UserDetailsService và PasswordEncoder để xác thực
    @Bean
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // Định nghĩa bean SpringSessionRememberMeServices để quản lý chức năng "remember me" cho phiên làm việc
    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    // Định nghĩa bean AuthenticationSuccessHandler để xử lý sự kiện xác thực thành công
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }

    // Cấu hình StrictHttpFirewall để cho phép "//" trong URL
    @Bean
    public HttpFirewall allowDoubleSlashFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true); // Cho phép URL chứa "//"
        return firewall;
    }

    // Cấu hình chuỗi lọc bảo mật
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE)
                        .permitAll() // Cho phép tất cả các yêu cầu có kiểu dispatcher là FORWARD và INCLUDE
                        .requestMatchers("/", "/login", "product/**", "/client/**", "/css/**", "/js/**", "/images/**", "/register","/products/**")
                        .permitAll() // Cho phép tất cả các yêu cầu đến các URL này mà không cần xác thực
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Chỉ cho phép các yêu cầu đến URL /admin/** nếu người dùng có vai trò ADMIN
                        .anyRequest().authenticated()) // Yêu cầu xác thực cho tất cả các yêu cầu khác
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Luôn tạo phiên làm việc mới cho mỗi yêu cầu
                        .invalidSessionUrl("/logout?expired") // Chuyển hướng đến URL /logout?expired nếu phiên làm việc không hợp lệ
                        .maximumSessions(1) // Giới hạn số phiên làm việc tối đa là 1 cho mỗi người dùng
                        .maxSessionsPreventsLogin(false)) // Cho phép người dùng đăng nhập lại nếu phiên làm việc trước đó đã hết hạn
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID") // Xóa cookie JSESSIONID khi người dùng đăng xuất
                        .invalidateHttpSession(true)) // Vô hiệu hóa phiên làm việc HTTP khi người dùng đăng xuất
                .rememberMe(r -> r.rememberMeServices(rememberMeServices())) // Sử dụng dịch vụ "remember me" đã được cấu hình trước đó
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Chỉ định trang đăng nhập tùy chỉnh tại URL /login
                        .failureUrl("/login?error") // Chuyển hướng đến URL /login?error nếu đăng nhập thất bại
                        .successHandler(customSuccessHandler()) // Sử dụng trình xử lý thành công tùy chỉnh khi đăng nhập thành công
                        .permitAll()) // Cho phép tất cả các yêu cầu đến trang đăng nhập
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny")); // Chuyển hướng đến trang /access-deny nếu người dùng bị từ chối truy cập

        http.setSharedObject(HttpFirewall.class, allowDoubleSlashFirewall()); // Áp dụng StrictHttpFirewall đã tùy chỉnh
        return http.build(); // Xây dựng và trả về đối tượng SecurityFilterChain đã được cấu hình
    }
}
