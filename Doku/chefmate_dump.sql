-- MySQL dump 10.13  Distrib 9.0.0, for Win64 (x86_64)
--
-- Host: localhost    Database: chefmate
-- ------------------------------------------------------
-- Server version	9.0.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `recipe_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `quantity` varchar(50) NOT NULL,
  `unit` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ingredients_ibfk_1` (`recipe_id`),
  CONSTRAINT `ingredients_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
INSERT INTO `ingredients` VALUES (25,22,'Seelachsfilet','300','g'),(26,22,'Bandnudeln','180','g'),(27,22,'Butter','2','El'),(28,22,'Mehl','1','El'),(29,22,'Gemüsebrühe','350','ml'),(30,22,'Schlagsahne','150','ml'),(31,22,' Senf (grob)','1','Tl'),(35,25,'Zartbitter-Kuvertüre','200','gr'),(36,25,'Pistazienmus gezuckert','100','gr'),(37,25,'Kadayif','100','gr'),(38,25,'Sesampaste','1','EL'),(39,25,'Butter','1','EL'),(40,25,'Pistazien gehackt','50','gr'),(41,26,'Mehl','600','gr'),(42,26,'Salz','1','TL'),(43,26,'Zucker','100','gr'),(44,26,'Vanillezucker','1','TL'),(45,26,'Margarine','100','gr'),(46,26,'Ei','1','st'),(47,26,'Hefe','1','pck'),(48,26,'Milch lauwarm','250','ml'),(49,27,'Butter','250','gr'),(50,28,'fleisch','1','kg'),(52,30,'spagetti','1','kg'),(53,31,'knoblauch','1','g'),(54,32,'wiener schnitzel','1','g'),(55,33,'gem','1','g'),(56,34,'syx','1','g'),(57,35,'vxcv','1','d'),(58,36,'vx','1','dc'),(59,37,'ds','1','w'),(60,38,'fd','1','wd'),(61,39,'Ei','2','stk'),(62,39,'Butter','1','El');
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `ingredients` text NOT NULL,
  `description` text NOT NULL,
  `category` enum('Vorspeisen','Suppen','Salate','Hauptgerichte','Beilagen','Desserts','Vegetarische','Vegane') DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  CONSTRAINT `recipes_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` VALUES (22,12,'Seelachsragout mit Bandnudeln','300 g Seelachsfilet, 180 g Bandnudeln, 2 El Butter, 1 El Mehl, 350 ml Gemüsebrühe, 150 ml Schlagsahne, 1 Tl  Senf (grob)','Fischfilet in 1 cm große Würfel schneiden. Zwiebel fein würfeln. Dillspitzen abzupfen und grob hacken. Nudeln in einem Topf in reichlich kochendem Salzwasser nach Packungsanweisung bissfest garen.\nButter in einem Topf zerlassen. Zwiebeln zugeben und darin glasig dünsten. Mehl zugeben und unter Rühren mit dem Schneebesen farblos anschwitzen. Brühe nach und nach mit dem Schneebesen einrühren. Mit Salz und Pfeffer würzen und bei milder Hitze 10 Minuten kochen.\n Sahne und Senf in die Sauce geben und einmal aufkochen. Fisch und Dill zugeben und abgedeckt bei milder Hitze 5 Minuten ziehen lassen. Nudeln abgießen, abtropfen lassen und mit dem Seelachsragout auf Tellern anrichten.\n',NULL,'file:/C:/Users/Bria/Downloads/fjt2022011036-seelachs-ragout-mit-bandnudeln.jpg'),(25,15,'Dubai Schokolade','200 gr Zartbitter-Kuvertüre, 100 gr Pistazienmus gezuckert, 100 gr Kadayif, 1 EL Sesampaste, 1 EL Butter, 50 gr Pistazien gehackt','Die Kuvertüre nach Anleitung schmelzen und gut die Hälfte in eine Silikon-Kastenform (es geht auch eine normale Kastenform, aber das Lösen der Schokolade ist dann etwas schwieriger) füllen und gleichmäßig am Boden verteilen. Die Kuvertüre an den Rändern ca. 1.5 cm hochziehen. Danach die Form kaltstellen bis alles wieder festgeworden ist.\nn der Zwischenzeit das Kadayif schmal herunterschneiden, bis kleine Stücke entstanden sind. Die Kadayif Stücke mit der Butter in einer Pfanne langsam rösten, bis sie eine goldbraune Färbung angenommen haben.\nDie gerösteten Kadayif, die Sesampaste und das Pistazienmus in einer weiteren Schüssel vorsichtig vermischen und die Masse auskühlen lassen.\nZunächst den Rest der Kuvertüre nochmal schmelzen (falls diese bis jetzt schon wieder hartgeworden ist. Einfach im Wasserbad). Nun die Pistazien-Kadayif-Masse in der Silikonform auf dem gehärteten Kuvertüre-Boden gleichmäßig verstreichen. Mit der restlichen geschmolzenen Kuvertüre bedecken, so dass die Pistazienmasse von allen Seiten ummantelt ist.\nDie noch weiche Kuvertüre-Oberfläche mit den gehackten Pistazien bestreuen und alles festwerden lassen. Danach die Schokolade vorsichtig aus der Form lösen und genießen.\nDie Schokolade eignet sich auch toll zum Verschenken. Grundsätzlich kann natürlich auch Vollmilch-Kuvertüre verwendet werden, ggf. zusammen mit Pistaziencreme ohne Zucker - je nach Geschmack.\n',NULL,'file:/C:/Users/Bria/Downloads/dubai-schokolade-selbstgemacht-rezept-bild-nr-17210.jpg'),(26,15,'Kohlrabi-Schnitzel ','600 gr Mehl, 1 TL Salz, 100 gr Zucker, 1 TL Vanillezucker, 100 gr Margarine, 1 st Ei, 1 pck Hefe, 250 ml Milch lauwarm','Hefeteig herstellen: Milch mit Zucker, Margarine, Vanillezucker, Ei und Hefe verrühren, Mehl und Salz untermengen. Den Teig 3 - 5 Min. kneten lassen. Diesen zugedeckt 30 Min. an einem warmen Ort gehen lassen. Den Teig in 3 Portionen teilen und zu einem Zopf formen, diesen auf ein Backblech mit Backpapier legen und nochmals 30 Min. abgedeckt gehen lassen. Den Zopf im vorgeheizten Backofen bei 180°C ca. 35 Min. backen.\nGutes Gelingen und guten Appetit wünscht Michaela.\n',NULL,'file:/C:/Users/Bria/Downloads/OIP%20(1).jpg'),(27,15,'Sandkuchen','250 gr Butter','1.Teig wie gewohnt verrühren\n\n2.Teig in eine gefettete Kastenform geben\n\n3.Umluft/ Heißluft: 160 Grad Ober-/ Unterhitze: 180 Grad 60 Minuten backen Nach 15 Minuten den Oberfläche quer einschneiden\n\n4.lässt sich mit allem kombinieren! entweder pur, oder mit likör, schoko, schokostreusel, zitrone, nüsse, etc\n4.lässt sich mit allem kombinieren! entweder pur, oder mit likör, schoko, schokostreusel, zitrone, nüsse, etc\n',NULL,'file:/C:/Users/Bria/Downloads/einfacher-sandkuchen.jpg'),(28,15,'spaghetti bolognese','1 kg fleisch','sdasd\n',NULL,'file:/C:/Users/Bria/Downloads/OIP%20(2).jpg'),(30,15,'spaghetti carbonara','1 kg spagetti','csdda\n',NULL,'file:/C:/Users/Bria/Downloads/R.jpg'),(31,15,'knoblauchcremesuppe','1 g knoblauch','sdasdas\n',NULL,'file:/C:/Users/Bria/Downloads/R%20(1).jpg'),(32,15,'wiener schnitzel','1 g wiener schnitzel','<asydxfcgvh\n',NULL,'file:/C:/Users/Bria/Downloads/OIP%20(3).jpg'),(33,15,'gemüsesuppe','1 g gem','sydxv\n',NULL,'file:/C:/Users/Bria/Downloads/Gemüseesuppe_shutterstock_393469756-600x404.jpg'),(34,15,'reisauflauf','1 g syx','yfcx\n',NULL,'file:/C:/Users/Bria/Downloads/495653-960x720-suesser-reisauflauf.jpg'),(35,15,'kürbiscremesuppe','1 d vxcv','xdcv\n',NULL,'file:/C:/Users/Bria/Downloads/R%20(2).jpg'),(36,15,'wildschweinbraten','1 dc vx','cyc\n',NULL,'file:/C:/Users/Bria/Downloads/R%20(3).jpg'),(37,15,'Gulasch','1 w ds','dsd\n',NULL,'file:/C:/Users/Bria/Downloads/R%20(4).jpg'),(38,15,'Pizza','1 wd fd','ydv\n',NULL,'file:/C:/Users/Bria/Downloads/__opt__aboutcom__coeus__resources__content_migration__simply_recipes__uploads__2019__09__easy-pepperoni-pizza-lead-3-8f256746d649404baa36a44d271329bc.jpg'),(39,15,'SpigelEi','2 stk Ei, 1 El Butter','butter wärmen\nEi hineinschlagen\n',NULL,'file:/C:/Users/Bria/Downloads/R%20(5).jpg');
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppinglist`
--

