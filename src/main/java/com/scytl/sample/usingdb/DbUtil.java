package com.scytl.sample.usingdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cesardiaz on 21/07/15.
 *
 * @author cesardiaz
 */
public class DbUtil {

    private static final Logger log = LoggerFactory.getLogger(DbUtil.class);
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initialize() {
        log.info("*************************************** DbUtil.initialize() ***************************************");

        DataSource ds = getDataSource();
        try (Connection connection = ds.getConnection();
                Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE USER_AUTHENTICATION IF EXISTS");
            statement.executeUpdate("CREATE TABLE USER_AUTHENTICATION (USER_ID INTEGER, USERNAME VARCHAR(50), PASSWORD VARCHAR(50), ENABLED BOOLEAN);");
            statement.executeUpdate("INSERT INTO USER_AUTHENTICATION VALUES(1,'alpha','pass1',TRUE);");
            statement.executeUpdate("INSERT INTO USER_AUTHENTICATION VALUES(2,'beta','pass2',TRUE);");
            statement.executeUpdate("INSERT INTO USER_AUTHENTICATION VALUES(3,'gamma','pass3',TRUE);");
            statement.executeUpdate("DROP TABLE USER_AUTHORIZATION IF EXISTS");
            statement.executeUpdate("CREATE TABLE USER_AUTHORIZATION (USER_ROLE_ID INTEGER,USER_ID INTEGER, ROLE VARCHAR(50));");
            statement.executeUpdate("INSERT INTO USER_AUTHORIZATION VALUES(1,1,'ROLE_ADMIN');");
            statement.executeUpdate("INSERT INTO USER_AUTHORIZATION VALUES(2,1,'ROLE_REGULAR_USER');");
            statement.executeUpdate("INSERT INTO USER_AUTHORIZATION VALUES(3,2,'ROLE_REGULAR_USER');");
            statement.executeUpdate("INSERT INTO USER_AUTHORIZATION VALUES(4,3,'ROLE_REGULAR_USER');");
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

}
