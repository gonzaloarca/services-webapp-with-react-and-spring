package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.HireNetAuthenticationFailureHandler;
import ar.edu.itba.paw.webapp.auth.HireNetUserDetails;
import ar.edu.itba.paw.webapp.auth.OwnershipVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@Configuration
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Value("classpath:key.txt")
    private Resource key;

    @Autowired
    private HireNetUserDetails hireNetUserDetails;

    @Autowired
    private AccessDecisionVoter ownershipVoter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hireNetUserDetails).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**","/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String keyString = StreamUtils.copyToString( key.getInputStream(), Charset.availableCharsets().get("utf-8") );

        http.authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .antMatchers("/create-job-post").hasAnyRole("PROFESSIONAL")
                .antMatchers("/contract/**","/my-contracts", "/qualify-contract/**").hasRole("CLIENT")
                .antMatchers("/login","/register").anonymous()
                .antMatchers("/account/**").authenticated()
                .antMatchers("/**").permitAll()
            .and().formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/",false)
                .loginPage("/login")
                .failureHandler(authenticationFailureHandler())
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
            .and()
                .sessionManagement()
                .sessionAuthenticationErrorUrl("/login")
//                .invalidSessionUrl("/login")
            .and().rememberMe()
                .rememberMeParameter("rememberMeCheck")
                .key("keyString")
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .userDetailsService(hireNetUserDetails)
                .and().exceptionHandling()
                .accessDeniedPage("/403")
            .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager(){
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                ownershipVoter);
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new HireNetAuthenticationFailureHandler();
    }

}

