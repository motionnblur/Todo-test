package com.example.todotest.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("init() method has been get invoked");
        log.debug("Filter name is "+filterConfig.getFilterName());
        log.debug("ServletContext name is"+filterConfig.getServletContext());
        log.debug("init() method is ended");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("doFilter() method is invoked");
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        log.debug("doFilter() method is ended");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
