package ee.iglu.skeleton.libs.auth.email;

import ee.iglu.skeleton.libs.auth.BaseAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
class EmailAuthController {

    private final EmailAuthHelper helper;
    private final BaseAuthService authService;

    @RequestMapping(EmailAuthHelper.LOGIN_LINK_PATH)
    public RedirectView login(@RequestParam(EmailAuthHelper.TOKEN_PARAM_NAME) String token) {
        String email = helper.getEmailFromToken(token);
        authService.loginAs(email);
        return new RedirectView("/");
    }
}
