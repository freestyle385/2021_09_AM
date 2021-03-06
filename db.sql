#DB 삭제/생성/사용
DROP DATABASE IF EXISTS am;
CREATE DATABASE am;
USE am;

# 게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    title CHAR(255) NOT NULL,
    `body` LONGTEXT NOT NULL,
    memberId INT(10) NOT NULL
);

#회원 테이블 생성
CREATE TABLE `member` (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  loginId CHAR(100) NOT NULL UNIQUE,
  loginPw CHAR(100) NOT NULL,
  `name` CHAR(100) NOT NULL
);

# 게시물 데이터 추가 
INSERT INTO article 
SET regDate = NOW(),
title = '제목1',
`body` = '내용1',
memberId = 1;

INSERT INTO article 
SET regDate = NOW(),
title = '제목2',
`body` = '내용2',
memberId = 1;

INSERT INTO article 
SET regDate = NOW(),
title = '제목3',
`body` = '내용3',
memberId = 2;

INSERT INTO article 
SET regDate = NOW(),
title = '제목4',
`body` = '내용4',
memberId = 3;

INSERT INTO article 
SET regDate = NOW(),
title = CONCAT('제목__',RAND()),
`body` = CONCAT('내용__',RAND()),
memberId = 1;

# 회원 데이터 추가 
INSERT INTO `member` 
SET regDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '홍길동';

INSERT INTO `member` 
SET regDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '임꺽정';

INSERT INTO `member` 
SET regDate = NOW(),
loginId = 'test3',
loginPw = 'test3',
`name` = '장길산';

SELECT SHA2('test1',256)

#글 갯수 2배
INSERT INTO article (regDate,title,`body`)
SELECT NOW(), CONCAT('제목_',RAND()), CONCAT('내용_',RAND())
FROM article;

SELECT * FROM article;

SELECT * FROM `member`;

SELECT * FROM article ORDER BY id DESC LIMIT 0, 20

SELECT COUNT(*) FROM article;

DELETE FROM article
WHERE id > 200;

DELETE FROM article
WHERE id = 434 OR id = 433;
#위와 동일
DELETE FROM article
WHERE id IN (434, 433);
