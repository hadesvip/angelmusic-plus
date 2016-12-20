create database am_plus;
use am_plus;
create table am_qrcode(
qrcode_id int primary key not null auto_increment comment '二维码编号',
qrcode_no varchar(64) not null comment '二维码编号',
UNIQUE key uq_qrcode_qrcode_no_index(qrcode_no)
)engine = innodb default charset =utf8 comment '二维码信息表';

create table am_qrcode_client(
id int primary key not null auto_increment comment '主键',
client_id varchar(64) not null comment '客户端Id',
qrcode_id varchar(64) not null comment '绑定的二维码编号，对应am_qrcode表中的主键',
unique key uq_qrcode_client_id_index (client_id)
)engine = innodb default charset =utf8 comment '绑定二维码的客户端';

create table am_activation_code(
  id int primary key not null auto_increment comment '主键',
  code varchar(64) not null comment '激活码',
  effective_time int not null DEFAULT 12 COMMENT '有效时间:单位月',
  status int not null default 1 comment  '激活码状态:1未激活2已激活',
  unique key uq_activation_code_index (code)
)engine = innodb default charset =utf8 comment '激活码';

create table am_user (
  user_id int not null primary key AUTO_INCREMENT comment '用户编号',
  user_name varchar(16) comment '用户名',
  user_phone int not null comment '用户手机号码',
  total_recharge int comment '累计消费金额',
  create_date DATETIME comment '用户创建时间',
  unique key uq_user_phone_index (user_phone)
)engine = innodb default charset =utf8 comment '用户信息表';

create table am_recharge_record(
  linkId varchar(64) not null primary key comment '订单编号',
  user_id int not null comment '用户编号',
  charge_status int  not null comment '订单状态0成功,其他失败',
  recharge_date datetime not null default now() comment '本次充值时间',
  chargemsg varchar(64) not null comment '订单状态描述',
  price  int not null comment  '订单价格',
  paytype int not null comment '支付方式:1移动,2联通,3电信,4支付宝,5微信,6其他或未知',
  pay_time DATETIME not null comment '支付时间',
  key recharge_record_index(user_id)
)engine =innodb default charset =utf8 comment '充值记录表';

create table am_topic(
  topic_id int primary key not null AUTO_INCREMENT comment '主题编号',
  month int not null comment '主题月份',
  free int not null default 1 comment '是否免费:1收费,2免费',
  key topic_month_index(month)
)engine = innodb default charset =utf8 comment '主题表';

create table am_gift_pack(
  gift_pack_id int not null primary key AUTO_INCREMENT comment '礼包编号',
  gift_pack_name varchar(16) comment '大礼包名称',
  gift_pack_desc varchar(64) comment '大礼包描述',
  effective_time int not null DEFAULT 12 COMMENT '有效时间:单位月'
)engine =innodb default charset =utf8 comment '大礼包';

create table am_order_record(
  order_id varchar(64) not null PRIMARY KEY  comment '订单号',
  user_id int not null COMMENT  '用户编号',
  money decimal(2) not null comment '本次充值金额',
  order_date datetime not null default now() comment '本次充值时间',
  type  int not null comment  '支付类型:激活码1，大礼包2',
  product_id int not null comment '激活码或者大礼包编号',
  pay_result int not null comment '支付结果1成功2失败',
  key order_record_index(user_id)
)engine =innodb default charset =utf8 comment '订单记录';

create table am_content(
  content_id int not null primary key comment '内容编号',
  name varchar(12) not null comment '内容名称',
  content_desc varchar(64)  comment '内容描述',
  course_name varchar(12)  comment '课程名称',
  course_video varchar(64) comment '视频',
  game  varchar(64) comment '游戏'
)engine =innodb default charset =utf8 comment '主题内容';

create table am_topic_content(
  topic_id int not null comment '主题编号',
  content_id int not null comment '内容编号',
  unique key uq_topic_content_index (topic_id,content_id)
)engine =innodb default charset =utf8 comment '主题内容关系';
