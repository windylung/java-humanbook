package project.humanbook.humanbook.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        System.out.println("Incoming Request Data:");
        System.out.println("URL: " + wrappedRequest.getRequestURL());
        System.out.println("Method: " + wrappedRequest.getMethod());

        System.out.println("Headers:");
        Enumeration<String> headerNames = wrappedRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + wrappedRequest.getHeader(headerName));
        }

        System.out.println("Parameters:");
        Enumeration<String> parameterNames = wrappedRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = wrappedRequest.getParameter(paramName);
            System.out.println(paramName + ": " + paramValue);
        }

        System.out.println("Body: " + requestBody);

        System.out.println("Outgoing Response Data:");
        System.out.println("Status: " + wrappedResponse.getStatus());

        System.out.println("Headers:");
        for (String headerName : wrappedResponse.getHeaderNames()) {
            System.out.println(headerName + ": " + wrappedResponse.getHeader(headerName));
        }

        System.out.println("Body: " + responseBody);

        wrappedResponse.copyBodyToResponse();
    }
}
