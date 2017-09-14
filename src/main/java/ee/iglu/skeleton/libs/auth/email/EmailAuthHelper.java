package ee.iglu.skeleton.libs.auth.email;

import ee.iglu.skeleton.libs.session.ApiSessionSerializer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class EmailAuthHelper {
    static final String LOGIN_LINK_PATH = "/api/auth/email";
    static final String TOKEN_PARAM_NAME = "token";

    private final HttpServletRequest request;
    private final ApiSessionSerializer serializer;

    @Data
    @RequiredArgsConstructor
    private static class EmailAuthSession {
        private final String email;
    }

    public String createLinkFor(String email) {
        return getLocation(request) + LOGIN_LINK_PATH + "?" + TOKEN_PARAM_NAME + "=" + createTokenFor(email);
    }

    String getEmailFromToken(String token) {
        EmailAuthSession session = serializer.readFromToken(token, EmailAuthSession.class);
        return session.getEmail();
    }

    private String createTokenFor(String email) {
        return serializer.writeAsToken(new EmailAuthSession(email));
    }

    private String getLocation(HttpServletRequest request) {
        String proto = request.getHeader("X-Forwarded-Proto");
        if (proto == null) {
            proto = request.getScheme();
        }

        String host = request.getHeader("X-Forwarded-Host");
        if (host == null) {
            host = request.getHeader("Host");
        }
        return proto + "://" + host;
    }
}
