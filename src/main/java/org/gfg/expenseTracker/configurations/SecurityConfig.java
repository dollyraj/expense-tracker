package org.gfg.expenseTracker.configurations;

import org.gfg.expenseTracker.model.UserType;
import org.gfg.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.csrf().
                        disable().
                        httpBasic().
                        and().
                        authorizeRequests().
                        antMatchers("/signup/**").permitAll().//addExpenseType
                        antMatchers("/v1/expenseTracker/**").hasAuthority(UserType.ADMIN.name()).
                        //antMatchers("/v1/expenseTracker/getAllExpenseType").hasAuthority(UserType.ADMIN.name()).
                        //antMatchers("/v1/expenseTracker/getAllUsers").hasAuthority(UserType.ADMIN.name()).
                       // antMatchers("/v1/expenseTracker/addUserTxn").hasAuthority(UserType.ADMIN.name()).
                       // antMatchers("/v1/expenseTracker/fetchTxn").hasAuthority(UserType.ADMIN.name()).
                        //antMatchers("/v1/expenseTracker/fetchCalculatedResults").hasAuthority(UserType.ADMIN.name()).
                        //antMatchers("/v1/expenseTracker/updateUserProfile").permitAll().
                        and().
                        formLogin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    public PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }
}
