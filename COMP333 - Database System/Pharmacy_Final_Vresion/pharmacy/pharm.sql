create database pharmacy;
use pharmacy;

show tables;

create table provide_company( company_name varchar(70) primary key not null, phone int 
, address varchar(150));
insert into provide_company values ("AL_Quds",022406550,"Nablus Street - Al Baloua - Al-Bireh - Ramallah / Palestine"),
("Birzeit",022987572,"Shatilla Street - Ramallah / Palestine");

create table categores( cat_id int primary key not null, categores_name varchar(60),
 number_of_item int);
 insert into categores values (110,"sedatives",2),(120,"heart medications",2);
 
create table item(item_name varchar(80),par_code int not null,quantity int,
discription varchar(700), sale_price real,origen_price real,provide_company_name varchar(70)
, cat_id int,primary key(par_code,provide_company_name,cat_id),
foreign key(provide_company_name) references provide_company(company_name) on delete cascade
 on update cascade,
 foreign key(cat_id) references categores(cat_id) on delete cascade
 on update cascade);
 insert into item values ("Acamol",114,15,"Antipyretic and analgesic for pain accompanied by insomnia",25,15,"AL_Quds",110),
 ("acebutolol",115,27,"Beta blockers reduce the heart rate and reduce irregularity",34,22,"Birzeit",120);
 

 create table employee(id int primary key not null auto_increment, employee_name varchar(60),
 birthday date, date_of_employment date, emp_password varchar(30));
 insert into employee (employee_name,birthday,date_of_employment,emp_password) values ("Ahlam Eldeen Asfour",'1992-01-15','2018-05-16',"root"),
 ("Mera Ahmad Shekh",'1997-08-26','2020-08-14',"root1"); 
 
 select * from employee;

  
 create table hourly_employee(id int primary key, work_hours real, hour_price real,
 foreign key(id) references employee(id) on delete cascade
 on update cascade);
 
 create table contrect_employee(id int primary key,amount_paid real,
 foreign key(id) references employee(id) on delete cascade
 on update cascade);


 create table orderes(order_id int primary key not null auto_increment,
 id int ,foreign key(id) references employee(id) on delete no action
 on update cascade);
 
 create table cash(order_id int primary key,order_date date,
 foreign key(order_id) references orderes(order_id) on delete cascade
 on update cascade);

 create table inshurance_company( inshurance_company_name varchar(60) primary key not null,
 discount_in_percent real);

 create table inshurance(coustumer_inshurance_id int not null,order_id int,
 primary key(coustumer_inshurance_id,order_id),
 coustumer_name varchar(60), inshurance_company_name varchar(60),
  foreign key(inshurance_company_name) references inshurance_company(inshurance_company_name) 
  on delete no action on update cascade,
  foreign key(order_id) references orderes(order_id) on delete cascade
 on update cascade);
 
 
 create table invoice( quantity int, full_sale_price real,full_original_price real,
 par_code int not null,order_id int, primary key(par_code,order_id),
 foreign key(order_id) references orderes(order_id) on delete no action
 on update cascade,
 foreign key(par_code) references item(par_code) on delete no action
 on update cascade);
 