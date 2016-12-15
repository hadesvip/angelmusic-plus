create database am_secondary;
use am_secondary;
create table am_qrcode(
qrcode_id int primary key not null auto_increment comment '二维码编号',
qrcode_no varchar(64) not null comment '二维码编号'
)engine = innodb default charset =utf8 comment '二维码信息表';

create table am_qrcode_client(
id int primary key not null auto_increment comment '主键',
client_id varchar(64) not null comment '客户端Id',
qrcode_id varchar(64) not null comment '绑定的二维码编号，对应am_qrcode表中的主键',
unique key qrcode_client_id (client_id)
)engine = innodb default charset =utf8 comment '绑定二维码的客户端';