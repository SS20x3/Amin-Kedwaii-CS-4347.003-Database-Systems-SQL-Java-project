CREATE TABLE Store(
Store_id		int			NOT NULL,
Manager_id		int			NOT NULL ,
Num_employees	int			NOT NULL,
Address		varchar(255)		NOT NULL,
	CONSTRAINT STR_PK
		PRIMARY KEY (Store_id),
	CONSTRAINT STR_MNGR_KEY
		FOREIGN KEY (Manager_id) REFERENCES Employee(Employee_id)
			ON DELETE CASCADE ON UPDATE CASCADE
);