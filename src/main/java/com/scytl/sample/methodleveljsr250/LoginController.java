package com.scytl.sample.methodleveljsr250;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.RolesAllowed;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal) {
        String name = principal.getName();

        log.info("printWelcome: name {}", name);

        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is welcome page!");

        return "main_page";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            log.info("error {}", error);
            model.addObject("error", "Invalid username and password!");
        }

        model.setViewName("login_page");

        log.info("login: {}", model);

        return model;
    }

    @Deprecated
    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "true");

        log.info("loginError: ModelMap {}", model.keySet());

        return "login_page";
    }

    @RolesAllowed({"ROLE_REGULAR_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    public String common(ModelMap model) {
        log.info("common: ModelMap {}", model.keySet());
        return "common_page";
    }

    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(ModelMap model) {
        log.info("admin: ModelMap {}", model.keySet());
        return "admin_page";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        model.addAttribute("msg", "You've been logged out successfully.");

        log.info("logout: ModelMap {}", model.keySet());

        return "login_page";
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {
        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;
    }

}
