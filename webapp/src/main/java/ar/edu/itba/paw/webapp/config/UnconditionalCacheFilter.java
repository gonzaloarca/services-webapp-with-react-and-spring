package ar.edu.itba.paw.webapp.config;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

public class UnconditionalCacheFilter extends OncePerRequestFilter {


    private static final int MAX_TIME = 31536000;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age="+ MAX_TIME +", immutable");
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
