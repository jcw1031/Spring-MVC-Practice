package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {

    private static final String SESSION_COOKIE_NAME = "mySessionId";

    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     * <br>
     * 1. 세션 ID 생성(임의의 랜덤 값)<br>
     * 2. 세션 저장소에 세션 ID와 값 저장<br>
     * 3. 세션 ID로 응답 쿠키 생성 및 전달
     */
    public void createSession(Object value, HttpServletResponse response) {
        // 세션 ID 생성 후 값 저장
        String sessionId = UUID.randomUUID()
                .toString()
                .replaceAll("-", "");
        sessionStore.put(sessionId, value);

        // 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {
        return findByCookieName(request, SESSION_COOKIE_NAME)
                .map(Cookie::getValue)
                .map(sessionStore::get)
                .orElse(null);
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request) {
        findByCookieName(request, SESSION_COOKIE_NAME)
                .map(Cookie::getValue)
                .ifPresent(sessionStore::remove);
    }

    private Optional<Cookie> findByCookieName(HttpServletRequest request, String cookieName) {
        return Arrays.stream(request.getCookies())
                .filter(Objects::nonNull)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny();
    }
}
