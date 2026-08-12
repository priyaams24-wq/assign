-- Note: You should create the database 'ecommerce_db' in PostgreSQL before running this script:
-- CREATE DATABASE ecommerce_db;

DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

CREATE TABLE customers (
    customer_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    credit DECIMAL(15, 2) NOT NULL
);

CREATE TABLE products (
    product_no VARCHAR(50) PRIMARY KEY,
    price DECIMAL(15, 2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE orders (
    order_no SERIAL PRIMARY KEY,
    order_date DATE NOT NULL,
    customer_id VARCHAR(50) NOT NULL,
    product_no VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (product_no) REFERENCES products(product_no)
);

INSERT INTO customers (customer_id, password, email, credit) VALUES
('101', 'pass101', 'rahul@gmail.com', 150000.00),
('102', 'pass102', 'amit@gmail.com', 500000.00),
('103', 'pass103', 'priya@gmail.com', 1000000.00),
('104', 'pass104', 'neha@gmail.com', 50000.00);

INSERT INTO products (product_no, price, stock) VALUES
('P101', 75000.00, 10),
('P102', 45000.00, 20),
('P103', 125000.00, 5),
('P104', 25000.00, 50),
('P105', 15000.00, 0);

INSERT INTO orders (order_date, customer_id, product_no, quantity) VALUES
('2026-08-01', '101', 'P101', 2),
('2026-08-04', '101', 'P102', 1),
('2026-08-08', '101', 'P104', 4),
('2026-08-02', '102', 'P103', 3),
('2026-08-05', '102', 'P104', 2),
('2026-08-03', '103', 'P101', 5),
('2026-08-06', '103', 'P103', 2),
('2026-08-07', '103', 'P104', 10),
('2026-08-09', '104', 'P102', 1);
