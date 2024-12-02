-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS lawmon;

-- 사용자 생성 및 권한 부여
CREATE USER IF NOT EXISTS 'qnlove'@'%' IDENTIFIED BY 'chanyoup1@';
GRANT ALL PRIVILEGES ON lawmon.* TO 'qnlove'@'%';
FLUSH PRIVILEGES;