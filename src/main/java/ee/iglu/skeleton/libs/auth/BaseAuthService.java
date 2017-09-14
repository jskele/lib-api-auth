package ee.iglu.skeleton.libs.auth;

import ee.iglu.skeleton.libs.session.ApiSessionHolder;
import ee.iglu.skeleton.libs.session.ApiSessionSerializer;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseAuthService<T> {

    @Autowired
    private ApiSessionHolder holder;

    @Autowired
    private ApiSessionSerializer serializer;

    protected abstract Class<T> getSessionClass();

    public abstract void loginAs(String email);

    protected void setSession(T session) {
        String token = serializer.writeAsToken(session);
        holder.setToken(token);
    }

    protected T getSession() {
        String token = holder.getToken();
        return serializer.readFromToken(token, getSessionClass());
    }
}
