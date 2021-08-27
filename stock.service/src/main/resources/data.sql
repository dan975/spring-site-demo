DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS product_sizes;
DROP TABLE IF EXISTS size_mappings;
DROP TABLE IF EXISTS product_colors;
DROP TABLE IF EXISTS color_mappings;

CREATE TABLE product_sizes (
   id INT AUTO_INCREMENT PRIMARY KEY,
   product_key INT NOT NULL,
   size_id INT NOT NULL
);

CREATE TABLE size_mappings (
    size_id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE product_colors (
   id INT AUTO_INCREMENT PRIMARY KEY,
   product_key INT NOT NULL,
   color_id INT NOT NULL
);

CREATE TABLE color_mappings (
    color_id INT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    rgb CHAR(6) NOT NULL
);

CREATE TABLE stock (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_key INT NOT NULL,
  serial_number VARCHAR(30) NOT NULL,
  size_id INT NOT NULL,
  color_id INT NOT NULL
);

INSERT INTO product_sizes (product_key, size_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (2, 2),
  (2, 3),
  (2, 4),
  (3, 1),
  (3, 2),
  (3, 3),
  (4, 2),
  (4, 4),
  (5, 1),
  (5, 2),
  (5, 3),
  (5, 4),
  (6, 1),
  (6, 2),
  (6, 3);

INSERT INTO size_mappings (size_id, name) VALUES
  (1, 'Extra small'),
  (2, 'Small'),
  (3, 'Medium'),
  (4, 'Large');

INSERT INTO product_colors (product_key, color_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (2, 1),
  (3, 4),
  (3, 5),
  (4, 2),
  (4, 3),
  (5, 1),
  (5, 3),
  (6, 1),
  (6, 2),
  (6, 3),
  (6, 5);

INSERT INTO color_mappings (color_id, name, rgb) VALUES
  (1, 'Black', '000000'),
  (2, 'Red', 'ff0000'),
  (3, 'Green', '00ff00'),
  (4, 'Purple', '800080'),
  (5, 'White', 'ffffff');

INSERT INTO stock(product_key, serial_number, size_id, color_id) VALUES
  (1, 'AAA001', 1, 1),
  (1, 'AAA002', 1, 2),
  (1, 'AAA003', 1, 3),
  (1, 'AAA004', 2, 1),
  (1, 'AAA005', 2, 1),
  (2, 'AAA006', 2, 1),
  (2, 'AAA007', 3, 1),
  (2, 'AAA008', 4, 1),
  (2, 'AAA009', 3, 1),
  (2, 'AAA010', 2, 1),
  (3, 'AAA011', 1, 4),
  (4, 'AAA012', 4, 2),
  (5, 'AAA013', 1, 3),
  (6, 'AAA014', 3, 5);
