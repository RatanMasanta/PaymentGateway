create database test_db;
use test_db;
create table payment (id integer not null auto_increment, amount double precision, email varchar(255), mihpay_id varchar(255), mode varchar(255), name varchar(255), payment_date date, payment_status varchar(255), phone varchar(255), product_info varchar(255), txn_id varchar(255), primary key (id)) engine=MyISAM;