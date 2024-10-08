package org.ecommerce.shared_database_api.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.io.IOException;


@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // Log details of the request
        logger.info("Request Method: {}", httpRequest.getMethod());
        logger.info("Request URI: {}", httpRequest.getRequestURI());
        logger.info("Remote Address: {}", httpRequest.getRemoteAddr());
        
        // Log headers
        httpRequest.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            logger.info("Header {}: {}", headerName, httpRequest.getHeader(headerName));
        });

        // Proceed with the request
        chain.doFilter(request, response);
    }
}
