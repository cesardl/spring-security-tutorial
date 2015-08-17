package com.scytl.sample.web.handler;

import com.scytl.sample.dao.UserDetailsDao;
import com.scytl.sample.model.UserAttempts;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 *
 * @author cesardiaz
 */
@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    UserDetailsDao userDetailsDao;

    @Autowired
    @Qualifier("userDetailsService")
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication auth = super.authenticate(authentication);

            //if reach here, means login success, else an exception will be thrown
            //reset the user_attempts
            userDetailsDao.resetFailAttempts(authentication.getName());

            return auth;
        } catch (BadCredentialsException e) {
            //invalid login, update to user_attempts
            userDetailsDao.updateFailAttempts(authentication.getName());

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

            userDetailsDao.printAllUserAttempts(authentication.getName());

            throw new LockedException(error);
        }
    }

}
