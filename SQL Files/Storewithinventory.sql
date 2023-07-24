CREATE VIEW Storewithinventory
AS (SELECT Amount_inventory 
	FROM Store Join Product ON (Store.Store_id  = Product.Store_id)
) 
