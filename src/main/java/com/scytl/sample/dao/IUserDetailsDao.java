package com.scytl.sample.dao;

import com.scytl.sample.model.UserAttempts;

/**
 * Created by cesardiaz on 07/08/15.
 *
 * @author cesardiaz
 */
public interface IUserDetailsDao {

    void updateFailAttempts(String username);

    void resetFailAttempts(String username);

    UserAttempts getUserAttempts(String username);

    void printAllUserAttempts(String username);
}
