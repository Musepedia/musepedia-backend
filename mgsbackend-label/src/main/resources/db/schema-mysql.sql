DROP TABLE IF EXISTS paragraph;

CREATE TABLE paragraph(
    paragraph_id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    text VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS question;

CREATE TABLE question(
    question_id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    text VARCHAR(255) NOT NULL,
    paragraph_id BIGINT(20) NOT NULL,
    INDEX (paragraph_id)
);

DROP TABLE IF EXISTS answer;

CREATE TABLE answer(
    answer_id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    text VARCHAR(255) NOT NULL,
    start_index INT(11) NOT NULL,
    question_id BIGINT(20) NOT NULL,
    INDEX (question_id)
);