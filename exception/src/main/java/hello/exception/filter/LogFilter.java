package hello.exception.filter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        DispatcherType dispatcherType = request.getDispatcherType();

        try {
            log.info("REQUEST [{}][{}][{}]", uuid, dispatcherType, requestURI);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception exception) {
            log.info("EXCEPTION! {}", exception.getMessage());
            throw exception;
        } finally {
            log.info("RESPONSE [{}][{}][{}]", uuid, dispatcherType, requestURI);
        }
    }
}
