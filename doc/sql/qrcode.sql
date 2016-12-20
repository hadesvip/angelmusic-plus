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
  status int not null default 1 comment  '激活码状态:1未激活2已激活',
  unique key uq_activation_code_index (code)
)engine = innodb default charset =utf8 comment '激活码';

create table am_user (
  user_id int not null primary key comment '用户编号',
  user_name varchar(16) comment '用户名',
  user_phone int comment '用户手机号码',
  total_recharge int comment '累计消费金额',
  unique key uq_user_phone_index (user_phone)
)engine = innodb default charset =utf8 comment '用户信息表';


create table am_recharge_record(
  recharge_record_id int not null primary key comment '充值记录编号',
  user_id int not null comment '用户编号',
  recharge_money decimal(2) not null comment '本次充值金额',
  recharge_date datetime not null default now() comment '本次充值时间',
  activate_code varchar(64) not null comment '激活码',
  recharge_type  int not null comment  '充值方式',
  key recharge_record_index(user_id)
)engine =innodb default charset =utf8 comment '充值记录表';


create table am_course(
  course_id int primary key not null comment '课程编号',
  course_month int not null comment '课程月份',
  course_key  varchar(64) not null comment '课程秘钥',
  course_url varchar(64) not null  comment '课程地址',
  key course_month_index(course_month)
)engine = innodb default charset =utf8 comment '课程表';

create table am_course_subscribe(
  course_subscribe_id int not null primary key comment '订阅编号',
  user_id int not null comment '用户编号',
  course_id int not null comment '课程编号',
  unique key uq_cse_sbe_user_cse_id_index (user_id,course_id)
)engine =innodb default charset =utf8 comment '课程订阅表';


