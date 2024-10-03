INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Retailer','E-tailer', 90 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Retailer','Independent Grocer', 70 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Retailer','Home and Textile', 55 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Retailer','Pharmacy', 38 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Retailer','Mass Merchandiser', 52 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Health Practitioner','Medical Professional', 60 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Health Practitioner','Health Club', 65 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Distributor','Wholesaler of Finished Products', 33, 1);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Food Service','Full Service Restaurant', 91 , 4);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Food Service','Corporate Dining', 65 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Food Service','Coffee House', 54 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Food Service','Private Chef', 47 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Supplier','Equipment Supplier', 44 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Supplier','Ingredient Importer', 62 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Manufacturer','Food', 83 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Manufacturer','Pharmaceuticals', 68 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Manufacturer','Cosmetics', 34 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Manufacturer','Beverage', 86 , 4);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Manufacturer','Pet Products', 71 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Business Services','Consultant', 90 , 1);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Business Services','Government Agency', 57 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Business Services','Publisher', 61 , 3);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Business Services','Financial Institution', 58 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Investor','Private Equity Fund', 73 , 2);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Investor','Corporate Investor', 62 , 1);
INSERT INTO Business_Category (Business_Category,Business_Type , Total_Employees , Business_age) VALUES  ('Investor','Hedge Fund', 64 , 3);

INSERT INTO Property_Category (Insurance_Type,Property_Type,Building_Type,Building_Age) VALUES ('All Risks','Building','Owner', 1);
INSERT INTO Property_Category (Insurance_Type,Property_Type,Building_Type,Building_Age) VALUES ('Natural Disaster','Building','Owner', 3);
INSERT INTO Property_Category (Insurance_Type,Property_Type,Building_Type,Building_Age) VALUES ('Natural Disaster','Building','Rented', 2);
INSERT INTO Property_Category (Insurance_Type,Property_Type,Building_Type,Building_Age) VALUES ('Fire','Factory Equipment','Owner', 3);
INSERT INTO Property_Category (Insurance_Type,Property_Type,Building_Type,Building_Age) VALUES ('Natural Disaster','Factory Equipment','Rented',1);
INSERT INTO Property_Category (Insurance_Type,Property_Type,Building_Type,Building_Age) VALUES ('Fire','Property in Transit','Owner', 1);
INSERT INTO Property_Category (Insurance_Type,Property_Type,Building_Type,Building_Age) VALUES ('Fire','Property in Transit','Rented', 2);


insert into PROPERTY_MASTER("ID","MIN_AGE","MAX_AGE","INDEX") values(1,0,2,10);
insert into PROPERTY_MASTER("ID","MIN_AGE","MAX_AGE","INDEX") values(2,3,5,8);
insert into PROPERTY_MASTER("ID","MIN_AGE","MAX_AGE","INDEX") values(3,6,8,6);
insert into PROPERTY_MASTER("ID","MIN_AGE","MAX_AGE","INDEX") values(4,9,11,4);
insert into PROPERTY_MASTER("ID","MIN_AGE","MAX_AGE","INDEX") values(5,12,14,2);
insert into PROPERTY_MASTER("ID","MIN_AGE","MAX_AGE","INDEX") values(6,15,100,0);

insert into BUSINESS_MASTER("ID","MIN_PERCENT","MAX_PERCENT","INDEX") values(1,0,30,1);
insert into BUSINESS_MASTER("ID","MIN_PERCENT","MAX_PERCENT","INDEX") values(2,31,60,5);
insert into BUSINESS_MASTER("ID","MIN_PERCENT","MAX_PERCENT","INDEX") values(3,61,80,7);
insert into BUSINESS_MASTER("ID","MIN_PERCENT","MAX_PERCENT","INDEX") values(4,81,100,10);


INSERT INTO "PUBLIC"."BUSINESS" VALUES(11, 11, 'Business Services', 12, 'Consultant', 8, 123, 11, 12);
INSERT INTO "PUBLIC"."CONSUMER" VALUES(11, '1', '12-12-1992', 'df', 'dfdf', 'dfd', 12);
INSERT INTO "PUBLIC"."PROPERTY" VALUES(11, 12, '123', 'we', 'Rented', 11, 11, 12, 'Property in Transit', 5, 23, 4);








