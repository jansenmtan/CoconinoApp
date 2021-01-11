/*
*********************************************************************
http://www.mysqltutorial.org
*********************************************************************
Name: MySQL Sample Database classicmodels
Link: http://www.mysqltutorial.org/mysql-sample-database.aspx
Version 3.1
+ changed data type from DOUBLE to DECIMAL for amount columns
Version 3.0
+ changed DATETIME to DATE for some colunmns
Version 2.0
+ changed table type from MyISAM to InnoDB
+ added foreign keys for all tables 
*********************************************************************
*/

/* 
	Modified by Jansen Tan
	Rewritten for SQLite3
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ database_coconino /*!40100 DEFAULT CHARACTER SET latin1 */;



/* 
	Referred to this guide when naming objects:
	https://launchbylunch.com/posts/2014/Feb/16/sql-naming-conventions/
*/

/* 
	Table structure for table employee 
*/

DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	last_name	varchar(50) NOT NULL,
	first_name	varchar(50) NOT NULL,
	email		varchar(100) NOT NULL,
	phone		varchar(50) NOT NULL,
	job_title	varchar(100) NOT NULL
) 


;

/* Data for the table employee */

INSERT INTO employee (
	id,
	last_name,
	first_name,
	email,
	phone,
	job_title
) VALUES 

(
	1,
	'Strickland',
	'Charles',
	'charlesstrickland@cocino.com',
	'+1-219-777-2122',
	'President, CEO, CFO, COO, manager'
),

(
	115,
	'Tan',
	'Jansen',
	'jansenmtan@cocino.com',
	'+1-219-677-6748',
	'Software Engineer'
);

/* 
	Table structure for table brand 
*/

DROP TABLE IF EXISTS brand;

CREATE TABLE brand (
	id		INTEGER PRIMARY KEY AUTOINCREMENT,
	name	varchar(50) NOT NULL
) 


;

/* Data for the table brand */

INSERT INTO brand (
	id,
	name
) VALUES 

(
	1,
	'Beauty'
),

(
	2,
	'Electronics'
),

(
	3,
	'Furniture'
),

(
	4,
	'Home'
),

(
	5,
	'Clothing'
);

/* 
	Table structure for table product 
*/

DROP TABLE IF EXISTS product;

CREATE TABLE product (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	name		varchar(50) NOT NULL,
	description	text NOT NULL,
	list_price	decimal(10, 2) NOT NULL,
	stock_count INTEGER NOT NULL,
	brand_id	INTEGER NOT NULL,
	FOREIGN KEY (brand_id) REFERENCES brand (id)
) 


;

/* Data for the table product */

INSERT INTO product (
	id,
	name,
	description,
	list_price,
	stock_count,
	brand_id
) VALUES 

(
	152,
	'Plastic brush',
	'Reliable plastic brush with which you can comfortably brush your hair with.',
	'2.15',
	700,
	1
),

(
	163,
	'NIKON S980 photo camera',
	'The NIKON S980 is a professional grade digital photo camera preferred by the best photographers.',
	'249.99',
	100,
	2
),

(
	8453,
	'Lawn chair',
	'Classic wooden lawn chair. Sturdy and well built for years of relaxation.',
	'18.25',
	400,
	3
),

(
	493,
	'Samsung Microwave',
	'The 2015 Samsung Microwave offers the best and newest features from Samsung, including smartphone integration and Internet of Things capabilities.',
	'999.99',
	800,
	4
),

(
	4891,
	'Straw sunhat',
	'Fashionable straw sunhat for the summer.',
	'15.00',
	500,
	5
),

(
	944,
	'Nutella Face Moisturizer 8 oz',
	'The newest Nutella face moisturizer has the same great formula now in a 8 oz container.',
	'7.50',
	900,
	1
);

/* 
	Table structure for table account 
*/

DROP TABLE IF EXISTS account;

CREATE TABLE account (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	username	varchar(50) NOT NULL,
	password	varchar(50) NOT NULL,
	email		varchar(50) NOT NULL
) 


;

/* Data for the table account */

INSERT INTO account (
	id,
	username,
	password,
	email
) VALUES 

(
	9,
	'JS_1989',
	'Ja23k3!.',
	'jaysam@hotbook.org'
),

(
	34,
	'highclimber15',
	'HighCl1mber9',
	'hhughes15@gmail.com'
);

/* 
	Table structure for table customer 
*/

DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
	id				INTEGER PRIMARY KEY AUTOINCREMENT,
	account_id		INTEGER DEFAULT NULL,
	name			varchar(50) NOT NULL,
	last_name		varchar(50) NOT NULL,
	first_name		varchar(50) NOT NULL,
	phone			varchar(50) NOT NULL,
	address_line_1	varchar(50) NOT NULL,
	address_line_2	varchar(50) DEFAULT NULL,
	city			varchar(50) NOT NULL,
	state			varchar(50) DEFAULT NULL,
	postal_code		varchar(15) DEFAULT NULL,
	country			varchar(50) NOT NULL,
	credit_limit	decimal(10,2) DEFAULT NULL,
	/*PRIMARY KEY (id)*/
	FOREIGN KEY (account_id) REFERENCES account (id)
) 


;

/* Data for the table customer */

INSERT INTO customer (
	id,
	account_id,
	name,
	last_name,
	first_name,
	phone,
	address_line_1,
	address_line_2,
	city,
	state,
	postal_code,
	country,
	credit_limit
) VALUES 

