drop database pharmacy;
create database pharmacy;

use pharmacy;
show tables;

#####################################################################################
CREATE TABLE provide_company (
    company_name VARCHAR(70) PRIMARY KEY NOT NULL,
    phone INT,
    address VARCHAR(150)
);
#####################################################################################
CREATE TABLE categores (
    cat_id INT PRIMARY KEY NOT NULL,
    categores_name VARCHAR(60),
    number_of_item INT 
);
#####################################################################################
CREATE TABLE item (
    item_name VARCHAR(80),
    par_code INT NOT NULL,
    quantity INT,
    discription VARCHAR(700),
    sale_price REAL,
    origen_price REAL,
    provide_company_name VARCHAR(70),
    cat_id INT,
    exp_date DATE,
    PRIMARY KEY (par_code , provide_company_name , cat_id),
    FOREIGN KEY (provide_company_name)
        REFERENCES provide_company (company_name)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (cat_id)
        REFERENCES categores (cat_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
#####################################################################################
CREATE TABLE employee (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    employee_name VARCHAR(60),
    birthday DATE,
    date_of_employment DATE,
    emp_password VARCHAR(30)
);
#####################################################################################
CREATE TABLE hourly_employee (
    id INT PRIMARY KEY,
    work_hours REAL,
    hour_price REAL,
    FOREIGN KEY (id)
        REFERENCES employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
#####################################################################################
CREATE TABLE contrect_employee (
    id INT PRIMARY KEY,
    amount_paid REAL,
    FOREIGN KEY (id)
        REFERENCES employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
#####################################################################################
CREATE TABLE orderes (
    order_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id INT,
    FOREIGN KEY (id)
        REFERENCES employee (id)
        ON DELETE NO ACTION ON UPDATE CASCADE
);
#####################################################################################
CREATE TABLE cashOrder (
    order_id INT PRIMARY KEY,
    order_date DATE,
    FOREIGN KEY (order_id)
        REFERENCES orderes (order_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
#####################################################################################
CREATE TABLE inshurance_company (
    inshurance_companyName VARCHAR(32) PRIMARY KEY,
    inshurance_companyDiscount INT,
    numberOfCustomer INT DEFAULT 0
);
#####################################################################################
CREATE TABLE inshurance (
    coustumerID INT PRIMARY KEY,
    coustumerName VARCHAR(32),
    inshurance_companyName VARCHAR(32) NOT NULL,
    FOREIGN KEY (inshurance_companyName)
        REFERENCES inshurance_company (inshurance_companyName)
        ON DELETE CASCADE ON UPDATE CASCADE
);
#####################################################################################
CREATE TABLE inshuranceOrder (
    coustumer_inshurance_id INT NOT NULL,
    order_date DATE,
    order_id INT,
    PRIMARY KEY (coustumer_inshurance_id , order_id),
    FOREIGN KEY (coustumer_inshurance_id)
        REFERENCES inshurance (coustumerID)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (order_id)
        REFERENCES orderes (order_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
#####################################################################################
CREATE TABLE bill (
    order_id INT PRIMARY KEY NOT NULL,
    order_date DATE,
    full_price REAL,
    profits REAL,
    bill_type VARCHAR(32),
    emp_id INT,
    FOREIGN KEY (emp_id)
        REFERENCES employee (id)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (order_id)
        REFERENCES orderes (order_id)
        ON DELETE NO ACTION ON UPDATE CASCADE
);
#####################################################################################
CREATE TABLE invoice (
    quantity INT,
    full_sale_price REAL,
    full_original_price REAL,
    par_code INT NOT NULL,
    order_id INT,
    PRIMARY KEY (par_code , order_id),
    FOREIGN KEY (order_id)
        REFERENCES orderes (order_id)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (par_code)
        REFERENCES item (par_code)
        ON DELETE NO ACTION ON UPDATE CASCADE
);
 #####################################################################################
 
insert into employee (employee_name,birthday,date_of_employment,emp_password) value ("Nathera Alwan",'1985-01-15','2010-05-16',"admin");


insert into provide_company values ("AL_Quds",022406550,"Nablus Street - Al Baloua - Al-Bireh - Ramallah / Palestine"),
("Birzeit",022987572,"Shatilla Street - Ramallah / Palestine"),
("Beit_Jalla",022890447,"Beit_Jalla - hadera street"),("Al_Sharqea",022812586,"Al_Bera - Ramallah / Palestine"),
("Jama",022746892,"Betonea - Ramallah / Palestine"),("Dar_Alshefa",022478916,"Betonea - Ramallah / Palestine");

insert into categores values (110,"sedatives",3),(120,"heart medications",3),(113,"antibacterial drugs",2),(096,"Mycobacterial drugs",2),(118,"monocytes drugs",1),
 (240,"deworming drugs",2),(201,"antiviral drugs",1),(047,"cancer drugs",2),(014,"breakfast medicines",0);

 insert into employee (employee_name,birthday,date_of_employment,emp_password) values ("Ahlam Eldeen Asfour",'1992-01-15','2018-05-16',"root"),
 ("Mera Ahmad Shekh",'1997-08-26','2020-08-14',"root1"),("Ahmad Said Ammar",'1995-04-21','2021-08-14',"root2"),
 ("Lama Naser Hammad",'1990-05-26','2019-01-30',"root3"),("Malak Raed Hannon",'1980-08-26','2012-12-14',"root4"),
 ("Manal Awwad Salem",'1992-07-04','2018-01-25',"root5"),("Raed Mohammad Ayman",'1995-08-26','2020-08-14',"root6"),
 ("Ibrahim Ahmad Asfour",'1989-12-26','2015-02-14',"root7"),("Ahmad Malik Alwan",'1991-08-26','2018-07-14',"root8"); 

 insert into hourly_employee values (3,8,15),(4,5,18),(6,8,17),(7,4,11),(10,10,22);
 insert into contrect_employee value (2,4800),(5,3700),(8,2850),(9,3210);
 
 insert into item values ("Acamol",114,15,"Antipyretic and analgesic for pain accompanied by insomnia",25,15,"AL_Quds",110, '2022-04-15'),
      ("acebutolol",115,27,"Beta blockers reduce the heart rate and reduce irregularity",34,22,"Birzeit",120, '2022-03-25'),
      ("Dabigatran", 116,40,"used to prevent strokes in those with atrial fibrillation not caused by heart valve issues",40,37,"AL_Quds",120,'2022-01-17'),
      ("Edoxaban",117,10,"preventing blood clots in people with nonvalvular atrial fibrillation who also have at least one risk factor",55,51,"Birzeit",120, '2022-6-10'),
      ("Bendamustine (Levact)",118,22," chemotherapy medication used in the treatment of chronic lymphocytic leukemia (CLL)",41,35,"AL_Quds",47, '2022-12-12'),
      ("Bevacizumab (Avastin)",119,5,"used for Colorectal cancer, Lung cancer, Breast cancer, Renal cancers, Brain cancers, Eye disease, Ovarian cancer, Cervical cancer and Administration",90,80,"Birzeit",47, '2023-07-11'),
      ("rifampin",120,50,"used for the treatment of tuberculosis in combination with other antibiotics, such as pyrazinamide, isoniazid, and ethambutol.",64,52,"AL_Quds",96, '2024-09-10'),
      ("Isoniazid (INH)",121,71,"Bactericidal • primary drug for LTBI and a primary drug for use in combinations",9,5,"Birzeit",96, '2024-02-01'),
      ("diazepam (Valium)",122,10,"used to treat anxiety, insomnia, panic attacks and symptoms of acute alcohol withdrawal",62,54,"Birzeit",110, '2023-07-02'),
      ("alprazolam (Xanax)",123,32,"used to treat panic disorders and generalized anxiety",14,10,"Birzeit",110, '2024-11-15'),
      ("amoxicillin",124,6,"used to treat the symptoms of many different types of bacterial infections",36,24,"Birzeit",113, '2022-04-17'),
      ("doxycycline",125,124,"indicated for treatment of infections caused by the following microorganisms",47,35,"Birzeit",113, '2024-12-04'),
      ("Infliximab",126,65,"used to treat the symptoms of Rheumatoid Arthritis, Psoriatic Arthritis, Plaque Psoriasis, Chron Disease, Ulcerative Colitis and Ankylosing Spondylitis",27,20,"Birzeit",118, '2022-08-21'),
      ("Penciclovir",127,24,"used for the treatment of various herpes simplex virus (HSV) infections",33,24,"AL_Quds",201, '2023-04-30'),
      ("Peramivir",128,27,"treatment of acute uncomplicated influenza in adults",63,45,"Birzeit",201, '2024-01-26'),
      ("Levamisole",129,54,"used to treat helminth infections and some skin infections",30,21,"AL_Quds",240, '2023-07-14'),
      ("Niclosamide",130,12,"used to treat parasitic infections in millions of people worldwide",60,54,"AL_Quds",240, '2022-02-23');
 insert into inshurance_company values("Birzeit Univesity",10,2),("Bank of Palestine",15,2),("islamic Bank",20,1);
 insert into inshurance values(5956,"Dr.Diaeddin Rimawi","Birzeit Univesity"),(1190985,"Ibrahim Asfour","Birzeit Univesity"),(1190999,"Eyab Ghifari","Bank of Palestine"),(1181192,"Mohamad Alwan","Bank of Palestine"),(1191375,"Islam Jihad","islamic Bank");
    
    
	  show tables; 
      select * from bill;
      select * from cashorder;
      select * from categores;
      select * from contrect_employee;
      select * from employee;
      select * from hourly_employee;
      select * from inshurance;
      select * from inshurance_company;
      select * from inshuranceorder;
      select * from invoice;
      select * from item;
      select * from orderes;
      select * from provide_company;
      





























/*
 insert into hourly_employee value(3,8,15);
SELECT 
    *
FROM
    employee e,
    hourly_employee h
WHERE
    e.id = h.id;
insert into contrect_employee value (2,4800);
SELECT 
    *
FROM
    employee e,
    contrect_employee c
WHERE
    e.id = c.id;
 insert into employee (employee_name,birthday,date_of_employment,emp_password) values ("Ahlam Eldeen Asfour",'1992-01-15','2018-05-16',"root"),
 ("Mera Ahmad Shekh",'1997-08-26','2020-08-14',"root1"); 
 insert into employee (employee_name,birthday,date_of_employment,emp_password) values ("eyab",'1985-01-15','2010-05-16',"eyab");
  insert into employee (employee_name,birthday,date_of_employment,emp_password) values ("1",'1985-01-15','2010-05-16',"1");
SELECT 
    *
FROM
    employee
WHERE
    employee_name = '1';
SELECT 
    *
FROM
    employee;
 insert into item values ("Acamol",114,15,"Antipyretic and analgesic for pain accompanied by insomnia",25,15,"AL_Quds",110, '2022-04-15'),
      ("acebutolol",115,27,"Beta blockers reduce the heart rate and reduce irregularity",34,22,"Birzeit",120, '2022-03-25');
 
SELECT 
    *
FROM
    item;
SELECT 
    *
FROM
    item
WHERE
    item_name = 'acebutolol'
        OR par_code = 114;
 insert into categores values (110,"sedatives",2),(120,"heart medications",2);
SELECT 
    *
FROM
    categores;
insert into provide_company values ("AL_Quds",022406550,"Nablus Street - Al Baloua - Al-Bireh - Ramallah / Palestine"),
("Birzeit",022987572,"Shatilla Street - Ramallah / Palestine");
SELECT 
    *
FROM
    provide_company;

insert into orderes(id) value(2);
insert into orderes(id) value(2);
SELECT 
    *
FROM
    orderes;
SELECT 
    MAX(order_id)
FROM
    orderes;
SELECT 
    COUNT(order_id)
FROM
    orderes;

SELECT 
    *
FROM
    bill
ORDER BY order_id;
SELECT 
    COUNT(*)
FROM
    bill;
SELECT 
    SUM(profits)
FROM
    bill;
SELECT 
    SUM(full_price)
FROM
    bill;
SELECT 
    SUM(profits)
FROM
    bill
WHERE
    (order_date BETWEEN '2020-10-10' AND '2022-10-12'); 
*/
