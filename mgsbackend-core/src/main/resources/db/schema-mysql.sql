SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS tbl_recommend_question;

CREATE TABLE tbl_recommend_question
(
    question_id   BIGINT(20)   NOT NULL AUTO_INCREMENT,
    question_text VARCHAR(255) NOT NULL,
    create_time   DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_exhibit;

CREATE TABLE tbl_exhibit
(
    exhibit_id       BIGINT(20)   NOT NULL AUTO_INCREMENT,
    exhibit_category VARCHAR(255) NOT NULL,
    exhibit_label    VARCHAR(255) NOT NULL,
    create_time      DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`exhibit_id`) USING BTREE,
    INDEX idx_exhibits_label (exhibit_label)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_exhibit_text;

CREATE TABLE tbl_exhibit_text
(
    exhibit_text_id BIGINT(20)    NOT NULL AUTO_INCREMENT,
    exhibit_text    VARCHAR(4095) NOT NULL,
    exhibit_id      BIGINT(20)    NOT NULL,
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`exhibit_text_id`) USING BTREE,
    FOREIGN KEY fk_exhibit_id (exhibit_id) REFERENCES tbl_exhibit (exhibit_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_exhibit_alias;

CREATE TABLE tbl_exhibit_alias
(
    exhibit_alias_id BIGINT(20)   NOT NULL AUTO_INCREMENT,
    exhibit_id       BIGINT(20)   NOT NULL,
    exhibit_alias    VARCHAR(255) NOT NULL,
    create_time      DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`exhibit_alias_id`) USING BTREE,
    FOREIGN KEY fk_exhibit_id (exhibit_id) REFERENCES tbl_exhibit (exhibit_id),
    INDEX idx_exhibits_alias (exhibit_alias)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_user;

CREATE TABLE tbl_user
(
    user_id      BIGINT(20)   NOT NULL AUTO_INCREMENT,
    nickname     VARCHAR(255) NOT NULL DEFAULT '',
    avatar_url   VARCHAR(1023)         DEFAULT NULL UNIQUE,
    phone_number VARCHAR(15)  NOT NULL DEFAULT '',
    create_time  DATETIME              DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_user_wx_openid;

CREATE TABLE tbl_user_wx_openid
(
    user_wx_openid_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    user_id           BIGINT(20) NOT NULL UNIQUE,
    wx_openid         CHAR(28)   NOT NULL UNIQUE,
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_wx_openid_id`) USING BTREE,
    FOREIGN KEY fk_exhibit_id (user_id) REFERENCES tbl_user (user_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_museum;


SET FOREIGN_KEY_CHECKS = 1;