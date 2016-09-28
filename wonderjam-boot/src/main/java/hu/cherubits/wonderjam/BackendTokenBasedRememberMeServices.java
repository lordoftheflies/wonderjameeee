/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 *
 * @author lordoftheflies
 */
public class BackendTokenBasedRememberMeServices extends TokenBasedRememberMeServices {

    public BackendTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Cancelling cookie");
        Cookie cookie = new Cookie(getCookieName(), null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
//		if (cookieDomain != null) {
//			cookie.setDomain(cookieDomain);
//		}
        response.addCookie(cookie);
    }

    /**
     * Sets the cookie on the response.
     *
     * By default a secure cookie will be used if the connection is secure. You
     * can set the {@code useSecureCookie} property to {@code false} to override
     * this. If you set it to {@code true}, the cookie will always be flagged as
     * secure. If Servlet 3.0 is used, the cookie will be marked as HttpOnly.
     *
     * @param tokens the tokens which will be encoded to make the cookie value.
     * @param maxAge the value passed to {@link Cookie#setMaxAge(int)}
     * @param request the request
     * @param response the response to add the cookie to.
     */
    @Override
    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request,
            HttpServletResponse response) {
        String cookieValue = encodeCookie(tokens);
        Cookie cookie = new Cookie(getCookieName(), cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getCookiePath(request));
//		if (super. != null) {
//			cookie.setDomain(cookieDomain);
//		}
        if (maxAge < 1) {
            cookie.setVersion(1);
        }

//		if (useSecureCookie == null) {
        cookie.setSecure(request.isSecure());
        cookie.setHttpOnly(false);
//		}
//		else {
//			cookie.setSecure(useSecureCookie);
//		}

//		if (setHttpOnlyMethod != null) {
//			ReflectionUtils.invokeMethod(setHttpOnlyMethod, cookie, Boolean.TRUE);
//		}
        if (logger.isDebugEnabled()) {
            logger.debug("Note: Cookie will not be marked as HttpOnly because you are not using Servlet 3.0 (Cookie#setHttpOnly(boolean) was not found).");
        }

        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
//		String contextPath = request.getContextPath();
//		return contextPath.length() > 0 ? contextPath : "/";
        return "/";
    }
}
