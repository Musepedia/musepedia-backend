SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS tbl_museum;

CREATE TABLE tbl_museum
(
    museum_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    museum_name VARCHAR(255) NOT NULL,
    museum_description VARCHAR(512) NOT NULL ,
    museum_logo_url VARCHAR(1023) DEFAULT '',
    museum_is_service BOOL NOT NULL DEFAULT FALSE,
    museum_floor_plan_filepath VARCHAR(255) NOT NULL,
    longitude DECIMAL(11,8) DEFAULT NULL,
    latitude DECIMAL(10,8) DEFAULT NULL,
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`museum_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_exhibition_hall;

CREATE TABLE tbl_exhibition_hall
(
    exhibition_hall_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    exhibition_hall_name varchar(255) NOT NULL,
    exhibition_hall_description varchar(512) NOT NULL,
    museum_id BIGINT(20) NOT NULL,
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`exhibition_hall_id`) USING BTREE,
    FOREIGN KEY fk_museum_id (museum_id) REFERENCES tbl_museum (museum_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_exhibit;

CREATE TABLE tbl_exhibit
(
    exhibit_id       BIGINT(20)   NOT NULL AUTO_INCREMENT,
    exhibition_hall_id BIGINT(20) DEFAULT NULL,
    museum_id BIGINT(20) DEFAULT NULL,
    exhibit_figure_url VARCHAR(1023) DEFAULT '',
    exhibit_label    VARCHAR(255) NOT NULL,
    exhibit_description VARCHAR(511),
    exhibit_url VARCHAR(1023) DEFAULT '',
    exhibit_is_hot BOOL NOT NULL DEFAULT FALSE,
    exhibit_prev_id BIGINT(20) DEFAULT NULL,
    exhibit_next_id BIGINT(20) DEFAULT NULL,
    create_time      DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`exhibit_id`) USING BTREE,
    FOREIGN KEY fk_exhibition_hall_id (exhibition_hall_id) REFERENCES tbl_exhibition_hall (exhibition_hall_id),
    FOREIGN KEY fk_museum_id (museum_id) REFERENCES tbl_museum (museum_id),
    INDEX idx_exhibits_label (exhibit_label)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_recommend_question;

CREATE TABLE tbl_recommend_question
(
    question_id   BIGINT(20)   NOT NULL AUTO_INCREMENT,
    question_text VARCHAR(255) NOT NULL,
    answer_type INT NOT NULL,
    answer_text VARCHAR(255),
    exhibit_id BIGINT(20),
    exhibit_text_id BIGINT(20),
    museum_id BIGINT(20) NOT NULL,
    question_freq INT NOT NULL DEFAULT 1,
    create_time   DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`question_id`) USING BTREE,
    FOREIGN KEY fk_exhibit_id (exhibit_id) REFERENCES tbl_exhibit (exhibit_id),
    FOREIGN KEY fk_museum_id (museum_id) REFERENCES tbl_museum (museum_id)
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
    user_id      BIGINT(20)    NOT NULL AUTO_INCREMENT,
    nickname     VARCHAR(255)  NOT NULL DEFAULT '',
    avatar_url   VARCHAR(1023) NOT NULL DEFAULT '',
    phone_number VARCHAR(15)            DEFAULT NULL UNIQUE,
    gender       TINYINT(1) DEFAULT NULL,
    age          TINYINT(1) DEFAULT NULL,
    create_time  DATETIME               DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    FOREIGN KEY fk_user_id (user_id) REFERENCES tbl_user (user_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_user_preference;

CREATE TABLE tbl_user_preference
(
    user_preference_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    user_id BIGINT(20) NOT NULL,
    exhibition_hall_id BIGINT(20) NOT NULL,
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_preference_id`) USING BTREE,
    FOREIGN KEY fk_user_id (`user_id`) REFERENCES tbl_user (`user_id`),
    FOREIGN KEY fk_exhibition_hall_id (`exhibition_hall_id`) REFERENCES tbl_exhibition_hall (`exhibition_hall_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_user_question;

CREATE TABLE tbl_user_question
(
    user_question_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    user_id BIGINT(20) NOT NULL,
    question_id BIGINT(20) NOT NULL,
    feedback BOOL DEFAULT NULL,
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_question_id`) USING BTREE,
    FOREIGN KEY fk_user_id (`user_id`) REFERENCES tbl_user (`user_id`),
    FOREIGN KEY fk_question_id (`question_id`) REFERENCES tbl_recommend_question (`question_id`),
    UNIQUE KEY `uk_user_question` (user_id, question_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS tbl_gpt_completion;

CREATE TABLE tbl_gpt_completion
(
    gpt_completion_id BIGINT(20) AUTO_INCREMENT,
    user_question VARCHAR(255) NOT NULL,
    gpt_prompt VARCHAR(4095) NOT NULL,
    gpt_completion VARCHAR(4095) NOT NULL,
    prompt_tokens INT NOT NULL,
    completion_tokens INT NOT NULL,
    user_id BIGINT(20) NOT NULL,
    feedback BOOL DEFAULT NULL,
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`gpt_completion_id`) USING BTREE,
    FOREIGN KEY fk_user_id (`user_id`) REFERENCES tbl_user (`user_id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
