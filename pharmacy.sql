create database pharmacy;
use pharmacy;

SET time_zone = "+00:00";

CREATE TABLE company (
ID            int primary key,
Name          varchar(150),
Address       varchar(150)
);

create table company_phones(
CompID        int,
Phone         varchar(20),
PRIMARY KEY (CompID,Phone),
FOREIGN KEY (CompID) REFERENCES company(ID)
);

create table drug(
ID            int primary key,
Name          varchar(100),
Barcode       varchar(100),
Price         double,
Comp_ID       int,
Typee         varchar(100),
Qty           double,
Tab           int default null,
foreign key (Comp_ID) references company(ID)
);

create table sales(
ID            int primary key,
Datee         varchar(20),
Subtotal      double,
Pay           double,
Balance       double
);

create table sales_product(
ID            int primary key,
Sales_ID      int,
Barcode       varchar(100),
Total         double,
Price         double,
QTY           int,
Typee         varchar(100),
User          int,
foreign key (Sales_ID) references sales(ID)
);

create table retrieve (
ID                  int primary key,    /*checked*/
Sales_ID            int,				/*checked*/
Drug_Name           varchar(100),
Typee               varchar(20),        /*checked*/
QTY                 double,             /*checked*/
price               double,             /*checked*/
Total               double,				/*checked*/
Pay                 double,				/*checked*/
Balance             double,				/*checked*/
Barcode             varchar(100),       /*checked*/
Username            varchar(200),       /*checked*/
Datee               varchar(15),        /*checked*/
foreign key (Sales_ID) references sales(ID) 
);

alter table retrieve
drop column Pay;


create table employee(
ID            int primary key,
Username      varchar(200),
Pass          varchar(150),
Address       varchar(200),
Salary        double,
Stat          varchar(20),
pro           varchar(50)
);

create table employee_phones(
EmpNO        int,
Phones       varchar(15),
PRIMARY KEY (EmpNO,Phones),
FOREIGN KEY (EmpNO) REFERENCES employee(ID)
);

create view phone_data
as
select EmpNO as 'ID',
	   Username as 'Name',
       Phones as 'phones'
       from employee_phones,employee
       where ID = EmpNO;

create view employee_data
as
select ID as 'ID',
       Username as 'Name',
       Address as 'Address',
       Salary as 'Salary',
       Phones as 'Phones'
       from employee , employee_phones
       where EmpNO = ID;
       
alter view employee_data
as
select ID as 'ID',
       Username as 'Name',
       Address as 'Address',
       Salary as 'Salary',
       Phones as 'Phones',
       Stat as 'Status'
       from employee , employee_phones
       where EmpNO = ID;

create view drug_data
as
select drug.ID as 'ID',
       drug.Barcode as 'BarCode',
	   drug.Name as 'Name',
       company.ID as 'Comp_ID',
       company.Name as 'Comp_Name',
       drug.Typee as 'Type',
       drug.Price as 'Price',
       drug.Qty as 'Qty'
       from drug,company
       where drug.Comp_ID = company.ID;
       
alter view drug_data as
select drug.ID as 'ID',
       drug.Barcode as 'BarCode',
	   drug.Name as 'Name',
       company.ID as 'Comp_ID',
       company.Name as 'Comp_Name',
       drug.Typee as 'Type',
       drug.Price as 'Price',
       drug.Qty as 'Qty',
       drug.Tab as 'Tab'
       from drug,company
       where drug.Comp_ID = company.ID;
              
create view phones_data
as
select company.ID as 'ID',
       company.Name as 'Name',
       company_phones.Phone as 'Phones'
       from company,company_phones
       where company_phones.CompID = company.ID;
       
create view history_sales
as
select sales_product.Sales_ID as 'Bill_NO',
       sales_product.User as 'User_ID',
       employee.Username as 'Employee_Name',
       sales_product.Barcode as 'Barcode',
       drug.Name as 'Drug_Name',
       sales_product.Typee as 'Type',
       sales_product.QTY as 'QTY',
       drug.Price as 'Price',
       sales_product.Total as 'Total',
       sales.Datee as 'Date'
       from employee,sales,sales_product,drug
       where employee.ID = sales_product.User
             and sales_product.Sales_ID = sales.ID
             and drug.Barcode = sales_product.Barcode
		order by sales_product.Sales_ID;
        
ALTER DATABASE pharmacy
CHARACTER SET utf8
COLLATE utf8_general_ci;