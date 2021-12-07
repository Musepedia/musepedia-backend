DROP TABLE IF EXISTS recommend_question;

CREATE TABLE recommend_question(
    question_id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    question_text VARCHAR(255) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS text;

CREATE TABLE text(
    text_id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    exhibits_text VARCHAR(4095) NOT NULL,
    exhibits_label VARCHAR(127) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_exhibits_label (exhibits_label)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;