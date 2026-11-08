package com.productionready.nplusone.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class QueryCountFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(QueryCountFilter.class);
    private static final int WARN_THRESHOLD = 10;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        QueryCounter.reset();
        long start = System.nanoTime();
        try {
            chain.doFilter(request, response);
        } finally {
            long millis = (System.nanoTime() - start) / 1_000_000;
            int queries = QueryCounter.count();
            String uri = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
            if (queries > WARN_THRESHOLD) {
                log.warn("{} {} -> {} queries in {} ms (possible N+1)", request.getMethod(), uri, queries, millis);
            } else {
                log.info("{} {} -> {} queries in {} ms", request.getMethod(), uri, queries, millis);
            }
        }
    }
}