(
	15,
	9,
	'Johnson Samuel',
	'Samuel',
	'Johnson',
	'+9-123-654-3210',
	'123 E Montgomery Ln',
	NULL,
	'Canterbury',
	'MT',
	'99999',
	'USSA',
	'21000.00'
),

(
	27,
	NULL,
	'Tanya Stevens',
	'Stevens',
	'Tanya',
	'+1-636-747-2687',
	'3280 S Michigan Rd',
	'Bldg 2',
	'Hammond',
	'IN',
	'47312',
	'US',
	NULL
),

(
	632,
	NULL,
	'Mark Halle',
	'Halle',
	'Mark',
	'+1-653-253-6845',
	'1555 W 155th Ln',
	NULL,
	'Hammond',
	'IN',
	'47312',
	'US',
	NULL
),

(
	93,
	34,
	'Hyden Hughes',
	'Hughes',
	'Hyden',
	'+3-344-135-5245',
	'665 N Otthat Cir',
	'APT 7',
	'Medellin',
	'AQ',
	'43210',
	'CO',
	'42000.00'
);

/* 
	Table structure for table receipt 
*/

DROP TABLE IF EXISTS receipt;

CREATE TABLE receipt (
	id						INTEGER PRIMARY KEY AUTOINCREMENT,
	customer_id				INTEGER NOT NULL,
	payment_id				INTEGER DEFAULT NULL,
	date_created			date DEFAULT NULL,
	quoted_shipping_date	date DEFAULT NULL,
	actual_shipping_date	date DEFAULT NULL,
	status					varchar(50) NOT NULL,
	comments				text,
	FOREIGN KEY (customer_id) REFERENCES customer (id),
	FOREIGN KEY (payment_id) REFERENCES payment (id)
) 


;

/* Data for the table receipt */

INSERT INTO receipt (
	id,
	customer_id,
	payment_id,
	date_created,
	quoted_shipping_date,
	actual_shipping_date,
	status,
	comments
) VALUES 

(
	5693,
	15,
	249,
	'2015-09-19',
	'2015-09-28',
	'2015-09-25',
	'Shipped',
	NULL
),

(
	1894,
	27,
	949,
	'2020-09-01',
	'2020-09-09',
	'2020-09-09',
	'Shipped',
	NULL
),

(
	9932,
	93,
	75,
	'2020-10-01',
	'2020-10-08',
	NULL,
	'Processing',
	'Customer wants poem to be shipped in the same box as order.'
),

(
	8482,
	27,
	169,
	'2019-04-10',
	'2019-04-21',
	'2019-04-25',
	'Shipped',
	'Hurricane Sandy delayed shipping time by at least 4 days.'
);

/* 
	Table structure for table receipt_detail 
*/

DROP TABLE IF EXISTS receipt_detail;

CREATE TABLE receipt_detail (
	receipt_id	INTEGER NOT NULL,
	product_id	INTEGER NOT NULL,
	/*The amount of the product from product_id ordered*/
	amount		INTEGER NOT NULL,
	FOREIGN KEY (receipt_id) REFERENCES receipt (id),
	FOREIGN KEY (product_id) REFERENCES product (id),
	CONSTRAINT receipt_detail_pkey PRIMARY KEY (receipt_id, product_id)
) 


;

/* Data for the table receipt_detail */
INSERT INTO receipt_detail (
	receipt_id,
	product_id,
	amount
) VALUES

(
	5693,
	4891,
	1
),

(
	5693,
	8453,
	1
),

(
	1894,
	4891,
	2
),

(
	1894,
	152,
	1
),

(
	1894,
	944,
	1
),

(
	9932,
	163,
	1
),

(
	9932,
	493,
	1
),

(
	9932,
	944,
	2
),

(
	8482,
	493,
	1
);

/* 
	Table structure for table payment 
*/

DROP TABLE IF EXISTS payment;

CREATE TABLE payment (
	id					INTEGER PRIMARY KEY AUTOINCREMENT,
	payment_method_id	INTEGER NOT NULL,
	total				decimal(10, 2) NOT NULL,
	FOREIGN KEY (payment_method_id) REFERENCES payment_method (id)
) 


;

/* Data for the table payment */
INSERT INTO payment (
	id,
	payment_method_id,
	total
) VALUES

/*I am aware that state taxes need to be accounted for, but they are not for these values.*/
(
	249,
	1,
	'23.25'
),

(
	949,
	4,
	'39.65'
),

(
	75,
	2,
	'1257.48'
),

(
	169,
	3,
	'999.99'
);

/* 
	Table structure for table payment_method 
*/

DROP TABLE IF EXISTS payment_method;

CREATE TABLE payment_method (
	id		INTEGER PRIMARY KEY AUTOINCREMENT,
	name	varchar(50) NOT NULL
) 


;

/* Data for the table payment_method */
INSERT INTO payment_method (
	id,
	name
) VALUES

(
	1,
	'credit card'
),

(
	2,
	'check'
),

(
	3,
	'debit'
),

(
	4,
	'paypal'
);

/* 
	Table structure for table discount 
*/

DROP TABLE IF EXISTS discount;

CREATE TABLE discount (
	id					INTEGER PRIMARY KEY AUTOINCREMENT,
	price_multiplier	decimal(10,2) NOT NULL,
	name				varchar(50) NOT NULL,
	description			text DEFAULT NULL
) 


;

/* Data for the table discount */
INSERT INTO discount (
	id,
	price_multiplier,
	name,
	description
) VALUES

(
	1,
	'0.90',
	'Employee discount',
	'Ten percent discount for employees'
);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
