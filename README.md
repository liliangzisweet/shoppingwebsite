# shoppingwebsite
The project uses spring mvc to build a simple shopping website.
The following sqls are used to build database associated with the project.

user table:
create table person(
id int auto_increment primary key, 
userName varchar(100), 
password varchar(100) comment "use md5 to encrypt",
nickName varchar(50),
userType tinyint(3) comment "usertype，buyer: 0，seller: 1") 
ENGINE=InnoDB  DEFAULT CHARSET=utf8;

product table:
create table content(
id int auto_increment primary key,  
price bigint  comment "current price",
title varchar(100),
icon mediumblob comment "image",
abstract varchar(200) comment "summary",
text blob comment "description"  )
ENGINE=InnoDB  DEFAULT CHARSET=utf8;

transaction table:
create table trx(
id int auto_increment primary key,  
contentId int  comment "contentID",
personId int comment "personID",
price int comment "purchase price",
time bigint comment "purchase time")
ENGINE=InnoDB  DEFAULT CHARSET=utf8;

initial data：
insert into `person` (`id`, `userName`, `password`, `nickName`, `userType`) values('0','buyer','37254660e226ea65ce6f1efd54233424','buyer','0');
insert into `person` (`id`, `userName`, `password`, `nickName`, `userType`) values('1','seller','981c57a5cfb0f868e064904b8745766f','seller','1');

buyer:reyub(plaintext for buyer's password)
seller:relles(plaintext for seller's password)
