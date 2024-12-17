package com.igeek.zncq.config;

import com.igeek.zncq.filter.JwtAuthenticationTokenFilter;
import com.igeek.zncq.filter.LoginFilter;
import com.igeek.zncq.filter.MyLoginUrlAuthenticationEntryPoint;
import com.igeek.zncq.handler.LoginFailHandler;
import com.igeek.zncq.handler.LoginSuccessHandler;
import com.igeek.zncq.handler.MyLogoutSuccessHandler;
import com.igeek.zncq.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/5 12:03
 * @email liuyia2022@163.com
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    IUserService userService;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailHandler loginFailHandler;
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    MyLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    MyLoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(loginFailHandler);
        loginFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return loginFilter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().configurationSource(corsConfigurationSource())
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailHandler)
                .and()
                .logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler).permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/pic/**").permitAll()
                .antMatchers("/swagger-ui.html","/webjars/**","/swagger-resources/**","/v2/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint)
                .and()
                .userDetailsService(userService);
        http.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return  authenticationConfiguration.getAuthenticationManager();
    }

}
