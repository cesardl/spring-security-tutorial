DROP TABLE USERS IF EXISTS;

CREATE TABLE USERS (
    USER_ID               INTEGER,
    USERNAME              VARCHAR(50),
    PASSWORD              VARCHAR(50),
    enabled               TINYINT DEFAULT 1 NOT NULL,
    accountNonExpired     TINYINT DEFAULT 1 NOT NULL,
    accountNonLocked      TINYINT DEFAULT 1 NOT NULL,
    credentialsNonExpired TINYINT DEFAULT 1 NOT NULL,
    PRIMARY KEY (USER_ID)
);

INSERT INTO USERS(USER_ID, USERNAME, PASSWORD) VALUES(1,'alpha','pass1');
INSERT INTO USERS(USER_ID, USERNAME, PASSWORD) VALUES(2,'beta','pass2');
INSERT INTO USERS(USER_ID, USERNAME, PASSWORD) VALUES(3,'gamma','pass3');

DROP TABLE USER_AUTHORIZATION IF EXISTS;

CREATE TABLE USER_AUTHORIZATION (
    USER_ROLE_ID INTEGER,
    USER_ID INTEGER,
    ROLE VARCHAR(50),
    PRIMARY KEY (USER_ROLE_ID)
);

INSERT INTO USER_AUTHORIZATION VALUES(1,1,'ROLE_ADMIN');
INSERT INTO USER_AUTHORIZATION VALUES(2,1,'ROLE_REGULAR_USER');
INSERT INTO USER_AUTHORIZATION VALUES(3,2,'ROLE_REGULAR_USER');
INSERT INTO USER_AUTHORIZATION VALUES(4,3,'ROLE_REGULAR_USER');

DROP TABLE USER_ATTEMPTS IF EXISTS;

CREATE TABLE USER_ATTEMPTS (
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100, INCREMENT BY 1),
    username     VARCHAR(45) NOT NULL,
    attempts     INTEGER NOT NULL,
    lastModified TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);