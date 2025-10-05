package com.scytl.sample.dao.impl;

import com.scytl.sample.dao.IUserDetailsDao;
import com.scytl.sample.model.UserAttempts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by cesardiaz on 07/08/15.
 *
 * @author cesardiaz
 */
@Repository
public class UserDetailsDao extends JdbcDaoSupport implements IUserDetailsDao {

    private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE users SET account_non_locked = ? WHERE username = ?";
    private static final String SQL_USERS_COUNT = "SELECT count(*) FROM users WHERE username = ?";

    private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM user_attempts WHERE username = ?";
    private static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO user_attempts (username, attempts, last_modified) VALUES(?, ?, ?)";
    private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE user_attempts SET attempts = attempts + 1, last_modified = ? WHERE username = ?";
    private static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE user_attempts SET attempts = 0, last_modified = null WHERE username = ?";

    private static final int MAX_ATTEMPTS = 3;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void updateFailAttempts(String username) {
        UserAttempts user = getUserAttempts(username);
        if (user == null) {
            if (isUserExists(username)) {
                // if no record, insert a new
                getJdbcTemplate().update(SQL_USER_ATTEMPTS_INSERT, username, 1, new Date());
            }
        } else {

            if (isUserExists(username)) {
                // update attempts count, +1
                getJdbcTemplate().update(SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS, new Date(), username);
            }

            if (user.getAttempts() + 1 >= MAX_ATTEMPTS) {
                // locked user
                getJdbcTemplate().update(SQL_USERS_UPDATE_LOCKED, false, username);
                // throw exception
                throw new LockedException("User Account is locked!");
            }
        }
    }

    @Override
    public UserAttempts getUserAttempts(String username) {
        try {
            return getJdbcTemplate().queryForObject(SQL_USER_ATTEMPTS_GET,
                    new Object[]{username}, new RowMapper<UserAttempts>() {
                        @Override
                        public UserAttempts mapRow(ResultSet rs, int rowNum) throws SQLException {
                            UserAttempts user = new UserAttempts();
                            user.setId(rs.getInt("id"));
                            user.setUsername(rs.getString("username"));
                            user.setAttempts(rs.getInt("attempts"));
                            user.setLastModified(rs.getDate("last_modified"));

                            return user;
                        }
                    });

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void resetFailAttempts(final String username) {
        getJdbcTemplate().update(
                SQL_USER_ATTEMPTS_RESET_ATTEMPTS, username);
    }

    private boolean isUserExists(String username) {
        boolean result = false;

        int count = getJdbcTemplate().queryForObject(
                SQL_USERS_COUNT, new Object[]{username}, Integer.class);
        if (count > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public void printAllUserAttempts(String username) {
        String sql = "SELECT * FROM USER_ATTEMPTS";

        List<UserAttempts> userAttemptses = getJdbcTemplate().query(sql,
                new BeanPropertyRowMapper<>(UserAttempts.class));

        if (!userAttemptses.isEmpty()) {
            System.out.println("ID\t|Username\t|Attempts\t|Last modified");
            for (UserAttempts userAttempts : userAttemptses) {
                System.out.println(String.format("%d\t|%s\t\t|%d\t\t|%s",
                        userAttempts.getId(), userAttempts.getUsername(),
                        userAttempts.getAttempts(), userAttempts.getLastModified()));
            }
        }
    }
}
