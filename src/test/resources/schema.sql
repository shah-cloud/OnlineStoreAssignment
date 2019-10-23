DROP TABLE IF EXISTS ITEMS;
CREATE TABLE ITEMS(
itemNo varchar(20) Primary Key,
itemName varchar(200),
itemDetail varchar(200),
rating float,
price float,
vendorName varchar(20),
vendorContact varchar(10),
itemCategory varchar(20)
);
