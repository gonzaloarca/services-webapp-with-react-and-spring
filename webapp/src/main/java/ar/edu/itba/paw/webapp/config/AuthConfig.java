package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.HireNetUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@Configuration
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Value("classpath:key.txt")
    private Resource key;

    @Autowired
    private HireNetUserDetails hireNetUserDetails;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hireNetUserDetails).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String keyString = StreamUtils.copyToString( key.getInputStream(), Charset.availableCharsets().get("utf-8") );

        http.authorizeRequests()
                .antMatchers("/create-job-post").hasAnyRole("PROFESSIONAL")
                .antMatchers("/contract/**").hasRole("CLIENT")
                .antMatchers("/login","/register").anonymous()
                .antMatchers("/**").permitAll()
            .and().formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/",false)
                .loginPage("/login")
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
            .and()
                .sessionManagement()
                .invalidSessionUrl("/login")
            .and().rememberMe()
                .rememberMeParameter("rememberme")
                .userDetailsService(hireNetUserDetails)
                .key(keyString)
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
            .and().exceptionHandling()
                .accessDeniedPage("/403")
            .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

