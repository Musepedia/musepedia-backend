### About database
```mysql
# import data(linux)
LOAD DATA INFILE '/var/lib/mysql-files/exhibits.csv'
INTO TABLE `text` CHARACTER SET utf8mb4
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(exhibits_label, exhibits_text);
```