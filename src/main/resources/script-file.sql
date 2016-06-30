DROP TABLE IF EXISTS users;

CREATE TABLE users (
  user_id                 INTEGER,
  username                VARCHAR(50),
  password                VARCHAR(50),
  enabled                 TINYINT DEFAULT 1 NOT NULL,
  account_non_expired     TINYINT DEFAULT 1 NOT NULL,
  credentials_non_expired TINYINT DEFAULT 1 NOT NULL,
  account_non_locked      TINYINT DEFAULT 1 NOT NULL,
  PRIMARY KEY (USER_ID)
);

INSERT INTO users (user_id, username, password) VALUES (1, 'alpha', 'pass1');
INSERT INTO users (user_id, username, password) VALUES (2, 'beta', 'pass2');
INSERT INTO users (user_id, username, password) VALUES (3, 'gamma', 'pass3');

DROP TABLE IF EXISTS user_authorization;

CREATE TABLE user_authorization (
  user_role_id INTEGER,
  user_id      INTEGER,
  role         VARCHAR(50),
  PRIMARY KEY (user_role_id)
);

INSERT INTO user_authorization VALUES (1, 1, 'ROLE_ADMIN');
INSERT INTO user_authorization VALUES (2, 1, 'ROLE_REGULAR_USER');
INSERT INTO user_authorization VALUES (3, 2, 'ROLE_REGULAR_USER');
INSERT INTO user_authorization VALUES (4, 3, 'ROLE_REGULAR_USER');

DROP TABLE IF EXISTS user_attempts;

CREATE TABLE user_attempts (
  id            INTEGER AUTO_INCREMENT,
  username      VARCHAR(45) NOT NULL,
  attempts      TINYINT     NOT NULL,
  last_modified TIMESTAMP,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS persistent_login;

CREATE TABLE persistent_login (
  username  VARCHAR(64) NOT NULL,
  series    VARCHAR(64) NOT NULL,
  token     VARCHAR(64) NOT NULL,
  last_used TIMESTAMP   NOT NULL,
  PRIMARY KEY (series)
);
