insert into am_topic values (1,'秋','秋',2,1);
insert into am_topic values (2,'宠物','宠物',1,2);
insert into am_topic values (3,'游戏','宠物',1,3);
insert into am_topic values (4,'冬','宠物',1,4);
insert into am_topic values (5,'春','春',1,5);


insert into am_content values (1,'音乐叮咚响','音乐叮咚响',1,'秋-音乐叮咚响','video','秋-游戏',2,1);
insert into am_content values (2,'节奏连连看','节奏连连看',1,'秋-节奏连连看','video','秋-游戏',1,2);
insert into am_content values (3,'乐器总动员','乐器总动员',1,'秋-乐器总动员','video','秋-游戏',1,3);
insert into am_content values (4,'音乐小剧场','音乐小剧场',1,'秋-音乐小剧场','video','秋-游戏',1,4);
insert into am_content values (5,'音乐叮咚响','音乐叮咚响',2,'宠物-音乐叮咚响','video','宠物-游戏',1,1);
insert into am_content values (6,'节奏连连看','节奏连连看',2,'宠物-节奏连连看','video','宠物-游戏',1,2);
insert into am_content values (7,'乐器总动员','乐器总动员',2,'宠物-乐器总动员','video','宠物-游戏',1,3);
insert into am_content values (8,'音乐小剧场','音乐小剧场',2,'宠物-音乐小剧场','video','宠物-游戏',1,4);


insert into am_content_mission values(1,'13584040966',1,2);


insert into am_gift_pack values(1,'三个月大礼包','三个月大礼包',30,3);
insert into am_gift_pack values(2,'六个月大礼包','六个月大礼包',59,6);
insert into am_gift_pack values(3,'十二个月大礼包','十二个月大礼包',100,12);

insert into am_order_record values('460da5d527f54458be8f3a5a025e43df','13584040966','2016-12-30 15:51:24',1,1,1);
insert into am_order_record values('dad4fc43276f4b938750a762eec22c42','13584040966','2017-12-30 15:51:24',1,2,1);
insert into am_order_record values('db961628a8b240dfa63f191b2ed09f40','13584040966','2017-05-30 15:51:24',1,3,1);