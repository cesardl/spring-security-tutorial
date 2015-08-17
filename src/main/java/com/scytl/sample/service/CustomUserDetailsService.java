package com.scytl.sample.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

/**
 * Reference org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl.
 *
 * Created by cesardiaz on 07/08/15.
 *
 * @author cesardiaz
 */
@Service("userDetailsService")
public class CustomUserDetailsService extends JdbcDaoImpl {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    @Value("select * from USERS where username=?")
    public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
        super.setUsersByUsernameQuery(usersByUsernameQueryString);
    }

    @Override
    @Value("select u1.USERNAME, u2.role from USERS u1, USER_AUTHORIZATION u2 where u1.user_id = u2.user_id and u1.USERNAME =?")
    public void setAuthoritiesByUsernameQuery(String queryString) {
        super.setAuthoritiesByUsernameQuery(queryString);
    }

    //override to get accountNonLocked
    @Override
    public List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[]{username},
                new RowMapper<UserDetails>() {
                    @Override
                    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String username = rs.getString("USERNAME");
                        String password = rs.getString("PASSWORD");
                        boolean enabled = rs.getBoolean("enabled");
                        boolean accountNonExpired = rs.getBoolean("accountNonExpired");
                        boolean credentialsNonExpired = rs.getBoolean("credentialsNonExpired");
                        boolean accountNonLocked = rs.getBoolean("accountNonLocked");

                        return new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
                                accountNonLocked, AuthorityUtils.NO_AUTHORITIES);
                    }

                });
    }

    //override to pass accountNonLocked
    @Override
    public UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
            List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        if (super.isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }

        return new User(returnUsername, userFromUserQuery.getPassword(),
                userFromUserQuery.isEnabled(),
                userFromUserQuery.isAccountNonExpired(),
                userFromUserQuery.isCredentialsNonExpired(),
                userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
    }

}
