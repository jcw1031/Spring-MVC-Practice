package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof UserException exception) {
                log.info("UserException resolve to 400");
                String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("exception", exception.getClass());
                    errorResult.put("message", exception.getMessage());

                    String result = objectMapper.writeValueAsString(errorResult);

                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    response.getWriter()
                            .write(result);
                    return new ModelAndView();
                } else {
                    return new ModelAndView("/error/500");
                }
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
