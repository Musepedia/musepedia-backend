-- 仅测试
BEGIN;
INSERT INTO recommend_question (question_text) VALUES ('银杏的寿命有多长');
INSERT INTO recommend_question (question_text) VALUES ('银杏是沙拉吗');
INSERT INTO recommend_question (question_text) VALUES ('原神好玩吗');
INSERT INTO recommend_question (question_text) VALUES ('咖啡是茶吗');
INSERT INTO recommend_question (question_text) VALUES ('健身房是汽车吗');
INSERT INTO recommend_question (question_text) VALUES ('蘑菇是炸弹吗');
COMMIT;

# 导入数据
# LOAD DATA INFILE 'exhibits.csv'
# INTO TABLE `text`
# FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY '"'
# LINES TERMINATED BY '\r\n'
# IGNORE 1 LINES
# (exhibits_label, exhibits_text);