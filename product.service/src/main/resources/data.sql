DROP TABLE IF EXISTS product_image_map;
DROP TABLE IF EXISTS product_images;
DROP TABLE IF EXISTS product_categories;
DROP TABLE IF EXISTS product_subcategory_names;
DROP TABLE IF EXISTS product_category_names;
DROP TABLE IF EXISTS products;

CREATE TABLE product_categories (
  product_key INT PRIMARY KEY,
  category_id INT NOT NULL,
  subcategory_id INT NOT NULL
);

CREATE TABLE product_category_names (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(50) NOT NULL
);

CREATE TABLE product_subcategory_names (
  subcategory_id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT NOT NULL,
  title VARCHAR(50) NOT NULL
);

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_name VARCHAR(250) NOT NULL,
  product_description VARCHAR(1000) NOT NULL
);

CREATE TABLE product_image_map (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_key INT NOT NULL,
  image LONGBLOB,
  color VARCHAR(12) NOT NULL,
  is_main_image BIT NOT NULL
);

INSERT INTO products (product_name, product_description) VALUES
  ('T-shirt 1', 'T-shirt 1 description'),
  ('Blouse 1', 'Blouse 1 description'),
  ('Dress 1 Casual', 'Dress 1 description'),
  ('Dress 2 Evening', 'Dress 2 description'),
  ('Dress 3 Summer', 'Dress 3 description'),
  ('Dress 4 Summer', 'Dress 4 description');

INSERT INTO product_category_names (title) VALUES
  ('Tops'),
  ('Dresses');

INSERT INTO product_subcategory_names (category_id, title) VALUES
  (1, 'T-shirts'),
  (1, 'Blouses'),
  (2, 'Casual dresses'),
  (2, 'Evening dresses'),
  (2, 'Summer dresses');

INSERT INTO product_categories (product_key, category_id, subcategory_id) VALUES
  (1, 1, 1),
  (2, 1, 2),
  (3, 2, 3),
  (4, 2, 4),
  (5, 2, 5),
  (6, 2, 5);
