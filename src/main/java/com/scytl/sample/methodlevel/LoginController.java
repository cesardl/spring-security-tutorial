package com.scytl.sample.methodlevel;

/**
 *
 * @author cesardiaz
 */
//@Controller
public class LoginController {
/*
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal) {
        String name = principal.getName();
        model.addAttribute("username", name);
        log.info("printWelcome: ModelMap {}, Principal {}", model.keySet(), principal);
        return "main_page";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        log.info("login: ModelMap {}", model.keySet());
        return "login_page";
    }

    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "true");
        log.info("loginError: ModelMap {}", model.keySet());
        return "login_page";
    }

    @Secured({"ROLE_REGULAR_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    public String common(ModelMap model) {
        log.info("common: ModelMap {}", model.keySet());
        return "common_page";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(ModelMap model) {
        log.info("admin: ModelMap {}", model.keySet());
        return "admin_page";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        log.info("logout: ModelMap {}", model.keySet());
        return login(model);
    }
*/
}
