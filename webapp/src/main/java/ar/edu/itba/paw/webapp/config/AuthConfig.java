package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@Configuration
@ComponentScan({"ar.edu.itba.paw.webapp.auth","ar.edu.itba.paw.webapp.restcontrollers"})
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Value("classpath:key.txt")
    private Resource key;

    private final String BASE_URL = "/api/v1";

    @Autowired
    private HireNetUserDetails hireNetUserDetails;

    @Autowired
    private AccessDecisionVoter ownershipVoter;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hireNetUserDetails).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http = http.csrf().disable();


        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint((request,response,ex)->response.sendError(HttpServletResponse.SC_UNAUTHORIZED,ex.getMessage()))
                .and();


        http.authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .antMatchers("/api/v1/users/*/rankings").hasRole("PROFESSIONAL")
                .antMatchers(HttpMethod.POST,"/api/v1/job-posts").hasRole("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/v1/job-posts/**").hasRole("PROFESSIONAL")
                .antMatchers(HttpMethod.PUT,"/api/v1/job-posts/**").hasRole("PROFESSIONAL")
                .antMatchers("/api/v1/reviews","/api/v1/contracts/states/**","/api/v1/contracts/*/image").permitAll()
                .antMatchers("/api/v1/contracts/**","/api/v1/reviews/**","/api/v1/users/security").hasRole("CLIENT")
                .antMatchers(HttpMethod.GET,"/api/v1/job-posts/**","/api/v1/users/*/rates").permitAll()
                .antMatchers("/api/v1/login", "/api/v1/categories/**","/api/v1/job-cards/**","/api/v1/zones/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/users","/api/v1/users/*/verify","/api/v1/users/recover-account/**").anonymous()
                .antMatchers(HttpMethod.PUT,"/api/v1/users/recover-account/**").anonymous()
                .antMatchers(HttpMethod.GET,"/api/v1/users/*/rankings").hasRole("PROFESSIONAL")
                .antMatchers(HttpMethod.PUT,"/api/v1/users/**").hasRole("CLIENT")
                .anyRequest().permitAll();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List decisionVoters = Arrays.asList(
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                ownershipVoter);
        return new UnanimousBased(decisionVoters);
    }


    @Override @Bean
    public AuthenticationManager authenticationManagerBean()throws Exception{
        return super.authenticationManagerBean();
    }

}

