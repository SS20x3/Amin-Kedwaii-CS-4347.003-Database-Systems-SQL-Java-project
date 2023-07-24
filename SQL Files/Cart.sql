CREATE TABLE Cart(
Customer_id		int			NOT NULL,
Product_id		int			NOT NULL,
Store_id		int			NOT NULL,
    CONSTRAINT CRT_OWNER
		FOREIGN KEY (Customer_id) REFERENCES Customer (Customer_id)
			ON DELETE CASCADE	ON UPDATE CASCADE,
		FOREIGN KEY (Product_id, Store_id) REFERENCES Product (Product_id, Store_id)
			ON DELETE CASCADE	ON UPDATE CASCADE,
);
