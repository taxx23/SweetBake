CREATE DATABASE  IF NOT EXISTS `sweetbake` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sweetbake`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: sweetbake
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Cakes','Custom cakes for every occasion','cakes.jpg',1),(2,'Cupcakes','Delicious customizable cupcakes','cupcakes.jpg',1),(3,'Cheesecake','Creamy cheesecakes','cheesecake.jpg',1),(4,'Brownies','Rich chocolate brownies','brownies.jpg',1),(5,'Tarts','Fresh and delicious tarts','tarts.jpg',1);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,1,'SIZE','Small 6\" (8 servings)',120.00,1),(2,1,'SIZE','Medium 8\" (15 servings)',180.00,1),(3,1,'SIZE','Large 10\" (25 servings)',260.00,1),(4,1,'SIZE','Two-tier (40 servings)',420.00,1),(5,1,'FLAVOR','Vanilla',0.00,1),(6,1,'FLAVOR','Chocolate',0.00,1),(7,1,'FLAVOR','Red Velvet',20.00,1),(8,1,'FLAVOR','Lotus',25.00,1),(9,1,'FLAVOR','Pistachio',30.00,1),(10,1,'FLAVOR','Carrot',15.00,1),(11,1,'FILLING','No filling',0.00,1),(12,1,'FILLING','Strawberry jam',10.00,1),(13,1,'FILLING','Chocolate ganache',15.00,1),(14,1,'FILLING','Salted caramel',15.00,1),(15,1,'FILLING','Cream cheese',10.00,1),(16,1,'TOPPING','Fresh strawberries',15.00,1),(17,1,'TOPPING','Chocolate chips',10.00,1),(18,1,'TOPPING','Macarons',30.00,1),(19,1,'TOPPING','Edible flowers',20.00,1),(20,1,'TOPPING','Gold leaf',25.00,1),(21,2,'SIZE','Box of 6',60.00,1),(22,2,'SIZE','Box of 12',110.00,1),(23,2,'FLAVOR','Vanilla',0.00,1),(24,2,'FLAVOR','Chocolate',0.00,1),(25,2,'FLAVOR','Red Velvet',10.00,1),(26,2,'FLAVOR','Lotus',10.00,1),(27,2,'FROSTING','Buttercream',0.00,1),(28,2,'FROSTING','Cream cheese',5.00,1),(29,2,'FROSTING','Chocolate',5.00,1),(30,2,'TOPPING','Sprinkles',5.00,1),(31,2,'TOPPING','Mini macarons',20.00,1),(32,2,'TOPPING','Fresh berries',10.00,1),(33,3,'SIZE','Small 6\"',140.00,1),(34,3,'SIZE','Large 9\"',220.00,1),(35,3,'FLAVOR','Classic New York',0.00,1),(36,3,'FLAVOR','Lotus',20.00,1),(37,3,'FLAVOR','Pistachio',25.00,1),(38,3,'SAUCE','No sauce',0.00,1),(39,3,'SAUCE','Strawberry',10.00,1),(40,3,'SAUCE','Blueberry',10.00,1),(41,3,'SAUCE','Salted caramel',10.00,1),(42,4,'SIZE','Box of 6',50.00,1),(43,4,'SIZE','Box of 12',90.00,1),(44,4,'FLAVOR','Classic fudge',0.00,1),(45,4,'FLAVOR','Nutella',10.00,1),(46,4,'FLAVOR','Pistachio',15.00,1),(47,4,'TOPPING','Chocolate drizzle',5.00,1),(48,4,'TOPPING','Crushed nuts',8.00,1),(49,4,'TOPPING','Sea salt',3.00,1),(50,5,'SIZE','Mini box (6 pieces)',70.00,1),(51,5,'SIZE','Large tart 9\" (8 servings)',160.00,1),(52,5,'FILLING','Vanilla custard',0.00,1),(53,5,'FILLING','Chocolate ganache',10.00,1),(54,5,'FILLING','Lemon curd',10.00,1),(55,5,'FILLING','Pistachio cream',20.00,1),(56,5,'TOPPING','Fresh berries',15.00,1),(57,5,'TOPPING','Sliced fruits',12.00,1),(58,5,'TOPPING','Caramelized nuts',10.00,1),(59,5,'TOPPING','Meringue',8.00,1);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order_options`
--

LOCK TABLES `order_options` WRITE;
/*!40000 ALTER TABLE `order_options` DISABLE KEYS */;
INSERT INTO `order_options` VALUES (1,1,120.00),(2,1,120.00);
/*!40000 ALTER TABLE `order_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order_status_history`
--

LOCK TABLES `order_status_history` WRITE;
/*!40000 ALTER TABLE `order_status_history` DISABLE KEYS */;
INSERT INTO `order_status_history` VALUES (1,1,1,'PENDING','ACCEPTED','2026-11-21 13:15:53'),(2,1,1,'ACCEPTED','BAKING','2026-11-21 13:30:57'),(3,1,1,'BAKING','READY','2026-11-21 13:35:22'),(4,1,1,'READY','PICKED_UP','2026-11-21 13:35:22');
/*!40000 ALTER TABLE `order_status_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,1,'2026-11-25','Happy Birthday!','Test cake order',120.00,'PICKED_UP',NULL,'2026-11-21 13:12:26'),(2,1,1,'2026-11-28','Happy Birthday!','Test order from Java',120.00,'PENDING',NULL,'2026-11-21 13:33:06');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,1,1,5,'Amazing cake! Loved it!','2026-11-21 13:51:54');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES ('daily_capacity','10');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Test Customer','customer@sweetbake.test','PLACEHOLDER_NOT_A_REAL_HASH','0501234567','CUSTOMER','2026-11-21 13:11:51');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-21 18:10:22
