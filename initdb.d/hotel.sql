-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `tbl_booking_room`
--

DROP TABLE IF EXISTS `tbl_booking_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_booking_room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cccd` varchar(255) NOT NULL,
  `sdt` varchar(255) NOT NULL,
  `dia_chi` varchar(255) NOT NULL,
  `gioi_tinh` varchar(255) NOT NULL,
  `ho_tenkh` varchar(255) NOT NULL,
  `ngay_den` datetime(6) NOT NULL,
  `ngay_di` datetime(6) DEFAULT NULL,
  `so_tien_coc` double NOT NULL,
  `thanh_tien` double DEFAULT NULL,
  `trang_thai` varchar(255) NOT NULL,
  `ma_phong` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqjve0gt51qpap2mna2jq050wu` (`cccd`),
  KEY `FK8a0m4lnrg5qjeghcnwh0q9lyo` (`ma_phong`),
  KEY `FKfwv0dlr5hu6go6kkrgu0a1y8p` (`user_id`),
  CONSTRAINT `FK8a0m4lnrg5qjeghcnwh0q9lyo` FOREIGN KEY (`ma_phong`) REFERENCES `tbl_phong` (`id`),
  CONSTRAINT `FKfwv0dlr5hu6go6kkrgu0a1y8p` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_booking_room`
--

LOCK TABLES `tbl_booking_room` WRITE;
/*!40000 ALTER TABLE `tbl_booking_room` DISABLE KEYS */;
INSERT INTO `tbl_booking_room` VALUES (1,'098765876','2345','HN','Nam','a','2024-07-22 21:41:00.000000','2024-07-29 21:41:00.000000',-1,2050,'Trống',2,4),(2,'1234567','2345','ddd','Nam','dddd','2024-07-22 21:47:00.000000','2024-07-23 21:47:00.000000',22222,1150,'Trống',2,4);
/*!40000 ALTER TABLE `tbl_booking_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_booking_room_dich_vu`
--

DROP TABLE IF EXISTS `tbl_booking_room_dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_booking_room_dich_vu` (
  `dich_vu_id` bigint NOT NULL,
  `booking_room_id` bigint NOT NULL,
  PRIMARY KEY (`dich_vu_id`,`booking_room_id`),
  KEY `FKdvdhnej8npw4dsmdiab7nospo` (`booking_room_id`),
  CONSTRAINT `FKdvdhnej8npw4dsmdiab7nospo` FOREIGN KEY (`booking_room_id`) REFERENCES `tbl_booking_room` (`id`),
  CONSTRAINT `FKki5s4wrh99md8ue4iqrg6npuh` FOREIGN KEY (`dich_vu_id`) REFERENCES `tbl_dich_vu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_booking_room_dich_vu`
--

LOCK TABLES `tbl_booking_room_dich_vu` WRITE;
/*!40000 ALTER TABLE `tbl_booking_room_dich_vu` DISABLE KEYS */;
INSERT INTO `tbl_booking_room_dich_vu` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `tbl_booking_room_dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_dich_vu`
--

DROP TABLE IF EXISTS `tbl_dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_dich_vu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `giadv` double NOT NULL,
  `madv` varchar(255) NOT NULL,
  `tendv` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfnys1flepbavccu732uvl7x45` (`madv`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_dich_vu`
--

LOCK TABLES `tbl_dich_vu` WRITE;
/*!40000 ALTER TABLE `tbl_dich_vu` DISABLE KEYS */;
INSERT INTO `tbl_dich_vu` VALUES (1,1000,'DV01','DV01');
/*!40000 ALTER TABLE `tbl_dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_loai_phong`
--

DROP TABLE IF EXISTS `tbl_loai_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_loai_phong` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ma_loai` varchar(255) NOT NULL,
  `ten_loai_phong` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKen3jvg92htjr326cysw4fnntu` (`ma_loai`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_loai_phong`
--

LOCK TABLES `tbl_loai_phong` WRITE;
/*!40000 ALTER TABLE `tbl_loai_phong` DISABLE KEYS */;
INSERT INTO `tbl_loai_phong` VALUES (1,'ML010','vip loại 1'),(2,'ML01','vip loại 1');
/*!40000 ALTER TABLE `tbl_loai_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_phong`
--

DROP TABLE IF EXISTS `tbl_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_phong` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dia_chi` varchar(255) NOT NULL,
  `gia_phong` double NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `ma_phong` bigint NOT NULL,
  `ten_phong` varchar(255) NOT NULL,
  `tinh_trang_phong` varchar(255) NOT NULL,
  `ma_loai` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK1ptbnjpatppa0o52rt7rojq49` (`ma_phong`),
  KEY `FK16wl705gjjxgfd2jt3n66yl9w` (`ma_loai`),
  CONSTRAINT `FK16wl705gjjxgfd2jt3n66yl9w` FOREIGN KEY (`ma_loai`) REFERENCES `tbl_loai_phong` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_phong`
--

LOCK TABLES `tbl_phong` WRITE;
/*!40000 ALTER TABLE `tbl_phong` DISABLE KEYS */;
INSERT INTO `tbl_phong` VALUES (1,'Hà nội',1000,NULL,1,'Vip','trống',1),(2,'123 Đường ABC, TP HCM',150,NULL,123,'Phòng Deluxe','Trống',1);
/*!40000 ALTER TABLE `tbl_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_role`
--

DROP TABLE IF EXISTS `tbl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_role`
--

LOCK TABLES `tbl_role` WRITE;
/*!40000 ALTER TABLE `tbl_role` DISABLE KEYS */;
INSERT INTO `tbl_role` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `tbl_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqyhp9ytkc0o8uapy1vtqmw350` (`role_id`),
  CONSTRAINT `FKqyhp9ytkc0o8uapy1vtqmw350` FOREIGN KEY (`role_id`) REFERENCES `tbl_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (3,'$2a$10$lYBRGfPLYagxuKsu581Zjeknm24ev1XTUf00b6I42QSXH5tR1rvNm','admin',1),(4,'$2a$10$fscgciD0sqHlDM9uS7lnj.D.SoShKhPjNB4jhD/buf6iNlEwOx6s.','user',2);
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-22 23:24:32
