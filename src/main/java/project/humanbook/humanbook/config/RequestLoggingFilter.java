package project.humanbook.humanbook.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        filterChain.doFilter(wrappedRequest, response);

        String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);

        System.out.println("Incoming Request Data:");
        System.out.println("URL: " + wrappedRequest.getRequestURL());
        System.out.println("Method: " + wrappedRequest.getMethod());
        Enumeration<String> parameterNames = wrappedRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = wrappedRequest.getParameter(paramName);
            System.out.println(paramName + ": " + paramValue);
        }
        System.out.println("Body: " + body);
    }
}
