create database triple;
create user 'triple'@'%' identified by 'triple';
GRANT ALL PRIVILEGES ON triple.* TO 'triple'@'%';

