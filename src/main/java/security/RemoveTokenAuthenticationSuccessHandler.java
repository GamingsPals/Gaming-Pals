package security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RemoveTokenAuthenticationSuccessHandler  extends SimpleUrlLogoutSuccessHandler {
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

                Cookie cookie2 = new Cookie("username", null);
                cookie2.setPath("/");
                cookie2.setMaxAge(0);
                Cookie cookie = new Cookie("token", null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                response.addCookie(cookie2);
            //set our response to OK status
            response.setStatus(HttpServletResponse.SC_OK);

            //since we have created our custom success handler, its up to us to where
            //we will redirect the user after successfully login
            response.sendRedirect(  request.getHeader("Referer"));
        super.onLogoutSuccess(request, response, authentication);
    }
}
