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
        // v6. lamda
        http
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                DispatcherType.INCLUDE)
                        .permitAll() // cho phep tat ca
                        .requestMatchers("/", "/login", "product/**", "/client/**", "/css/**", "/js/**", "/images/**")
                        .permitAll()
                        // ROLE_ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()) // bất kể cái request nào ta cũng cần phải xác thức

                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .invalidSessionUrl("/logout?expired")
                        // trong 1 phiên đăng nhập đc tối đa bao nhiêu người đăng nhập ké đc
                        .maximumSessions(1)
                        // (false người thứ 2 đăng nhập thì sẽ đá người trước ra) (true người t2 đăng
                        // nhập r, vẫn phải chờ người 1 logout để ngu2 sd)
                        .maxSessionsPreventsLogin(false))
                // đăng xuất ra thì xóa cookie
                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))

                .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        // CustomSuccessHandler.java
                        .successHandler(customSuccessHandler())
                        .permitAll())
                // dùng để hiện lỗi 403 khi người đung đổi url tào lao
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

        return http.build();
    }

}
