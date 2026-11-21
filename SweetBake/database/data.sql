USE sweetbake;

INSERT INTO users (user_id, full_name, email, password_hash, phone, role) VALUES
(1, 'Bakery Admin', 'admin@sweetbake.com', '$2a$10$DhL6.GwRlh6U8SWNRpHNY.tiDw4XsU7sPUbhk3S3ceViH4gDqajgy', '0500000001', 'ADMIN'),
(2, 'Main Baker', 'baker@sweetbake.com', '$2a$10$LP5xac3UotKY0p3L6x/Tzeh7iNsw9/5yOO876WIYuUuHty6OG0/Q.', '0500000002', 'BAKER'),
(3, 'Test Customer', 'customer@sweetbake.com', '$2a$10$eAaw/pK.QYIirabrOJOfqeAnx3Q/2toiuDM2sTvyuiK/DrFRswQYK', '0500000003', 'CUSTOMER');

INSERT INTO categories (category_id, name, description, image) VALUES
(1, 'Custom Cakes', 'Choose the size, flavor, filling and toppings.', 'desserts/cakes.jpg'),
(2, 'Cupcakes', 'Boxes of 6 or 12 with your favorite frosting.', 'desserts/cupcakes.jpg'),
(3, 'Cheesecake', 'Creamy cheesecake with a sauce on top.', 'desserts/cheesecake.jpg'),
(4, 'Brownies', 'Soft fudge brownies in boxes of 6 or 12.', 'desserts/brownies.jpg'),
(5, 'Tarts', 'Crispy tarts with cream and fresh fruits.', 'desserts/tarts.jpg');

INSERT INTO options (option_id, category_id, option_type, name, price) VALUES
(1, 1, 'SIZE', 'Small 6" (8 servings)', 120),
(2, 1, 'SIZE', 'Medium 8" (15 servings)', 180),
(3, 1, 'SIZE', 'Large 10" (25 servings)', 260),
(4, 1, 'SIZE', 'Two-tier (40 servings)', 420),
(5, 1, 'FLAVOR', 'Vanilla', 0),
(6, 1, 'FLAVOR', 'Chocolate', 0),
(7, 1, 'FLAVOR', 'Red Velvet', 20),
(8, 1, 'FLAVOR', 'Lotus', 25),
(9, 1, 'FLAVOR', 'Pistachio', 30),
(10, 1, 'FLAVOR', 'Carrot', 15),
(11, 1, 'FILLING', 'No filling', 0),
(12, 1, 'FILLING', 'Strawberry jam', 10),
(13, 1, 'FILLING', 'Chocolate ganache', 15),
(14, 1, 'FILLING', 'Salted caramel', 15),
(15, 1, 'FILLING', 'Cream cheese', 10),
(16, 1, 'TOPPING', 'Fresh strawberries', 15),
(17, 1, 'TOPPING', 'Chocolate chips', 10),
(18, 1, 'TOPPING', 'Macarons', 30),
(19, 1, 'TOPPING', 'Edible flowers', 20),
(20, 1, 'TOPPING', 'Gold leaf', 25),
(21, 2, 'SIZE', 'Box of 6', 60),
(22, 2, 'SIZE', 'Box of 12', 110),
(23, 2, 'FLAVOR', 'Vanilla', 0),
(24, 2, 'FLAVOR', 'Chocolate', 0),
(25, 2, 'FLAVOR', 'Red Velvet', 10),
(26, 2, 'FLAVOR', 'Lotus', 10),
(27, 2, 'FROSTING', 'Buttercream', 0),
(28, 2, 'FROSTING', 'Cream cheese', 5),
(29, 2, 'FROSTING', 'Chocolate', 5),
(30, 2, 'TOPPING', 'Sprinkles', 5),
(31, 2, 'TOPPING', 'Mini macarons', 20),
(32, 2, 'TOPPING', 'Fresh berries', 10),
(33, 3, 'SIZE', 'Small 6"', 140),
(34, 3, 'SIZE', 'Large 9"', 220),
(35, 3, 'FLAVOR', 'Classic New York', 0),
(36, 3, 'FLAVOR', 'Lotus', 20),
(37, 3, 'FLAVOR', 'Pistachio', 25),
(38, 3, 'SAUCE', 'No sauce', 0),
(39, 3, 'SAUCE', 'Strawberry', 10),
(40, 3, 'SAUCE', 'Blueberry', 10),
(41, 3, 'SAUCE', 'Salted caramel', 10),
(42, 4, 'SIZE', 'Box of 6', 50),
(43, 4, 'SIZE', 'Box of 12', 90),
(44, 4, 'FLAVOR', 'Classic fudge', 0),
(45, 4, 'FLAVOR', 'Nutella', 10),
(46, 4, 'FLAVOR', 'Pistachio', 15),
(47, 4, 'TOPPING', 'Chocolate drizzle', 5),
(48, 4, 'TOPPING', 'Crushed nuts', 8),
(49, 4, 'TOPPING', 'Sea salt', 3),
(50, 5, 'SIZE', 'Mini box (6 pieces)', 70),
(51, 5, 'SIZE', 'Large tart 9" (8 servings)', 160),
(52, 5, 'FILLING', 'Vanilla custard', 0),
(53, 5, 'FILLING', 'Chocolate ganache', 10),
(54, 5, 'FILLING', 'Lemon curd', 10),
(55, 5, 'FILLING', 'Pistachio cream', 20),
(56, 5, 'TOPPING', 'Fresh berries', 15),
(57, 5, 'TOPPING', 'Sliced fruits', 12),
(58, 5, 'TOPPING', 'Caramelized nuts', 10),
(59, 5, 'TOPPING', 'Meringue', 8);

INSERT INTO orders (order_id, customer_id, pickup_date, notes, total_price, status) VALUES
(1, 3, '2026-11-15', 'Write Happy Birthday Sara on the cake', 370, 'PICKED_UP'),
(2, 3, '2026-12-10', NULL, 170, 'BAKING'),
(3, 3, '2026-12-20', 'Please pack the tarts in two boxes', 390, 'PENDING');

INSERT INTO order_items (item_id, order_id, category_id, quantity, price) VALUES
(1, 1, 1, 1, 240),
(2, 1, 2, 1, 130),
(3, 2, 3, 1, 170),
(4, 3, 5, 2, 195);

INSERT INTO order_item_options (item_id, option_id) VALUES
(1, 2), (1, 7), (1, 12), (1, 18),
(2, 22), (2, 26), (2, 28), (2, 30),
(3, 33), (3, 36), (3, 39),
(4, 51), (4, 55), (4, 56);

INSERT INTO reviews (review_id, order_id, rating, comment) VALUES
(1, 1, 5, 'Beautiful cake and the cupcakes were fresh. Will order again!');
