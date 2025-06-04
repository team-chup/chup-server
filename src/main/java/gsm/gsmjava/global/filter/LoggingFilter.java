package gsm.gsmjava.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import static gsm.gsmjava.global.filter.JwtReqFilter.AUTHORIZATION_HEADER;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        UUID logId = UUID.randomUUID();
        long startTime = System.currentTimeMillis();

        try {
            requestLogging(requestWrapper, logId);
            filterChain.doFilter(requestWrapper, responseWrapper);
        } catch (Exception e) {
            log.error("LoggingFilter의 FilterChain에서 예외가 발생했습니다.");
            e.printStackTrace();
        } finally {
            responseLogging(responseWrapper, startTime, logId);
            try {
                responseWrapper.copyBodyToResponse();
            } catch (IOException e) {
                log.error("LoggingFilter에서 response body를 출력하는 도중 예외가 발생했습니다.");
                e.printStackTrace();
            }
        }
    }

    private void requestLogging(ContentCachingRequestWrapper request, UUID logId) {
        log.info(
                String.format(
                        "Log-ID: %s, IP: %s, URI: %s, Http-Method: %s, Params: %s, Content-Type: %s, User-Cookies: %s, User-Agent: %s, Authorization: %s, Request-Body: %s",
                        logId,
                        request.getRemoteAddr(),
                        request.getRequestURI(),
                        request.getMethod(),
                        request.getQueryString(),
                        request.getContentType(),
                        request.getCookies() != null ? String.join(", ", getCookiesAsString(request.getCookies())) : "[none]",
                        request.getHeader("User-Agent"),
                        request.getHeader(AUTHORIZATION_HEADER),
                        getRequestBody(request.getContentAsByteArray())
                )
        );
    }

    private void responseLogging(ContentCachingResponseWrapper response, long startTime, UUID logId) {
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        log.info(
                String.format(
                        "Log-ID: %s, Status-Code: %d, Content-Type: %s, Response Time: %dms, Headers: %s, Response-Body: %s",
                        logId,
                        response.getStatus(),
                        response.getContentType(),
                        responseTime,
                        getResponseHeaders(response),
                        new String(response.getContentAsByteArray(), StandardCharsets.UTF_8)
                )
        );
    }

    private String getRequestBody(byte[] byteArrayContent) {
        String oneLineContent = new String(byteArrayContent, StandardCharsets.UTF_8).replaceAll("\\s", "");
        return StringUtils.hasText(oneLineContent) ? oneLineContent : "[empty]";
    }

    private String[] getCookiesAsString(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .map(cookie -> String.format("%s=%s", cookie.getName(), cookie.getValue()))
                .toArray(String[]::new);
    }

    private String getResponseHeaders(HttpServletResponse response) {
        return response.getHeaderNames().stream()
                .map(name -> name + "=" + String.join(",", response.getHeaders(name)))
                .reduce((a, b) -> a + "; " + b)
                .orElse("[none]");
    }
}
