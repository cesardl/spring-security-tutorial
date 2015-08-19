package com.scytl.sample.web.handler;

import com.scytl.sample.dao.UserDetailsDao;
import com.scytl.sample.model.UserAttempts;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author cesardiaz
 */
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(LimitLoginAuthenticationProvider.class);

    UserDetailsDao userDetailsDao;

    public UserDetailsDao getUserDetailsDao() {
        return userDetailsDao;
    }

    public void setUserDetailsDao(UserDetailsDao userDetailsDao) {
        this.userDetailsDao = userDetailsDao;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication auth = super.authenticate(authentication);

            //if reach here, means login success, else an exception will be thrown
            //reset the user_attempts
            userDetailsDao.resetFailAttempts(authentication.getName());

            log.info("Successful authenticate");
            userDetailsDao.printAllUserAttempts(authentication.getName());

            return auth;
        } catch (BadCredentialsException e) {
            //invalid login, update to user_attempts
            userDetailsDao.updateFailAttempts(authentication.getName());

            log.warn("Bad Credentials authenticate");
            userDetailsDao.printAllUserAttempts(authentication.getName());

            throw e;
        } catch (LockedException e) {
            //this user is locked!
            String error;
            UserAttempts userAttempts
                    = userDetailsDao.getUserAttempts(authentication.getName());

            if (userAttempts != null) {
                Date lastAttempts = userAttempts.getLastModified();
                error = "User account is locked! <br /><br />Username : "
                        + authentication.getName() + "<br />Last Attempts : " + lastAttempts;
            } else {
                error = e.getMessage();
            }

            log.warn("Locked Account authenticate");
            userDetailsDao.printAllUserAttempts(authentication.getName());

            throw new LockedException(error);
        }
    }

}
