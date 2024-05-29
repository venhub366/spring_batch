CREATE TABLE IF NOT EXISTS PRODUCTS
(
    product_id varchar(20) primary key,
    title varchar(200),
    description varchar(200),
    price varchar(20),
    discount varchar(2),
    discount_price varchar(10)
);

CREATE TABLE IF NOT EXISTS CUSTOMERS
(
    ID VARCHAR(20) PRIMARY KEY,
    FIRST_NAME VARCHAR(100),
    LAST_NAME VARCHAR(100),
    EMAIL VARCHAR(100),
    GENDER VARCHAR(20),
    CONTACT_NO VARCHAR(50),
    COUNTRY VARCHAR(200),
    DOB VARCHAR(20)
);