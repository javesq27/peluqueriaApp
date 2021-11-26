package ub.tp.reservaPeluquerias.Model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private DataSource dataSource;
     
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, status from user where username=?")
            .authoritiesByUsernameQuery("select username, role from user where username=?")
            .and()
            .inMemoryAuthentication()
            .passwordEncoder(new BCryptPasswordEncoder())
            .withUser("user1")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .and()
            .withUser("user2")
            .password(passwordEncoder().encode("password"))
            .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/user", true)
            .failureUrl("/login?error=true")
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/");
    }
}