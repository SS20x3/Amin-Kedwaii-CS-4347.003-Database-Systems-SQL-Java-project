CREATE TABLE Employee (
Employee_id		int			NOT NULL,
Manager_id		int				      ,
Pay_rate 		int			NOT NULL,
Store_id 		int			NOT NULL,
First_name 		varchar(255)		NOT NULL,
Last_name 		varchar(255)		NOT NULL,
MiddleIinit 		varchar(1)			      ,
Address 		varchar(255)		NOT NULL,
	CONSTRAINT EMP_PK
		PRIMARY KEY (Employee_id),
	CONSTRAINT EMP_MNGR_KEY
		FOREIGN KEY (Manager_id) REFERENCES Employee(Employee_id)
			ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT EMP_WRKS_AT
		FOREIGN KEY (Store_id) REFERENCES Store (Store_id)
			ON UPDATE CASCADE
);

