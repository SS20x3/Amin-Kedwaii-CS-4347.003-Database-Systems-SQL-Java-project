CREATE TABLE Product(
Product_id		int			NOT NULL,
Store_id		int			NOT NULL,
Supplier_name	varchar(255)		NOT NULL,
Product_type		varchar(255)		NOT NULL,
Purchace_price	int			NOT NULL,
Sales_price		int			NOT NULL,
Amount_inventory	int				      ,
	CONSTRAINT PRDCT_PK
		PRIMARY KEY (Product_id, Store_id),
	CONSTRAINT SOLD_AT
		FOREIGN KEY (Store_id) REFERENCES Store (Store_id)
			ON DELETE CASCADE	ON UPDATE CASCADE
);
