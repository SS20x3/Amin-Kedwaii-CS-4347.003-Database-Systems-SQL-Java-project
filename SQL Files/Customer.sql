CREATE TABLE Customer(
Customer_id		int			NOT NULL,
Card_no		int				      ,
CVV			int				      ,
First_name 		varchar(255)		NOT NULL,
Last_name 		varchar(255)		NOT NULL,
MiddleIinit 		varchar(1)			      ,
Address 		varchar(255)		NOT NULL,
	CONSTRAINT CSTMR_PK
		PRIMARY KEY (Customer_id),
);
