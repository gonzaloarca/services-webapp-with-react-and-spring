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
@ComponentScan({"ar.edu.itba.paw.webapp.auth", "ar.edu.itba.paw.webapp.restcontrollers"})
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Value("classpath:key.txt")
    private Resource key;

    private final String BASE_URL = "/api";

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
        web.ignoring().antMatchers("/static/**","/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http = http.csrf().disable();

        http = http.headers().cacheControl().disable().and();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage()))
                .and();

        String relativePath = BASE_URL;
        http.authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .antMatchers(relativePath.concat("/users/*/rankings")).hasRole("PROFESSIONAL")
                .antMatchers(HttpMethod.POST, relativePath.concat("/job-posts")).hasRole("CLIENT")
                .antMatchers(HttpMethod.POST, relativePath.concat("/job-posts/**")).hasRole("PROFESSIONAL")
                .antMatchers(HttpMethod.PUT, relativePath.concat("/job-posts/**")).hasRole("PROFESSIONAL")
                .antMatchers(relativePath.concat("/reviews"), relativePath.concat("/contracts/states/**"),
                        relativePath.concat("/contracts/*/image")).permitAll()
                .antMatchers(relativePath.concat("/contracts/**"), relativePath.concat("/reviews/**"),
                        relativePath.concat("/users/security")).hasRole("CLIENT")
                .antMatchers(HttpMethod.GET, relativePath.concat("/job-posts/**"), relativePath.concat("/users/*/rates")).permitAll()
                .antMatchers(relativePath.concat("/login"), relativePath.concat("/categories/**"),
                        relativePath.concat("/job-cards/**"), relativePath.concat("/zones/**")).permitAll()
                .antMatchers(HttpMethod.POST, relativePath.concat("/users"), relativePath.concat("/users/*/verify"),
                        relativePath.concat("/uses/recover-account/**")).anonymous()
                .antMatchers(HttpMethod.PUT, relativePath.concat("/users/recover-account/**")).anonymous()
                .antMatchers(HttpMethod.GET, relativePath.concat("/users/*/rankings")).hasRole("PROFESSIONAL")
                .antMatchers(HttpMethod.PUT, relativePath.concat("/users/**")).hasRole("CLIENT")
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


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

