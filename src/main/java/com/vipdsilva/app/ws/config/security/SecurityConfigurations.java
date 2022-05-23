package com.vipdsilva.app.ws.config.security;

import com.vipdsilva.app.ws.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Profile(value = {"prod", "test"})
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    //Injeção de dependência no controller
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/user").permitAll()
        .antMatchers(HttpMethod.POST, "/api/auth").permitAll()
        .antMatchers(HttpMethod.GET, "/api/films/*").permitAll()
        .antMatchers(HttpMethod.GET, "/api/people/*").permitAll()
        .antMatchers(HttpMethod.GET, "/api/colors/*").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/user/*").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/api/colors").hasRole("MODERADOR")
        .antMatchers(HttpMethod.POST, "/api/films").hasRole("MODERADOR")
        .antMatchers(HttpMethod.POST, "/api/people").hasRole("MODERADOR")
        .antMatchers(HttpMethod.PUT, "/api/colors").hasRole("MODERADOR")
        .antMatchers(HttpMethod.PUT, "/api/films").hasRole("MODERADOR")
        .antMatchers(HttpMethod.PUT, "/api/people").hasRole("MODERADOR")
        .antMatchers(HttpMethod.DELETE, "/api/colors").hasRole("MODERADOR")
        .antMatchers(HttpMethod.DELETE, "/api/films").hasRole("MODERADOR")
        .antMatchers(HttpMethod.DELETE, "/api/people").hasRole("MODERADOR")
        .anyRequest().authenticated()
        .and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(new AuthByTokenFilter(tokenService, userRepository),
        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

}
