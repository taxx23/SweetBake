DROP DATABASE IF EXISTS sweetbake;
CREATE DATABASE sweetbake;
USE sweetbake;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role ENUM('CUSTOMER', 'BAKER', 'ADMIN') NOT NULL DEFAULT 'CUSTOMER'
);

CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    image VARCHAR(255)
);

CREATE TABLE options (
    option_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    option_type ENUM('SIZE', 'FLAVOR', 'FILLING', 'FROSTING', 'SAUCE', 'TOPPING') NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(8,2) NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    pickup_date DATE NOT NULL,
    notes VARCHAR(255),
    total_price DECIMAL(8,2) NOT NULL,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'BAKING', 'READY', 'PICKED_UP', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (customer_id) REFERENCES users(user_id)
);

CREATE TABLE order_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    category_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(8,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE order_item_options (
    item_id INT NOT NULL,
    option_id INT NOT NULL,
    PRIMARY KEY (item_id, option_id),
    FOREIGN KEY (item_id) REFERENCES order_items(item_id),
    FOREIGN KEY (option_id) REFERENCES options(option_id)
);

CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL UNIQUE,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(500),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