DROP TABLE IF EXISTS `shoppinglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoppinglist` (
  `idshoppinglist` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `item` varchar(45) NOT NULL,
  `quantily` int NOT NULL,
  PRIMARY KEY (`idshoppinglist`),
  KEY `userid_idx` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppinglist`
--

LOCK TABLES `shoppinglist` WRITE;
/*!40000 ALTER TABLE `shoppinglist` DISABLE KEYS */;
INSERT INTO `shoppinglist` VALUES (58,2,'kenyer',3),(68,2,'brot',5),(69,0,'alma',3),(72,12,'alma',4),(74,15,'Ei',1);
/*!40000 ALTER TABLE `shoppinglist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Brigitta','juhasz','brigi','admin','get@gmail.com'),(4,'juhasz2','Brigitta2','brigi2','admin','bbb@gmail.com'),(12,'brigitta2','brigitta2','brigitta2','$2a$10$yJZdssdQoX9uClIahbm.d.vYWlF9D9Hsla2CKmw4wZs/5k5fXovSW','brigitta2'),(13,'cycx','cxyc','ydvc','$2a$10$IccboUwBHCU5uQ6S6cEXBe2HiFgpLmSLfSvJ0Ua8xZZYXymQ8Hvjq','cyxc'),(14,'s<sfy','ccxxy','xcyxc','$2a$10$Uy2oFEOOE7sj3z9UJ3VvFuBfVCu8dXoc/I1KlZk8yM0/91R0SWiUe','xyxc'),(15,'brigitta','Juhasz','admin2','$2a$10$kiZnDoUpVaOZK3WlKrwbse5xHHF2K8YDzPaAWs9ru02VNxEidCa4y','admin2@gmali.com'),(16,'Bugs','Bunny','Bunny1','$2a$10$e/0qQjDL.QRRN.twd8X/2e4etfbs2f4torcyobzLKwJ09SWtGgK1G','asd@dig.com');
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

-- Dump completed on 2024-11-22 20:58:00
