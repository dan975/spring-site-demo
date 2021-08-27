DROP TABLE IF EXISTS product_prices;

CREATE TABLE product_prices (
  product_key INT PRIMARY KEY,
  price DECIMAL(10,2) NOT NULL
);

INSERT INTO product_prices (product_key, price) VALUES
  (1, 13.99),
  (2, 25.99),
  (3, 14.49),
  (4, 9.99),
  (5, 39.99),
  (6, 29.99);