package com.scytl.sample.web.controller;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author cesardiaz
 */
@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/main**", method = RequestMethod.GET)
    public String main(ModelMap model, Principal principal) {
        String name = principal.getName();

        log.info("main: name {}", name);

        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");

        return "main_page";
    }

    /**
     * This update page is for user login with password only. If user is login
     * via remember me cookie, send login to ask for password again. To avoid
     * stolen remember me cookie to update info.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/main/update**", method = RequestMethod.GET)
    public ModelAndView updatePage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        if (isRememberMeAuthenticated()) {
            //send login for update
            setRememberMeTargetUrlToSession(request);
            model.addObject("loginUpdate", true);
            model.setViewName("/login");
        } else {
            model.setViewName("update");
        }

        log.info("updatePage: {}", model);

        return model;
    }

    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public String admin(ModelMap model) {
        log.info("admin: ModelMap {}", model.keySet());

        return "admin_page";
    }

    /**
     * Check if user is login by remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
    private boolean isRememberMeAuthenticated() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    /**
     * save targetURL in session
     */
    private void setRememberMeTargetUrlToSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("targetUrl", "/main/update");
        }
    }

    @RolesAllowed({"ROLE_REGULAR_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    public String common(ModelMap model) {
        log.info("common: ModelMap {}", model.keySet());
        return "common_page";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            log.info("login.error {}", error);
            // model.addObject("error", "Invalid username and password!");
            model.addObject("error",
                    getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));

            //login form for update page
            //if login error, get the targetUrl from session again.
            String targetUrl = getRememberMeTargetUrlFromSession(request);

            if (StringUtils.hasText(targetUrl)) {
                model.addObject("targetUrl", targetUrl);
                model.addObject("loginUpdate", true);
            }
        }

        model.setViewName("login_page");

        log.info("login: {}", model);

        return model;
    }

    /**
     * get targetURL from session
     */
    private String getRememberMeTargetUrlFromSession(HttpServletRequest request) {
        String targetUrl = "";
        HttpSession session = request.getSession(false);
        if (session != null) {
            targetUrl = session.getAttribute("targetUrl") == null ? ""
                    : session.getAttribute("targetUrl").toString();
            log.info("Targer url: {}", targetUrl);
        } else {
            log.info("Session null");
        }
        return targetUrl;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        model.addAttribute("msg", "You've been logged out successfully.");

        log.info("logout: ModelMap {}", model.keySet());

        return "login_page";
    }

    //customize the error message
    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception
                = (Exception) request.getSession().getAttribute(key);

        String error;
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }

        return error;
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {
        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            log.info("{}", userDetail);

            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;
    }

}
