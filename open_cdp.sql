-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: open_cdp
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `ai_context`
--

DROP TABLE IF EXISTS `ai_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_context` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bId` int DEFAULT NULL,
  `pId` int DEFAULT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_context`
--

LOCK TABLES `ai_context` WRITE;
/*!40000 ALTER TABLE `ai_context` DISABLE KEYS */;
INSERT INTO `ai_context` VALUES (1,NULL,NULL,'### Tờ Rơi\n- **Các kiểu gia công đã bao gồm mà không tính phí**: Xén thành phẩm  \n- **Kích thước A4**: 210 x 297 mm  \n- **Loại giấy**: Coucher  \n### Lưu ý\n### Không in khi nội dung mờ\n','Tờ rơi','2025-06-09 00:28:27','2025-06-09 00:28:27'),(2,NULL,NULL,'### Tờ Rơi\n- **Các kiểu gia công đã bao gồm mà không tính phí**: Xén thành phẩm  \n- **Kích thước A4**: 210 x 297 mm  \n- **Loại giấy**: Coucher  \n### Lưu ý\n### Không in khi nội dung mờ\n','Tờ rơi2','2025-06-09 22:28:20','2025-06-09 22:28:20');
/*!40000 ALTER TABLE `ai_context` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attributed`
--

DROP TABLE IF EXISTS `attributed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attributed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status` int DEFAULT '1',
  `icon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10026 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attributed`
--

LOCK TABLES `attributed` WRITE;
/*!40000 ALTER TABLE `attributed` DISABLE KEYS */;
INSERT INTO `attributed` VALUES (10008,'PHong cách',1,NULL),(10009,'Kích thước',1,NULL),(10010,'Màu sắc',1,NULL),(10011,'Khối lượng',1,NULL),(10012,'Kiểu dáng',1,NULL),(10013,'Số lượng',1,NULL),(10014,'Diện tích',1,NULL),(10015,'Giá',1,NULL),(10016,'Quy cách',1,NULL),(10017,'Vật liệu',1,NULL),(10018,'Thuôc tính chung',1,NULL),(10019,'In ấn',1,NULL),(10020,'Gia công:',1,NULL),(10021,'Hệ thống',1,NULL),(10022,'Học kỳ',1,NULL),(10023,'Dạng kết cấu',1,NULL),(10024,'Bồi vỏ',1,NULL),(10025,'Loại',1,NULL);
/*!40000 ALTER TABLE `attributed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attributed_value`
--

DROP TABLE IF EXISTS `attributed_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attributed_value` (
  `id` int NOT NULL AUTO_INCREMENT,
  `attributed_id` int NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `status` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10094 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attributed_value`
--

LOCK TABLES `attributed_value` WRITE;
/*!40000 ALTER TABLE `attributed_value` DISABLE KEYS */;
INSERT INTO `attributed_value` VALUES (10009,10008,'Tối giản B+',1),(10010,10009,'30x55x30',1),(10011,10009,'10x40x55',1),(10012,10010,'Đen',1),(10013,10010,'Đỏ',1),(10014,10008,'hjhhhh',1),(10015,10008,'hjhhhhlll',1),(10016,10008,'test1',1),(10017,10008,'test 2',1),(10018,10008,'ghghghhghghg',1),(10019,10008,'test',1),(10020,10008,'trtrttrtrrtttrtrttttrtrrtt',1),(10021,10008,'hunbg',1),(10022,10008,'tétt 3',1),(10023,10008,'test 5',1),(10024,10008,'6666',1),(10025,10008,'Sang trọng',1),(10026,10008,'xin chào',1),(10027,10008,'hihi',1),(10028,10008,'haha',1),(10029,10008,'Tinh tế',1),(10030,10008,'Bảo thủ',1),(10031,10008,'test 6',1),(10032,10008,'test 7',1),(10033,10008,'hihii7',1),(10034,10008,'test 8',1),(10035,10008,'hehe8',1),(10036,10008,'xin chào 2',1),(10037,10008,'xin chào 3',1),(10038,10008,'hehhee',1),(10039,10008,'ehhehe',1),(10040,10008,'hhhhh',1),(10041,10009,'20x40x50',1),(10042,10008,'Hiện đại',1),(10043,10008,'hello',1),(10044,10008,'test11',1),(10045,10008,'test8',1),(10046,10008,'hello 3',1),(10047,10008,'hihii66666',1),(10048,10008,'test57777',1),(10049,10008,'huhuhhu',1),(10050,10008,'test999',1),(10051,10008,'hiihih',1),(10052,10008,'jjjj',1),(10053,10008,'Linh hoat',1),(10054,10013,'Dành cho 4-5 nhân sự',1),(10055,10014,'13-14.5 m2',1),(10056,10015,'8-9 triệu vnđ/tháng',1),(10057,10013,'Dành cho 7-8 nhân sự',1),(10058,10013,'Dành cho 9-11 nhân sự',1),(10059,10013,'Dành cho 15-17 nhân sự',1),(10060,10014,'19.5-24 m2',1),(10061,10014,'24.5-34.5 m2',1),(10062,10014,'53m2',1),(10063,10015,'11-16.5 triệu vnđ/tháng',1),(10064,10015,'14.8-23 triệu vnđ/tháng',1),(10065,10015,'32 triệu vnđ/tháng',1),(10066,10016,'Kết cấu âm dương nắp lửng',1),(10067,10017,' Vỏ ngoài: Giấy C150 (N/A gsm)',1),(10068,10018,' Kích thước: 360x360x95',1),(10069,10017,'- Cốt vỏ ngoài: Carton thường 2,5 mm',1),(10070,10017,'- Tờ gác vỏ hộp: N/A (N/A gsm)',1),(10071,10017,'- Khay định hình: Carton lạnh (N/A gsm)',1),(10072,10017,'- Cốt khay định hình: N/A N/A',1),(10073,10019,'Công nghệ in: In Offset',1),(10074,10019,' Kiểu in: N/A',1),(10075,10020,' UV Toàn phần: Toàn bộ hoạt tiết trên mặt hộp',1),(10076,10020,'Cán mờ',1),(10077,10018,' Dạng kết cấu: Âm dương nắp lửng',1),(10078,10017,'test2',1),(10079,10013,'Danh cho 18-20 nhân sự',1),(10080,10011,'45kg',1),(10081,10022,'Kỳ 1',1),(10082,10022,'Kỳ 2',1),(10083,10023,'Âm dương nắp lửng',1),(10084,10023,'Âm dương nắp lật',1),(10085,10023,'Nam châm nắp phẳng',1),(10086,10023,'Hai tầng',1),(10087,10023,'Hai cánh mở giữa',1),(10088,10024,'Giấy C150',1),(10089,10024,'Giấy Crat',1),(10090,10024,'Giấy Mỹ thuật',1),(10091,10024,'Giấy Op/Ford',1),(10092,10025,'12 bánh',1),(10093,10025,'24 bánh',1);
/*!40000 ALTER TABLE `attributed_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int DEFAULT '0',
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '0',
  `icon` varchar(255) DEFAULT '',
  `image` varchar(100) DEFAULT NULL,
  `order_no` int DEFAULT '0',
  `seo_title` varchar(255) DEFAULT '',
  `seo_description` varchar(255) DEFAULT '',
  `seo_content` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10009 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (10008,0,'React.Js',NULL,0,'',NULL,0,'','',NULL,'2024-11-13 05:05:31','2024-11-13 05:05:31');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `value` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_UNIQUE` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,'info_bank','{\"nameBank\":\"VIETCOMBANK\", \"number\":0123456789}'),(8,'1','https://flash-solution-eight.vercel.app/');
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_activities`
--

DROP TABLE IF EXISTS `customer_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_activities` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `user_id` int NOT NULL,
  `name` text NOT NULL,
  `note` text,
  `status` int NOT NULL DEFAULT '0',
  `due_date` timestamp NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=644 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_activities`
--

LOCK TABLES `customer_activities` WRITE;
/*!40000 ALTER TABLE `customer_activities` DISABLE KEYS */;
INSERT INTO `customer_activities` VALUES (590,24,2,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-07-27 10:27:31','2025-07-24 10:27:31','2025-07-24 10:27:31'),(591,24,2,'Gửi Báo giá',NULL,0,'2025-07-29 10:27:31','2025-07-24 10:27:31','2025-07-24 10:27:31'),(592,24,2,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-07-31 10:27:31','2025-07-24 10:27:31','2025-07-24 10:27:31'),(593,27,1638,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 09:59:59','2025-08-27 09:59:59','2025-08-27 09:59:59'),(594,27,1638,'Gửi Báo giá',NULL,0,'2025-09-01 09:59:59','2025-08-27 09:59:59','2025-08-27 09:59:59'),(595,27,1638,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 09:59:59','2025-08-27 09:59:59','2025-08-27 09:59:59'),(596,28,1639,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 10:00:53','2025-08-27 10:00:53','2025-08-27 10:00:53'),(597,28,1639,'Gửi Báo giá',NULL,0,'2025-09-01 10:00:53','2025-08-27 10:00:53','2025-08-27 10:00:53'),(598,28,1639,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 10:00:53','2025-08-27 10:00:53','2025-08-27 10:00:53'),(599,29,1638,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 10:01:36','2025-08-27 10:01:36','2025-08-27 10:01:36'),(600,29,1638,'Gửi Báo giá',NULL,0,'2025-09-01 10:01:36','2025-08-27 10:01:36','2025-08-27 10:01:36'),(601,29,1638,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 10:01:36','2025-08-27 10:01:36','2025-08-27 10:01:36'),(602,30,1639,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 10:02:18','2025-08-27 10:02:18','2025-08-27 10:02:18'),(603,30,1639,'Gửi Báo giá',NULL,0,'2025-09-01 10:02:18','2025-08-27 10:02:18','2025-08-27 10:02:18'),(604,30,1639,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 10:02:18','2025-08-27 10:02:18','2025-08-27 10:02:18'),(605,31,1638,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 10:03:14','2025-08-27 10:03:14','2025-08-27 10:03:14'),(606,31,1638,'Gửi Báo giá',NULL,0,'2025-09-01 10:03:14','2025-08-27 10:03:14','2025-08-27 10:03:14'),(607,31,1638,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 10:03:14','2025-08-27 10:03:14','2025-08-27 10:03:14'),(608,32,1644,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:36:20','2025-08-27 14:36:20','2025-08-27 14:36:20'),(609,32,1644,'Gửi Báo giá',NULL,0,'2025-09-01 14:36:20','2025-08-27 14:36:20','2025-08-27 14:36:20'),(610,32,1644,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:36:20','2025-08-27 14:36:20','2025-08-27 14:36:20'),(611,33,1644,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:37:08','2025-08-27 14:37:08','2025-08-27 14:37:08'),(612,33,1644,'Gửi Báo giá',NULL,0,'2025-09-01 14:37:08','2025-08-27 14:37:08','2025-08-27 14:37:08'),(613,33,1644,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:37:08','2025-08-27 14:37:08','2025-08-27 14:37:08'),(614,34,1644,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:37:48','2025-08-27 14:37:48','2025-08-27 14:37:48'),(615,34,1644,'Gửi Báo giá',NULL,0,'2025-09-01 14:37:48','2025-08-27 14:37:48','2025-08-27 14:37:48'),(616,34,1644,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:37:48','2025-08-27 14:37:48','2025-08-27 14:37:48'),(617,35,1643,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:38:39','2025-08-27 14:38:39','2025-08-27 14:38:39'),(618,35,1643,'Gửi Báo giá',NULL,0,'2025-09-01 14:38:39','2025-08-27 14:38:39','2025-08-27 14:38:39'),(619,35,1643,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:38:39','2025-08-27 14:38:39','2025-08-27 14:38:39'),(620,36,1644,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:39:41','2025-08-27 14:39:41','2025-08-27 14:39:41'),(621,36,1644,'Gửi Báo giá',NULL,0,'2025-09-01 14:39:41','2025-08-27 14:39:41','2025-08-27 14:39:41'),(622,36,1644,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:39:41','2025-08-27 14:39:41','2025-08-27 14:39:41'),(623,37,1644,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:40:19','2025-08-27 14:40:19','2025-08-27 14:40:19'),(624,37,1644,'Gửi Báo giá',NULL,0,'2025-09-01 14:40:19','2025-08-27 14:40:19','2025-08-27 14:40:19'),(625,37,1644,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:40:19','2025-08-27 14:40:19','2025-08-27 14:40:19'),(626,38,1644,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:41:14','2025-08-27 14:41:14','2025-08-27 14:41:14'),(627,38,1644,'Gửi Báo giá',NULL,0,'2025-09-01 14:41:14','2025-08-27 14:41:14','2025-08-27 14:41:14'),(628,38,1644,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:41:14','2025-08-27 14:41:14','2025-08-27 14:41:14'),(629,39,1638,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:41:51','2025-08-27 14:41:51','2025-08-27 14:41:51'),(630,39,1638,'Gửi Báo giá',NULL,0,'2025-09-01 14:41:51','2025-08-27 14:41:51','2025-08-27 14:41:51'),(631,39,1638,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:41:51','2025-08-27 14:41:51','2025-08-27 14:41:51'),(632,40,1638,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:42:42','2025-08-27 14:42:42','2025-08-27 14:42:42'),(633,40,1638,'Gửi Báo giá',NULL,0,'2025-09-01 14:42:42','2025-08-27 14:42:42','2025-08-27 14:42:42'),(634,40,1638,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:42:42','2025-08-27 14:42:42','2025-08-27 14:42:42'),(635,41,1639,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:43:14','2025-08-27 14:43:14','2025-08-27 14:43:14'),(636,41,1639,'Gửi Báo giá',NULL,0,'2025-09-01 14:43:14','2025-08-27 14:43:14','2025-08-27 14:43:14'),(637,41,1639,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:43:14','2025-08-27 14:43:14','2025-08-27 14:43:14'),(638,42,1638,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:43:37','2025-08-27 14:43:37','2025-08-27 14:43:37'),(639,42,1638,'Gửi Báo giá',NULL,0,'2025-09-01 14:43:37','2025-08-27 14:43:37','2025-08-27 14:43:37'),(640,42,1638,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:43:37','2025-08-27 14:43:37','2025-08-27 14:43:37'),(641,43,1638,'Tư vấn lại 3 ngày chưa Cơ Hộ',NULL,0,'2025-08-30 14:44:04','2025-08-27 14:44:04','2025-08-27 14:44:04'),(642,43,1638,'Gửi Báo giá',NULL,0,'2025-09-01 14:44:04','2025-08-27 14:44:04','2025-08-27 14:44:04'),(643,43,1638,'Gọi điện 7 ngày chưa ra đơn',NULL,0,'2025-09-03 14:44:04','2025-08-27 14:44:04','2025-08-27 14:44:04');
/*!40000 ALTER TABLE `customer_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_address`
--

DROP TABLE IF EXISTS `customer_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int unsigned DEFAULT '0',
  `receiver_name` varchar(100) DEFAULT '',
  `address` varchar(255) DEFAULT NULL,
  `ward_id` int unsigned DEFAULT '0',
  `province_id` int unsigned DEFAULT '0',
  `mobile_phone` varchar(20) DEFAULT NULL,
  `is_default` tinyint unsigned DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8213 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_address`
--

LOCK TABLES `customer_address` WRITE;
/*!40000 ALTER TABLE `customer_address` DISABLE KEYS */;
INSERT INTO `customer_address` VALUES (8212,24,'Hà Nam','123 Đường 5',218,1,'0985155445',1,'2025-08-25 21:33:13','2025-08-25 21:33:13');
/*!40000 ALTER TABLE `customer_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_contract`
--

DROP TABLE IF EXISTS `customer_contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_contract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(500) NOT NULL,
  `enterprise_id` bigint DEFAULT NULL,
  `order_code` varchar(100) NOT NULL,
  `in_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `enterprise_id` (`enterprise_id`),
  CONSTRAINT `customer_contract_ibfk_1` FOREIGN KEY (`enterprise_id`) REFERENCES `customer_enterprise` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_contract`
--

LOCK TABLES `customer_contract` WRITE;
/*!40000 ALTER TABLE `customer_contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_enterprise`
--

DROP TABLE IF EXISTS `customer_enterprise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_enterprise` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `total_fee` bigint DEFAULT '0',
  `contact_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `tax_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `director` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `ward_id` int unsigned DEFAULT '0',
  `province_id` int unsigned DEFAULT '0',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `mobile_phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_tax_code_mobile_phone` (`tax_code`,`mobile_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_enterprise`
--

LOCK TABLES `customer_enterprise` WRITE;
/*!40000 ALTER TABLE `customer_enterprise` DISABLE KEYS */;
INSERT INTO `customer_enterprise` VALUES (51,'CÔNG TY TNHH TRUNG TÂM DẠY THÊM NHD',NULL,'VÕ THỊ THU THANH','0973560918','VÕ THỊ THU THANH','Thôn Tân Hóa Nam',389,27,NULL,'0973560918','2025-07-21 23:07:07');
/*!40000 ALTER TABLE `customer_enterprise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_order`
--

DROP TABLE IF EXISTS `customer_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `data_id` bigint DEFAULT '0',
  `source` int DEFAULT NULL,
  `enterprise_id` bigint DEFAULT '0',
  `enterprise_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `order_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `total_not_vat` bigint DEFAULT '0',
  `customer_id` int unsigned DEFAULT '0',
  `customer_receiver_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `customer_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `customer_ward_id` int unsigned DEFAULT NULL,
  `customer_province_id` int unsigned DEFAULT NULL,
  `customer_mobile_phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `customer_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `customer_note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `subtotal` bigint unsigned DEFAULT '0',
  `price_off` bigint DEFAULT '0',
  `discount_info` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `voucher` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `shipping_cost` int unsigned DEFAULT '0',
  `shipping_real` int DEFAULT '0',
  `cod_cost` int unsigned DEFAULT '0',
  `transport_type_id` int DEFAULT NULL,
  `total` bigint unsigned DEFAULT '0',
  `vat` tinyint DEFAULT '0',
  `fee_import` int DEFAULT '0',
  `paid` bigint unsigned DEFAULT '0',
  `paid_status` int DEFAULT NULL,
  `shipping_status` enum('na','danggiao','dagiao','giaoloi') CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT 'na',
  `cancel_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `done_at` timestamp NULL DEFAULT NULL,
  `user_create_id` int unsigned DEFAULT '0',
  `user_create_username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `faulty` bit(1) DEFAULT b'0',
  `status` int unsigned DEFAULT '0',
  `type` enum('order','cohoi') CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT 'cohoi',
  `opportunity_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `classification` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `enterprise_id` (`enterprise_id`),
  KEY `order_code_index` (`code`),
  KEY `user_id_index` (`user_create_id`),
  KEY `ship_idx` (`shipping_status`),
  KEY `phone_idx` (`customer_mobile_phone`),
  KEY `data_id_idx` (`data_id`),
  KEY `customer_id_idx` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34013 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_order`
--

LOCK TABLES `customer_order` WRITE;
/*!40000 ALTER TABLE `customer_order` DISABLE KEYS */;
INSERT INTO `customer_order` VALUES (34002,33,5,NULL,NULL,NULL,'OFBG2625OWC',NULL,22,'Hà NAm',NULL,NULL,NULL,'0974260540','manvtk32@gmail.com',NULL,720000,0,NULL,NULL,NULL,NULL,NULL,NULL,720000,0,NULL,0,0,NULL,NULL,'2025-07-18 19:16:17','2025-07-18 19:16:17',NULL,2,'admin',NULL,1,'cohoi','2025-07-18 19:16:17',2),(34003,33,5,51,'CÔNG TY TNHH TRUNG TÂM DẠY THÊM NHD',NULL,'OPJV2625QTK',NULL,22,'Hà NAm',NULL,NULL,NULL,'0974260540','manvtk32@gmail.com',NULL,2800000,0,NULL,NULL,30000,NULL,NULL,NULL,3054000,8,NULL,2054000,0,NULL,NULL,'2025-07-19 09:34:27','2025-07-21 08:57:07',NULL,2,'admin',NULL,1,'order','2025-07-18 19:20:27',2),(34004,34,1,NULL,NULL,NULL,'OWLD2725IWD',NULL,23,'Pép DiaLa','CT07',216,1,'098545555',NULL,NULL,950000,0,NULL,NULL,20000,NULL,NULL,NULL,1046000,8,NULL,200000,0,NULL,NULL,'2025-07-20 07:31:48','2025-08-09 23:46:39',NULL,2,'Administrator',NULL,1,'order','2025-07-20 07:21:50',2),(34005,34,1,NULL,NULL,NULL,'OCFY2925YUX',NULL,23,'Pép DiaLa',NULL,NULL,NULL,'098545555',NULL,NULL,5600000,0,NULL,NULL,NULL,NULL,NULL,NULL,5600000,0,NULL,0,0,NULL,NULL,'2025-07-21 23:11:40','2025-07-22 05:56:30',NULL,2,'Administrator',NULL,2,'cohoi','2025-07-21 23:11:40',2),(34006,33,5,NULL,NULL,NULL,'OQMA3025SET',NULL,22,'Hà NAm',NULL,NULL,NULL,'0974260540','manvtk32@gmail.com',NULL,2900000,0,NULL,NULL,NULL,NULL,NULL,NULL,2900000,0,NULL,0,0,NULL,NULL,'2025-07-23 04:08:46','2025-07-23 04:08:46',NULL,2,'Administrator',NULL,1,'cohoi','2025-07-23 04:08:46',2),(34007,34,1,NULL,NULL,NULL,'OCSN2025UBD',NULL,23,'Pép DiaLa',NULL,NULL,NULL,'098545555',NULL,NULL,1900000,0,NULL,NULL,NULL,NULL,NULL,NULL,1900000,0,NULL,1000000,0,NULL,NULL,'2025-08-12 03:13:24','2025-08-12 03:13:24',NULL,2,'Administrator',NULL,1,'order','2025-08-12 03:12:33',2),(34008,40,11,NULL,NULL,NULL,'OFBV3525UGJ',NULL,31,'Công ty TNHH Ngũ cốc Trường Thọ',NULL,NULL,NULL,'0913601308',NULL,NULL,3000000,0,NULL,NULL,NULL,NULL,NULL,NULL,3000000,0,NULL,2000000,1,NULL,NULL,'2025-08-27 10:07:07','2025-08-27 05:07:48',NULL,67,'LongHuu',NULL,1,'order','2025-08-27 10:04:21',NULL),(34009,39,11,NULL,NULL,NULL,'OGED3525JPP',NULL,30,'Hộ kinh doanh Lê Đăng Tuấn',NULL,NULL,NULL,'0915626539',NULL,NULL,10000000,0,NULL,NULL,NULL,NULL,NULL,NULL,10000000,0,NULL,5000000,0,NULL,NULL,'2025-08-27 10:09:46','2025-08-27 10:09:46',NULL,67,'LongHuu',NULL,1,'order','2025-08-27 10:08:42',NULL),(34010,38,3,NULL,NULL,NULL,'OAZT3525KHR',NULL,29,'Công ty TNHH MTV Tân Nguyên',NULL,NULL,NULL,'0935775665',NULL,NULL,200000000,0,NULL,NULL,NULL,NULL,NULL,NULL,200000000,0,NULL,20000000,0,NULL,NULL,'2025-08-27 10:23:07','2025-08-27 10:23:07',NULL,67,'LongHuu',NULL,1,'order','2025-08-27 10:22:45',NULL),(34011,52,1,NULL,NULL,NULL,'ORHX3525TDG',NULL,43,'Thu Thao Thu Bui','Xuân Quỳnh Trung Hòa',50,24,'0979046332',NULL,NULL,49000000,0,NULL,NULL,NULL,NULL,NULL,NULL,49000000,0,NULL,10000000,0,NULL,NULL,'2025-08-27 15:47:00','2025-08-27 15:48:46',NULL,1638,'ThanhTrung',NULL,1,'order','2025-08-27 15:46:35',NULL),(34012,51,4,NULL,NULL,NULL,'OGFW3625HTF',NULL,42,'Chị Uyên','54 Trương Công Giai, Cầu Giấy, Hà Nội, Việt Nam  ',50,24,'0976265571',NULL,NULL,120000000,0,NULL,NULL,NULL,NULL,NULL,NULL,120000000,0,NULL,100000000,0,NULL,NULL,'2025-08-28 02:40:06','2025-08-28 02:42:25',NULL,67,'LongHuu',NULL,1,'order','2025-08-28 02:37:31',NULL);
/*!40000 ALTER TABLE `customer_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_order_detail`
--

DROP TABLE IF EXISTS `customer_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_order_detail` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `customer_order_id` bigint DEFAULT '0',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `warranty_period` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sku_id` int DEFAULT '0',
  `sku_info` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `price` int DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '0',
  `price_off` int DEFAULT '0',
  `total` int DEFAULT '0',
  `status` int DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `customer_note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `day_quote` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_order_id` (`customer_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34078 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_order_detail`
--

LOCK TABLES `customer_order_detail` WRITE;
/*!40000 ALTER TABLE `customer_order_detail` DISABLE KEYS */;
INSERT INTO `customer_order_detail` VALUES (34063,'OFBG2625OWC-1',34002,'Bán lẻ',774,'Hộp Mềm','(Chưa có)',10198,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10341,\"text\":\"Tối giản B+\"}]}]',90000,8,0,720000,1,'2025-07-18 19:16:17','2025-07-18 19:16:17',NULL,NULL),(34064,'OPJV2625QTK-1',34003,'Bán lẻ',774,'Hộp Mềm','6 Tháng',10198,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10341,\"text\":\"Tối giản B+\"}]}]',9000,100,0,900000,3,'2025-07-18 19:20:27','2025-07-22 09:43:43',NULL,NULL),(34066,'OPJV2625QTK-2',34003,'Bán lẻ',776,'Hộp carton lạnh','6 Tháng',10208,'[{\"text\":\"Dạng kết cấu\",\"values\":[{\"id\":10382,\"text\":\"Âm dương nắp lửng\"},{\"id\":10383,\"text\":\"Âm dương nắp lật\"},{\"id\":10384,\"text\":\"Nam châm nắp phẳng\"}]},{\"text\":\"Bồi vỏ\",\"values\":[{\"id\":10387,\"text\":\"Giấy C150\"},{\"id\":10389,\"text\":\"Giấy Mỹ thuật\"}]}]',200000,10,100000,1900000,4,'2025-07-19 05:58:18','2025-07-22 09:44:34','',NULL),(34067,'OWLD2725IWD-1',34004,'Bán lẻ',774,'Hộp Mềm','6 Tháng',10198,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10341,\"text\":\"Tối giản B+\"}]}]',100000,10,50000,950000,5,'2025-07-20 07:21:50','2025-07-23 04:58:07','',NULL),(34068,'OCFY2925YUX-1',34005,'Bán lẻ',774,'Hộp Mềm','(Chưa có)',10198,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10341,\"text\":\"Tối giản B+\"}]}]',500000,10,0,5000000,1,'2025-07-21 23:11:40','2025-07-21 23:11:40','',NULL),(34069,'OCFY2925YUX-2',34005,'Bán lẻ',776,'Hộp carton lạnh','(Chưa có)',10208,'[{\"text\":\"Dạng kết cấu\",\"values\":[{\"id\":10385,\"text\":\"Hai tầng\"},{\"id\":10383,\"text\":\"Âm dương nắp lật\"}]},{\"text\":\"Bồi vỏ\",\"values\":[{\"id\":10389,\"text\":\"Giấy Mỹ thuật\"},{\"id\":10388,\"text\":\"Giấy Crat\"}]}]',120000,5,0,600000,2,'2025-07-21 23:11:40','2025-07-22 05:54:54','',NULL),(34070,'OQMA3025SET-1',34006,'Bán lẻ',776,'Hộp carton lạnh','(Chưa có)',10208,'[{\"text\":\"Dạng kết cấu\",\"values\":[{\"id\":10385,\"text\":\"Hai tầng\"},{\"id\":10383,\"text\":\"Âm dương nắp lật\"}]},{\"text\":\"Bồi vỏ\",\"values\":[{\"id\":10389,\"text\":\"Giấy Mỹ thuật\"},{\"id\":10388,\"text\":\"Giấy Crat\"}]}]',120000,15,0,1800000,1,'2025-07-23 04:08:46','2025-07-23 04:08:46','',NULL),(34071,'OQMA3025SET-2',34006,'Bán lẻ',776,'Hộp carton lạnh','(Chưa có)',10209,'[{\"text\":\"Dạng kết cấu\",\"values\":[{\"id\":10395,\"text\":\"Hai cánh mở giữa\"}]},{\"text\":\"Bồi vỏ\",\"values\":[{\"id\":10399,\"text\":\"Giấy Op/Ford\"}]}]',55000,20,0,1100000,1,'2025-07-23 04:08:46','2025-07-23 04:08:46','',NULL),(34072,'OCSN2025UBD-1',34007,'Bán lẻ',773,'Hộp yến sào Nam Bắc','(Chưa có)',10205,'[{\"text\":\"Thuôc tính chung\",\"values\":[{\"id\":10358,\"text\":\" Kích thước: 360x360x95\"}]},{\"text\":\"Vật liệu\",\"values\":[{\"id\":10360,\"text\":\"- Cốt vỏ ngoài: Carton thường 2,5 mm\"}]},{\"text\":\"In ấn\",\"values\":[{\"id\":10364,\"text\":\"Công nghệ in: In Offset\"}]},{\"text\":\"Gia công:\",\"values\":[{\"id\":10366,\"text\":\" UV Toàn phần: Toàn bộ hoạt tiết trên mặt hộp\"},{\"id\":10367,\"text\":\"Cán mờ\"}]}]',100000,20,100000,1900000,1,'2025-08-12 03:12:33','2025-08-12 03:12:33','',NULL),(34073,'OFBV3525UGJ-1',34008,'',774,'Hộp Mềm','(Chưa có)',10198,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10341,\"text\":\"Tối giản B+\"}]}]',100000,30,0,0,1,'2025-08-27 10:04:21','2025-08-27 05:05:37','',NULL),(34074,'OGED3525JPP-1',34009,'Bán lẻ',771,'Hộp cứng','(Chưa có)',10200,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10344,\"text\":\"Sang trọng\"}]},{\"text\":\"Kích thước\",\"values\":[{\"id\":10345,\"text\":\"30x55x30\"}]}]',1000000,10,0,10000000,6,'2025-08-27 10:08:42','2025-08-27 05:17:01','',NULL),(34075,'OAZT3525KHR-1',34010,'Bán lẻ',771,'Hộp cứng','(Chưa có)',10200,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10344,\"text\":\"Sang trọng\"}]},{\"text\":\"Kích thước\",\"values\":[{\"id\":10345,\"text\":\"30x55x30\"}]}]',200000,1000,0,200000000,2,'2025-08-27 10:22:45','2025-08-27 10:25:18','',NULL),(34076,'ORHX3525TDG-1',34011,'Bán lẻ',771,'Hộp cứng','(Chưa có)',10200,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10344,\"text\":\"Sang trọng\"}]},{\"text\":\"Kích thước\",\"values\":[{\"id\":10345,\"text\":\"30x55x30\"}]}]',245000,200,0,49000000,1,'2025-08-27 15:46:35','2025-08-27 15:46:35','',NULL),(34077,'OGFW3625HTF-1',34012,'Sản xuất',771,'Hộp cứng','(Chưa có)',10200,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10344,\"text\":\"Sang trọng\"}]},{\"text\":\"Kích thước\",\"values\":[{\"id\":10345,\"text\":\"30x55x30\"}]}]',300000,400,0,120000000,1,'2025-08-28 02:37:31','2025-08-28 02:37:31','',NULL);
/*!40000 ALTER TABLE `customer_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_order_payment`
--

DROP TABLE IF EXISTS `customer_order_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_order_payment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `method` varchar(50) DEFAULT NULL,
  `amount` bigint unsigned DEFAULT '0',
  `sso_id` varchar(100) NOT NULL,
  `is_confirm` tinyint unsigned DEFAULT '0',
  `content` text,
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `confirm_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pay_order_code_index` (`code`),
  KEY `pay_sale_index` (`sso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_order_payment`
--

LOCK TABLES `customer_order_payment` WRITE;
/*!40000 ALTER TABLE `customer_order_payment` DISABLE KEYS */;
INSERT INTO `customer_order_payment` VALUES (19,'OYOO1025XQC','tienmat',20000000,'1',1,'thanh toán trước','2025-05-05 04:40:28','2025-05-05 04:40:28'),(20,'OSEA1325ODL','tienmat',2000000,'1',1,'thanh toán trước','2025-05-07 21:51:50','2025-05-07 21:51:50'),(21,'OGDA1125JJA','tienmat',60000000,'1',1,'thanh toán trước','2025-05-13 21:59:14','2025-05-13 21:59:14'),(22,'OORD1925ELC','tienmat',4000000,'1',1,'thanh toán trước','2025-05-13 21:59:59','2025-05-13 21:59:59'),(23,'OOKM2425XQF','tienmat',6000000,'1',1,'thanh toán trước','2025-05-19 11:05:03','2025-05-19 11:05:03'),(24,'OPJV2625QTK','tienmat',2000000,'admin',1,'Đặt cọc','2025-07-19 08:58:35','2025-07-19 08:48:13'),(25,'OPJV2625QTK','tienmat',54000,'admin',1,'Đặt cọc','2025-07-19 09:34:27','2025-07-19 09:34:09'),(26,'OWLD2725IWD','momo',200000,'Administrator',1,'Đặt cọc','2025-07-20 07:31:48','2025-07-20 07:31:46'),(27,'OCSN2025UBD','vnpay',1000000,'Administrator',1,'Đặt cọc','2025-08-12 03:13:24','2025-08-11 12:00:00'),(28,'OFBV3525UGJ','tienmat',2000000,'LongHuu',1,'Đặt cọc','2025-08-27 10:07:07','2025-08-27 17:00:00'),(29,'OGED3525JPP','tienmat',5000000,'LongHuu',1,'Đặt cọc','2025-08-27 10:09:46','2025-08-27 17:00:00'),(30,'OAZT3525KHR','tienmat',20000000,'LongHuu',1,'Đặt cọc','2025-08-27 10:23:07','2025-08-28 17:00:00'),(31,'ORHX3525TDG','tienmat',10000000,'ThanhTrung',1,'Đặt cọc','2025-08-27 15:47:00','2025-08-26 17:00:00'),(32,'OGFW3625HTF','tienmat',50000000,'LongHuu',1,'Đặt cọc','2025-08-28 02:38:24','2025-08-27 17:00:00'),(33,'OGFW3625HTF','tienmat',50000000,'LongHuu',1,'Đặt cọc','2025-08-28 02:40:06','2025-08-27 17:00:00');
/*!40000 ALTER TABLE `customer_order_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_order_status`
--

DROP TABLE IF EXISTS `customer_order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_order_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `color` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'black',
  `order` int DEFAULT '0',
  `del_flag` tinyint DEFAULT '0',
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_order_status`
--

LOCK TABLES `customer_order_status` WRITE;
/*!40000 ALTER TABLE `customer_order_status` DISABLE KEYS */;
INSERT INTO `customer_order_status` VALUES (1,'Đơn mới','#36e9ddc9',1,0,1),(2,'Sản xuất','#113cca',2,0,1),(3,'Hoàn thành','#bf7373',5,0,1),(4,'ĐÓng gói','#e9a71a',3,0,1),(5,'Đang Giao','green',4,0,1),(6,'Đã giao','#812c94',4,0,1),(12,'Hủy đơn','#fa1111',8,0,1);
/*!40000 ALTER TABLE `customer_order_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_personal`
--

DROP TABLE IF EXISTS `customer_personal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_personal` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `id_card` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `sale_id` varchar(100) DEFAULT '0',
  `gender` varchar(20) DEFAULT 'other',
  `source_id` int DEFAULT NULL,
  `level` tinyint(1) DEFAULT '0',
  `facebook_id` varchar(255) DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `province_id` int unsigned DEFAULT NULL,
  `ward_id` int unsigned DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_id` int DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `is_trust_email` int DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token_confirm` varchar(200) DEFAULT NULL,
  `status` int unsigned DEFAULT '0',
  `date_of_birth` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `diem_danh_gia` decimal(8,2) DEFAULT '0.00',
  `num_of_order` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mobile_index` (`mobile`),
  KEY `saleId_index` (`sale_id`),
  KEY `token_idx` (`token_confirm`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_personal`
--

LOCK TABLES `customer_personal` WRITE;
/*!40000 ALTER TABLE `customer_personal` DISABLE KEYS */;
INSERT INTO `customer_personal` VALUES (22,NULL,'2','other',1,NULL,NULL,'Hà NAm',NULL,NULL,'Hà Nội',NULL,NULL,NULL,'manvtk32@gmail.com',NULL,'0974260540',NULL,NULL,NULL,NULL,'2025-07-18 03:14:46','2025-07-18 03:14:46',NULL,0),(23,NULL,'67','other',1,NULL,NULL,'Pép DiaLa',NULL,NULL,'Hà Nội',NULL,NULL,NULL,NULL,NULL,'098545555',NULL,NULL,NULL,NULL,'2025-07-20 07:09:50','2025-07-20 10:07:55',NULL,0),(24,NULL,'2','other',2,NULL,NULL,'Bal Fing',1,218,'123 Đường 5',NULL,NULL,NULL,'hns-01@gmail.com',NULL,'0984554774',NULL,NULL,NULL,'1990-08-25 19:00:00','2025-07-24 10:27:31','2025-08-25 22:08:15',NULL,0),(27,NULL,'1638','other',1,NULL,NULL,'Đình Sang',NULL,NULL,'Hà Nội',NULL,NULL,NULL,NULL,NULL,'0939905789',NULL,NULL,NULL,NULL,'2025-08-27 09:59:59','2025-08-27 09:59:59',NULL,0),(28,NULL,'1639','other',1,NULL,NULL,'Anh Họ Trương',NULL,NULL,'Hồ Chí Minh',NULL,NULL,NULL,NULL,NULL,'0921235555',NULL,NULL,NULL,NULL,'2025-08-27 10:00:53','2025-08-27 10:00:53',NULL,0),(29,NULL,'1638','other',1,NULL,NULL,'Công ty TNHH MTV Tân Nguyên',NULL,NULL,'Đắk Lắk',NULL,NULL,NULL,NULL,NULL,'0935775665',NULL,NULL,NULL,NULL,'2025-08-27 10:01:36','2025-08-27 10:01:36',NULL,0),(30,NULL,'1639','other',1,NULL,NULL,'Hộ kinh doanh Lê Đăng Tuấn',NULL,NULL,'Bắc Ninh',NULL,NULL,NULL,NULL,NULL,'0915626539',NULL,NULL,NULL,NULL,'2025-08-27 10:02:18','2025-08-27 10:02:18',NULL,0),(31,NULL,'1638','other',1,NULL,NULL,'Công ty TNHH Ngũ cốc Trường Thọ',NULL,NULL,'Hà Nội',NULL,NULL,NULL,NULL,NULL,'0913601308',NULL,NULL,NULL,NULL,'2025-08-27 10:03:14','2025-08-27 10:03:14',NULL,0),(32,NULL,'1644','other',1,NULL,NULL,'Phương Trần',NULL,NULL,'Hà Nội',NULL,NULL,NULL,NULL,NULL,'0364560000',NULL,NULL,NULL,NULL,'2025-08-27 14:36:20','2025-08-27 14:36:20',NULL,0),(33,NULL,'1644','other',1,NULL,NULL,'Thái Hằng Tấm',NULL,NULL,'Đà Nẵng',NULL,NULL,NULL,NULL,NULL,'0973401051',NULL,NULL,NULL,NULL,'2025-08-27 14:37:08','2025-08-27 14:37:08',NULL,0),(34,NULL,'1644','other',1,NULL,NULL,'Bich Phuong',NULL,NULL,'Hải Phòng',NULL,NULL,NULL,NULL,NULL,'0983492667',NULL,NULL,NULL,NULL,'2025-08-27 14:37:48','2025-08-27 14:37:48',NULL,0),(35,NULL,'1643','other',1,NULL,NULL,'Công ty TNHH Dược liệu Việt Kết',NULL,NULL,'Hồ Chí Minh',NULL,NULL,NULL,NULL,NULL,'0936557750',NULL,NULL,NULL,NULL,'2025-08-27 14:38:39','2025-08-27 14:38:39',NULL,0),(36,NULL,'1644','other',1,NULL,NULL,'Khương Lưu',NULL,NULL,'Hồ Chí Minh',NULL,NULL,NULL,NULL,NULL,'0939495119',NULL,NULL,NULL,NULL,'2025-08-27 14:39:41','2025-08-27 14:39:41',NULL,0),(37,NULL,'1644','other',1,NULL,NULL,'MyHanh Foods',NULL,NULL,'Hồ Chí Minh',NULL,NULL,NULL,NULL,NULL,'0968790097',NULL,NULL,NULL,NULL,'2025-08-27 14:40:19','2025-08-27 14:40:19',NULL,0),(38,NULL,'1644','other',1,NULL,NULL,'Tiênn Tiênn',NULL,NULL,'Đồng Nai',NULL,NULL,NULL,NULL,NULL,'0963061490',NULL,NULL,NULL,NULL,'2025-08-27 14:41:14','2025-08-27 14:41:14',NULL,0),(39,NULL,'1638','other',1,NULL,NULL,'Mai Như Ý',NULL,NULL,'Hưng Yên',NULL,NULL,NULL,NULL,NULL,'0917978991',NULL,NULL,NULL,NULL,'2025-08-27 14:41:51','2025-08-27 14:41:51',NULL,0),(40,NULL,'1638','other',1,NULL,NULL,'Nguyễn Mai Phương Uyên',NULL,NULL,'Hà Nội',NULL,NULL,NULL,NULL,NULL,'0933069505',NULL,NULL,NULL,NULL,'2025-08-27 14:42:42','2025-08-27 14:42:42',NULL,0),(41,NULL,'1639','other',1,NULL,NULL,'Sala Factory',NULL,NULL,'Hà Nội',NULL,NULL,NULL,NULL,NULL,'0384609470',NULL,NULL,NULL,NULL,'2025-08-27 14:43:14','2025-08-27 14:43:14',NULL,0),(42,NULL,'1638','other',1,NULL,NULL,'Chị Uyên',NULL,NULL,'Hà Nội',NULL,NULL,NULL,NULL,NULL,'0976265571',NULL,NULL,NULL,NULL,'2025-08-27 14:43:37','2025-08-27 14:43:37',NULL,0),(43,NULL,'1638','other',1,NULL,NULL,'Thu Thao Thu Bui',NULL,NULL,'Hải Phòng',NULL,NULL,NULL,NULL,NULL,'0979046332',NULL,NULL,NULL,NULL,'2025-08-27 14:44:04','2025-08-27 14:44:04',NULL,0);
/*!40000 ALTER TABLE `customer_personal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_tags`
--

DROP TABLE IF EXISTS `customer_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_tags` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `tag` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_tags`
--

LOCK TABLES `customer_tags` WRITE;
/*!40000 ALTER TABLE `customer_tags` DISABLE KEYS */;
INSERT INTO `customer_tags` VALUES (14,24,'CRM'),(15,24,'Phần mềm xây dựng'),(16,24,'30/08 Nhắc lại');
/*!40000 ALTER TABLE `customer_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data`
--

DROP TABLE IF EXISTS `data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `service_id` int DEFAULT '0',
  `level` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `staff` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `province_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `source` tinyint(1) DEFAULT '0',
  `customer_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `customer_mobile` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `customer_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `customer_facebook` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `tags` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `sale_id` int DEFAULT '0',
  `note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `assign_to` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  `from_department` tinyint(1) DEFAULT NULL,
  `is_order` tinyint DEFAULT '0',
  `product_id` int DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `pre_sale_call` int DEFAULT NULL,
  `after_sale_call` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`,`in_time`),
  KEY `data_sale_id_index` (`sale_id`),
  KEY `data_assign_to_index` (`assign_to`),
  KEY `status_index` (`status`),
  KEY `department_index` (`from_department`),
  KEY `customer_mobile_idx` (`customer_mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci
/*!50100 PARTITION BY RANGE (unix_timestamp(`in_time`))
(PARTITION p2020 VALUES LESS THAN (1609347599) ENGINE = InnoDB,
 PARTITION quimot2021 VALUES LESS THAN (1617209999) ENGINE = InnoDB,
 PARTITION quihai2021 VALUES LESS THAN (1625072399) ENGINE = InnoDB,
 PARTITION quiba2021 VALUES LESS THAN (1633021199) ENGINE = InnoDB,
 PARTITION quitu2021 VALUES LESS THAN (1640969999) ENGINE = InnoDB,
 PARTITION quimot2022 VALUES LESS THAN (1648745999) ENGINE = InnoDB,
 PARTITION quihai2022 VALUES LESS THAN (1656608399) ENGINE = InnoDB,
 PARTITION pfuture VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data`
--

LOCK TABLES `data` WRITE;
/*!40000 ALTER TABLE `data` DISABLE KEYS */;
INSERT INTO `data` VALUES (32,1,NULL,'Administrator','Hà Nội',5,'Hà NAm','0974260540','manvtk32@gmail.com',NULL,NULL,2,NULL,'admin','2025-07-18 03:14:46','2025-07-18 03:14:46',7,0,NULL,776,'Hộp carton lạnh',NULL,NULL),(33,1,NULL,'Administrator','Hà Nội',5,'Hà NAm','0974260540','manvtk32@gmail.com',NULL,NULL,2,NULL,'admin','2025-07-18 04:06:15','2025-07-18 04:06:15',7,2,NULL,776,'Hộp carton lạnh',NULL,NULL),(34,1,NULL,'Administrator','Hà Nội',1,'Pép DiaLa','098545555',NULL,NULL,NULL,67,NULL,'LongHuu','2025-07-20 07:09:50','2025-07-21 23:11:40',7,2,NULL,774,'Hộp Mềm',NULL,NULL),(35,2,NULL,'Administrator','Tuyên Quang',1,'Bal Fing','0984554774','',NULL,NULL,67,NULL,'LongHuu','2025-07-24 10:27:31','2025-08-16 23:00:43',0,0,NULL,774,'Hộp Mềm',1,NULL),(36,1,NULL,'Hữu Long','Hà Nội',4,'Đình Sang','0939905789',NULL,NULL,NULL,1638,NULL,'ThanhTrung','2025-08-27 09:59:59','2025-08-27 09:59:59',0,1,NULL,774,'Hộp Mềm',NULL,NULL),(37,1,NULL,'Hữu Long','Hồ Chí Minh',7,'Anh Họ Trương','0921235555',NULL,NULL,NULL,1639,NULL,'HungDB','2025-08-27 10:00:53','2025-08-27 10:00:53',0,1,NULL,771,'Hộp cứng',NULL,NULL),(38,1,NULL,'Hữu Long','Đắk Lắk',3,'Công ty TNHH MTV Tân Nguyên','0935775665',NULL,NULL,NULL,1638,NULL,'ThanhTrung','2025-08-27 10:01:36','2025-08-27 10:22:45',7,1,NULL,771,'Hộp cứng',NULL,NULL),(39,1,NULL,'Hữu Long','Bắc Ninh',11,'Hộ kinh doanh Lê Đăng Tuấn','0915626539',NULL,NULL,NULL,1639,NULL,'HungDB','2025-08-27 10:02:18','2025-08-27 10:08:42',7,1,NULL,771,'Hộp cứng',NULL,NULL),(40,1,NULL,'Hữu Long','Hà Nội',11,'Công ty TNHH Ngũ cốc Trường Thọ','0913601308',NULL,NULL,NULL,1638,NULL,'ThanhTrung','2025-08-27 10:03:14','2025-08-27 10:04:21',7,1,NULL,774,'Hộp Mềm',NULL,NULL),(41,1,NULL,'Hữu Long','Hà Nội',2,'Phương Trần','0364560000',NULL,NULL,NULL,1644,NULL,'MoNT','2025-08-27 14:36:19','2025-08-27 14:36:19',0,1,NULL,771,'Hộp cứng',NULL,NULL),(42,1,NULL,'Hữu Long','Đà Nẵng',2,'Thái Hằng Tấm','0973401051',NULL,NULL,NULL,1644,NULL,'MoNT','2025-08-27 14:37:08','2025-08-27 14:37:08',0,1,NULL,771,'Hộp cứng',NULL,NULL),(43,1,NULL,'Hữu Long','Hải Phòng',1,'Bich Phuong','0983492667',NULL,NULL,NULL,1644,NULL,'MoNT','2025-08-27 14:37:48','2025-08-27 14:37:48',0,1,NULL,774,'Hộp Mềm',NULL,NULL),(44,1,NULL,'Hữu Long','Hồ Chí Minh',1,'Công ty TNHH Dược liệu Việt Kết','0936557750',NULL,NULL,NULL,1643,NULL,'AgentA2A','2025-08-27 14:38:38','2025-08-27 14:38:38',0,1,NULL,776,'Hộp carton lạnh',NULL,NULL),(45,1,NULL,'Hữu Long','Hồ Chí Minh',1,'Khương Lưu','0939495119',NULL,NULL,NULL,1644,NULL,'MoNT','2025-08-27 14:39:41','2025-08-27 14:39:41',0,1,NULL,771,'Hộp cứng',NULL,NULL),(46,1,NULL,'Hữu Long','Hồ Chí Minh',4,'MyHanh Foods','0968790097',NULL,NULL,NULL,1644,NULL,'MoNT','2025-08-27 14:40:19','2025-08-27 14:40:19',0,1,NULL,771,'Hộp cứng',NULL,NULL),(47,1,NULL,'Hữu Long','Đồng Nai',3,'Tiênn Tiênn','0963061490',NULL,NULL,NULL,1644,NULL,'MoNT','2025-08-27 14:41:14','2025-08-27 14:41:14',0,1,NULL,771,'Hộp cứng',NULL,NULL),(48,1,NULL,'Hữu Long','Hưng Yên',4,'Mai Như Ý','0917978991',NULL,NULL,NULL,1638,NULL,'ThanhTrung','2025-08-27 14:41:51','2025-08-27 14:41:51',0,1,NULL,771,'Hộp cứng',NULL,NULL),(49,1,NULL,'Hữu Long','Hà Nội',3,'Nguyễn Mai Phương Uyên','0933069505',NULL,NULL,NULL,1638,NULL,'ThanhTrung','2025-08-27 14:42:42','2025-08-27 14:42:42',0,1,NULL,769,'Hộp Mềm',NULL,NULL),(50,1,NULL,'Hữu Long','Hà Nội',4,'Sala Factory','0384609470',NULL,NULL,NULL,1639,NULL,'HungDB','2025-08-27 14:43:14','2025-08-27 14:43:14',0,1,NULL,771,'Hộp cứng',NULL,NULL),(51,1,NULL,'Hữu Long','Hà Nội',4,'Chị Uyên','0976265571',NULL,NULL,NULL,1638,NULL,'ThanhTrung','2025-08-27 14:43:37','2025-08-28 02:37:31',7,1,NULL,774,'Hộp Mềm',NULL,NULL),(52,1,NULL,'Hữu Long','Hải Phòng',1,'Thu Thao Thu Bui','0979046332',NULL,NULL,NULL,1638,NULL,'ThanhTrung','2025-08-27 14:44:04','2025-08-27 15:46:35',7,1,NULL,771,'Hộp cứng',NULL,NULL);
/*!40000 ALTER TABLE `data` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `delete_from_data_care_after_data` AFTER DELETE ON `data` FOR EACH ROW BEGIN
    DELETE FROM `data_care` WHERE object_id = OLD.id and object_type = "lead";
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `data_care`
--

DROP TABLE IF EXISTS `data_care`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_care` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '',
  `customer_id` int unsigned DEFAULT '0',
  `object_id` int unsigned DEFAULT '0',
  `object_type` varchar(100) NOT NULL,
  `cause` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '',
  `information` text NOT NULL,
  `action` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `priority` varchar(100) NOT NULL,
  `status` tinyint DEFAULT '0',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `data_id_Idx` (`object_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_care`
--

LOCK TABLES `data_care` WRITE;
/*!40000 ALTER TABLE `data_care` DISABLE KEYS */;
INSERT INTO `data_care` VALUES (9,'Administrator',24,35,'lead','Chờ duyệt','{\"issues\":[\"product\"],\"rating\":9,\"satisfactionPercent\":90,\"newFeatures\":\"\",\"supportRequest\":\"\"}','Khách đợi 2 ngày chưa liên lạc lại, cần liên hệ trong hôm nay.','cao',0,'2025-08-16 23:58:44','2025-08-16 23:58:44'),(10,'Administrator',22,34002,'cohoi','Đang trao đổi ','{\"issues\":[\"product\",\"service\"],\"rating\":9,\"satisfactionPercent\":95,\"newFeatures\":\"\",\"supportRequest\":\"\"}','Đang trình phương án, sự kiến tuần sau sẽ chốt, yêu cầu sale tương tác thêm','cao',0,'2025-08-17 05:22:07','2025-08-17 05:22:07'),(11,'Administrator',23,34007,'order','Tư vấn lại','{\"issues\":[],\"rating\":9,\"satisfactionPercent\":80,\"newFeatures\":\"\",\"supportRequest\":\"\"}','Sale chưa tư vấn đầy đủ thông tin, đơn hàng chậm tiến độ','thap',0,'2025-08-17 05:52:47','2025-08-17 05:52:47');
/*!40000 ALTER TABLE `data_care` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_collection`
--

DROP TABLE IF EXISTS `data_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_collection` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `profile_id` varchar(50) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `full_name` varchar(512) DEFAULT NULL,
  `profile_link` varchar(512) DEFAULT NULL,
  `service` varchar(512) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  `image_src` varchar(512) DEFAULT NULL,
  `group_id` varchar(500) DEFAULT NULL,
  `group_name` varchar(512) DEFAULT NULL,
  `profile_type` varchar(512) DEFAULT NULL,
  `status` int DEFAULT '0',
  `mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `receive_time_new` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `channel` varchar(100) DEFAULT NULL,
  `product` varchar(255) DEFAULT NULL,
  `note` text,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `p_idx` (`profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
/*!50100 PARTITION BY HASH (`id`)
PARTITIONS 100 */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_collection`
--

LOCK TABLES `data_collection` WRITE;
/*!40000 ALTER TABLE `data_collection` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_media`
--

DROP TABLE IF EXISTS `data_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_media` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_id` int DEFAULT '0',
  `session_id` int DEFAULT '0',
  `file` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `customer_mobile` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dataIdIdx` (`data_id`),
  KEY `sessionIdIdx` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20613 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='store customer files';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_media`
--

LOCK TABLES `data_media` WRITE;
/*!40000 ALTER TABLE `data_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_owner`
--

DROP TABLE IF EXISTS `data_owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_owner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_mobile` varchar(20) NOT NULL,
  `sale_id` int DEFAULT '0',
  `department_id` int DEFAULT '0',
  `sale_name` varchar(255) DEFAULT '',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `data_owner_mobile` (`customer_mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=40636 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_owner`
--

LOCK TABLES `data_owner` WRITE;
/*!40000 ALTER TABLE `data_owner` DISABLE KEYS */;
INSERT INTO `data_owner` VALUES (40616,'0974260540',2,0,'admin','2025-07-18 03:14:46'),(40617,'098545555',67,NULL,'LongHuu','2025-07-20 07:09:50'),(40618,'0984554774',67,NULL,'LongHuu','2025-07-24 10:27:31'),(40619,'0939905789',1638,NULL,'ThanhTrung','2025-08-27 09:59:59'),(40620,'0921235555',1639,NULL,'HungDB','2025-08-27 10:00:53'),(40621,'0935775665',1638,NULL,'ThanhTrung','2025-08-27 10:01:36'),(40622,'0915626539',1639,NULL,'HungDB','2025-08-27 10:02:18'),(40623,'0913601308',1638,NULL,'ThanhTrung','2025-08-27 10:03:14'),(40624,'0364560000',1644,NULL,'MoNT','2025-08-27 14:36:19'),(40625,'0973401051',1644,NULL,'MoNT','2025-08-27 14:37:08'),(40626,'0983492667',1644,NULL,'MoNT','2025-08-27 14:37:48'),(40627,'0936557750',1643,NULL,'AgentA2A','2025-08-27 14:38:38'),(40628,'0939495119',1644,NULL,'MoNT','2025-08-27 14:39:41'),(40629,'0968790097',1644,NULL,'MoNT','2025-08-27 14:40:19'),(40630,'0963061490',1644,NULL,'MoNT','2025-08-27 14:41:14'),(40631,'0917978991',1638,NULL,'ThanhTrung','2025-08-27 14:41:51'),(40632,'0933069505',1638,NULL,'ThanhTrung','2025-08-27 14:42:42'),(40633,'0384609470',1639,NULL,'HungDB','2025-08-27 14:43:14'),(40634,'0976265571',1638,NULL,'ThanhTrung','2025-08-27 14:43:37'),(40635,'0979046332',1638,NULL,'ThanhTrung','2025-08-27 14:44:04');
/*!40000 ALTER TABLE `data_owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flast_note`
--

DROP TABLE IF EXISTS `flast_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flast_note` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `object_type` varchar(100) NOT NULL,
  `object_id` int NOT NULL,
  `data_type` varchar(100) NOT NULL DEFAULT 'text',
  `user_id` int NOT NULL,
  `user_note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '',
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=590 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flast_note`
--

LOCK TABLES `flast_note` WRITE;
/*!40000 ALTER TABLE `flast_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `flast_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flast_projects_list`
--

DROP TABLE IF EXISTS `flast_projects_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flast_projects_list` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` text,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `status` enum('Not Started','In Progress','Completed','On Hold') DEFAULT 'Not Started',
  `budget` decimal(15,2) DEFAULT '0.00',
  `priority` enum('Low','Medium','High') DEFAULT 'Medium',
  `department_id` int DEFAULT NULL,
  `manager_id` bigint NOT NULL,
  `members` json NOT NULL,
  `progress` int DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `manager_id` (`manager_id`),
  CONSTRAINT `flast_projects_list_ibfk_1` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flast_projects_list`
--

LOCK TABLES `flast_projects_list` WRITE;
/*!40000 ALTER TABLE `flast_projects_list` DISABLE KEYS */;
INSERT INTO `flast_projects_list` VALUES (4,'Chuyến đổi số công ty vận hành cảng ChuLai','Dự án kéo dài 2 năm, 30 module chức năng','2025-08-01','2025-08-31','In Progress',200000000.00,'High',1,1638,'[\"LongHuu\", \"Thanhtrung\", \"HungDB\", \"AgentA2A\"]',45,'2025-08-23 21:00:30','2025-08-25 09:52:16'),(5,'Website + CDP Unitika','Xây dựng Website và hệ thống CDP cho công ty Unitika','2025-07-01','2025-11-30','Completed',200000000.00,'Medium',3,1644,'[\"LongHuu\", \"ThanhTrung\", \"HungDB\", \"MoNT\"]',65,NULL,'2025-08-27 15:16:10');
/*!40000 ALTER TABLE `flast_projects_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flast_projects_task`
--

DROP TABLE IF EXISTS `flast_projects_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flast_projects_task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_identity` varchar(100) NOT NULL,
  `project_id` int NOT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text,
  `sso_id` varchar(100) NOT NULL,
  `status` enum('To Do','In Progress','Done','Cancelled') DEFAULT 'To Do',
  `priority` enum('Low','Medium','High') DEFAULT 'Medium',
  `color` varchar(100) DEFAULT '#1890ff',
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `progress` int DEFAULT '0',
  `user_created_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  KEY `user_created_id` (`user_created_id`),
  KEY `index_indentity` (`task_identity`),
  CONSTRAINT `flast_projects_task_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `flast_projects_list` (`id`) ON DELETE CASCADE,
  CONSTRAINT `flast_projects_task_ibfk_2` FOREIGN KEY (`user_created_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flast_projects_task`
--

LOCK TABLES `flast_projects_task` WRITE;
/*!40000 ALTER TABLE `flast_projects_task` DISABLE KEYS */;
INSERT INTO `flast_projects_task` VALUES (5,'1746032451289',4,'Triển khai Database cho dự án','Chuyển đổi số cảng Chu Lai','Thanhtrung','In Progress','Medium','#8c1c1c','2025-08-05 17:00:00','2025-08-05 23:30:00',51,67,'2025-08-24 00:29:01','2025-08-27 14:58:27'),(6,'1746032531834',4,'Code giao diện trang Lead',NULL,'LongHuu','In Progress','Medium','#158985','2025-08-06 10:00:00','2025-08-07 11:00:00',40,2,'2025-08-24 04:14:00','2025-08-24 06:27:13'),(10,'1756033628173',4,'Họp dự án Quản lý công việc','Dự án trọng điểm chuyển đổi số tỉnh','HungDB','To Do','Medium','#930c0c','2025-08-07 08:30:00','2025-08-07 11:30:00',42,2,'2025-08-24 06:07:08','2025-08-24 06:07:08'),(11,'1756033731350',4,'Hẹn khách hàng demo. Sử dụng Tailwind CSS (nếu bạn dùng Tailwind)','Demo dự án Chuyển đổi số văn phòng','LongHuu','In Progress','Medium','#1a1251','2025-08-12 09:00:00','2025-08-12 14:00:00',38,2,'2025-08-24 06:08:51','2025-08-24 06:34:37'),(16,'1756306635753',4,'Lập kế hoạch triển khai','Phân công công viêc cho thành viên trong dự án','LongHuu','To Do','Medium','#d6baba','2025-07-30 08:30:00','2025-07-30 11:00:00',64,67,'2025-08-27 14:57:16','2025-08-27 14:57:16'),(17,'1756306732136',4,'Kick off dự án chuyển đổi số',NULL,'LongHuu','To Do','Medium',NULL,'2025-07-28 00:00:00','2025-07-28 00:00:00',0,67,'2025-08-27 14:58:52','2025-08-27 14:58:52'),(18,'1756306768228',4,'Lấy ý kiến sau demo',NULL,'HungDB','To Do','Medium',NULL,'2025-08-12 15:00:00','2025-08-12 16:00:00',0,67,'2025-08-27 14:59:28','2025-08-27 10:00:50'),(19,'1756307296466',5,'Trao đổi thông tin với khách hàng','lấy thông tin về cấu trúc, nội dung, thiết kế website ','MoNT','In Progress','Medium',NULL,'2025-07-02 08:30:00','2025-07-02 15:00:00',59,67,'2025-08-27 15:08:17','2025-08-27 15:08:17'),(20,'1756307367447',5,'Họp thống nhất nội dung','','LongHuu','To Do','Medium',NULL,'2025-07-03 14:00:00','2025-07-03 16:00:00',0,67,'2025-08-27 15:09:28','2025-08-27 15:09:28'),(21,'1756307416472',5,'Lập kế hoạch triển khai',NULL,'ThanhTrung','To Do','Medium',NULL,'2025-07-07 09:00:00','2025-07-07 11:00:00',56,67,'2025-08-27 15:10:17','2025-08-27 15:10:17'),(22,'1756307553142',5,'Triển khai nội dung website','triển khai trang home, dịch vụ, sản phẩm, liên hệ, tin tức lên production','ThanhTrung','In Progress','Medium',NULL,'2025-07-11 08:30:00','2025-07-11 11:00:00',54,67,'2025-08-27 15:12:33','2025-08-27 15:12:33'),(23,'1756307586743',5,'Demo website cho KH',NULL,'MoNT','To Do','Medium',NULL,'2025-07-14 10:00:00','2025-07-14 11:00:00',0,67,'2025-08-27 15:13:07','2025-08-27 15:13:07'),(24,'1756307624121',5,'Chỉnh sửa website theo ý kiến K/H',NULL,'ThanhTrung','To Do','Medium',NULL,'2025-07-15 00:00:00','2025-07-15 00:00:00',0,67,'2025-08-27 15:13:44','2025-08-27 15:13:44'),(25,'1756307662288',5,'Demo website hoàn thiện cho kh',NULL,'MoNT','To Do','Medium',NULL,'2025-07-21 08:00:00','2025-07-21 09:00:00',0,67,'2025-08-27 15:14:22','2025-08-27 10:14:41'),(26,'1756307624121',5,'Chỉnh sửa website theo ý kiến K/H',NULL,'ThanhTrung','To Do','Medium',NULL,'2025-07-21 10:00:00','2025-07-21 11:00:00',0,67,'2025-08-27 15:13:44','2025-08-27 10:15:26'),(27,'1756307795925',5,'Triển khai hệ thống CDP',NULL,'HungDB','In Progress','Medium','null','2025-08-29 08:00:00','2025-08-30 08:30:00',48,2,'2025-08-27 15:16:36','2025-08-29 14:20:53'),(28,'1756307856456',5,'Tiếp nhận ý kiến chỉnh sửa theo yc','lấy ý kiến kh về hệ thống CDP đẻ chỉnh sửa cho phù hợp với yc','MoNT','In Progress','Medium',NULL,'2025-08-07 08:00:00','2025-08-07 10:00:00',69,67,'2025-08-27 15:17:37','2025-08-27 15:17:37'),(29,'1756307909208',5,'Demo hệ thống CDP','Demo hệ thống cdp cho kh sau khi chỉnh sửa','ThanhTrung','In Progress','Medium','#d14f4f','2025-08-12 09:00:00','2025-08-12 11:20:00',99,2,'2025-08-27 15:18:29','2025-08-28 11:10:58'),(30,'1756308012378',5,'Họp trao đổi yc thêm chức năng','kh yêu cầu thêm chức năng kế toán, triển khai dự án','LongHuu','In Progress','Medium',NULL,'2025-08-20 14:00:00','2025-08-20 16:00:00',53,67,'2025-08-27 15:20:12','2025-08-27 15:20:12'),(31,'1756308052705',5,'Demo chức năng Dự án','','ThanhTrung','In Progress','Medium','null','2025-08-28 08:00:00','2025-08-28 09:00:00',47,2,'2025-08-27 15:20:53','2025-08-28 11:01:30'),(32,'1756308156914',5,'Bàn giao Website cho KH','Bàn giao website hoàn thiện bao gồm: tài liệu, file thiết kế, sourcecode,database','MoNT','Done','Medium','#61694f','2025-07-25 10:00:00','2025-07-25 11:00:00',97,67,'2025-08-27 15:22:37','2025-08-27 15:22:53');
/*!40000 ALTER TABLE `flast_projects_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flast_projects_tasks`
--

DROP TABLE IF EXISTS `flast_projects_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flast_projects_tasks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` text,
  `assigned_to` int DEFAULT NULL,
  `status` enum('To Do','In Progress','Done','Cancelled') DEFAULT 'To Do',
  `priority` enum('Low','Medium','High') DEFAULT 'Medium',
  `start_date` date DEFAULT NULL,
  `completed_date` date DEFAULT NULL,
  `user_created_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  KEY `user_created_id` (`user_created_id`),
  CONSTRAINT `flast_projects_tasks_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `flast_projects_list` (`id`) ON DELETE CASCADE,
  CONSTRAINT `flast_projects_tasks_ibfk_2` FOREIGN KEY (`user_created_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flast_projects_tasks`
--

LOCK TABLES `flast_projects_tasks` WRITE;
/*!40000 ALTER TABLE `flast_projects_tasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `flast_projects_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufacture_product`
--

DROP TABLE IF EXISTS `manufacture_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacture_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `product_id` int unsigned NOT NULL,
  `sku_id` int NOT NULL,
  `date_start` datetime NOT NULL,
  `date_end` datetime NOT NULL,
  `target` int unsigned NOT NULL,
  `achieved` int unsigned NOT NULL,
  `sso_id` varchar(200) NOT NULL,
  `status` int DEFAULT '0',
  `material_standard` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `material_real` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `in_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacture_product`
--

LOCK TABLES `manufacture_product` WRITE;
/*!40000 ALTER TABLE `manufacture_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `manufacture_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufacture_status`
--

DROP TABLE IF EXISTS `manufacture_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacture_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacture_status`
--

LOCK TABLES `manufacture_status` WRITE;
/*!40000 ALTER TABLE `manufacture_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `manufacture_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_inbound`
--

DROP TABLE IF EXISTS `material_inbound`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_inbound` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `material_id` bigint DEFAULT NULL,
  `warehouse_id` int DEFAULT NULL,
  `quantity` decimal(15,2) NOT NULL,
  `width` decimal(15,2) DEFAULT NULL,
  `height` decimal(15,2) DEFAULT NULL,
  `date` datetime NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  `sso_id` varchar(19) NOT NULL,
  `notes` text,
  PRIMARY KEY (`id`),
  KEY `material_id` (`material_id`),
  KEY `warehouse_id` (`warehouse_id`),
  CONSTRAINT `material_inbound_ibfk_1` FOREIGN KEY (`material_id`) REFERENCES `materials` (`id`),
  CONSTRAINT `material_inbound_ibfk_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse_stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_inbound`
--

LOCK TABLES `material_inbound` WRITE;
/*!40000 ALTER TABLE `material_inbound` DISABLE KEYS */;
INSERT INTO `material_inbound` VALUES (8,3,4,10.00,32.00,48.00,'2025-10-17 14:04:00','Hoa Dung','Administrator',NULL);
/*!40000 ALTER TABLE `material_inbound` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_inbound_insert` AFTER INSERT ON `material_inbound` FOR EACH ROW BEGIN
    INSERT INTO material_inventory (material_id, warehouse_id, quantity, width, height)
    VALUES (NEW.material_id, NEW.warehouse_id, NEW.quantity, NEW.width, NEW.height)
    ON DUPLICATE KEY UPDATE quantity = quantity + NEW.quantity;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `material_inventory`
--

DROP TABLE IF EXISTS `material_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_inventory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `material_id` int NOT NULL,
  `warehouse_id` int NOT NULL,
  `quantity` decimal(10,2) NOT NULL DEFAULT '0.00',
  `width` decimal(10,2) NOT NULL DEFAULT '0.00',
  `height` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_material_warehouse` (`material_id`,`warehouse_id`,`width`,`height`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_inventory`
--

LOCK TABLES `material_inventory` WRITE;
/*!40000 ALTER TABLE `material_inventory` DISABLE KEYS */;
INSERT INTO `material_inventory` VALUES (5,3,4,10.00,32.00,48.00);
/*!40000 ALTER TABLE `material_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_outbound`
--

DROP TABLE IF EXISTS `material_outbound`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_outbound` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `warehouse_id` int DEFAULT NULL,
  `manufacture_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `material_id` bigint DEFAULT NULL,
  `quantity` decimal(15,2) NOT NULL,
  `width` decimal(15,2) DEFAULT NULL,
  `height` decimal(15,2) DEFAULT NULL,
  `date` datetime NOT NULL,
  `sso_id` varchar(19) NOT NULL,
  `notes` text,
  PRIMARY KEY (`id`),
  KEY `material_id` (`material_id`),
  KEY `warehouse_id` (`warehouse_id`),
  CONSTRAINT `material_outbound_ibfk_1` FOREIGN KEY (`material_id`) REFERENCES `materials` (`id`),
  CONSTRAINT `material_outbound_ibfk_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse_stock` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_outbound`
--

LOCK TABLES `material_outbound` WRITE;
/*!40000 ALTER TABLE `material_outbound` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_outbound` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materials`
--

DROP TABLE IF EXISTS `materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materials` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `unit_type` varchar(50) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `description` text,
  `price_per_unit` decimal(15,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materials`
--

LOCK TABLES `materials` WRITE;
/*!40000 ALTER TABLE `materials` DISABLE KEYS */;
INSERT INTO `materials` VALUES (3,'Kẽm','DIMENSION','m2','Giá kẽm được tính theo khổ (ví dụ: A3, A2, A1, hoặc khổ theo máy in như 52×74, 64×90, 72×102 cm).',1200000.00,'2025-10-17 07:02:18');
/*!40000 ALTER TABLE `materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `object` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `object_id` int DEFAULT '0',
  `section_id` int DEFAULT '0',
  `i_resize` int DEFAULT '0',
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `obj_idx` (`object`),
  KEY `o_id_idx` (`object_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2559 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (2517,'/uploads/product-media/848811/18e1bbe27d4c18ca26235199af2971ba.jpg','Product',749,NULL,0,1),(2518,'/uploads/product-media/848811/a8d82191660a81c0c05c5e1386799a52.jpg','Product',749,NULL,0,1),(2519,'/uploads/product-media/848811/2cdafe1c950457023ea1d8da4b949dfd.jpg','Product',749,NULL,0,1),(2520,'/uploads/product-media/848811/54407dadc61de37c5d686d16e8434dd4.png','Product',749,NULL,0,0),(2521,'/uploads/product-media/848811/4a9766816c0857769769352e7a6e5a19.png','Product',749,NULL,0,0),(2524,'/uploads/product-media/848811/6d01d8413899d26b8d9bb39c431043bf.png','Product',749,NULL,0,0),(2525,'/uploads/product-media/848811/9275d2aef433b7fec39b1beecbe1eb5e.png','Product',749,NULL,0,0),(2528,'/uploads/product-media/848811/9939644ce99c4d3923bab0a2affd21dc.jpeg','Product',749,NULL,0,0),(2529,'/uploads/product-media/848811/3de849f68820aea0a05e2c5efb117b03.jpeg','Product',749,NULL,0,0),(2530,'/uploads/product-media/848811/eb130e187e9381a47e3dcd5c90551f67.png','Product',749,NULL,0,0),(2531,'/uploads/product-media/848811/d67f61704a75b444ac8a11ad4a5f89a1.png','Product',749,NULL,0,0),(2532,'/uploads/product-media/848811/246e61712bbf3c175a1d0fd81aca3abe.png','Product',0,NULL,0,1),(2533,'/uploads/product-media/848811/3f24b48acbdd31f34b9b654c6594e2f0.png','Product',0,NULL,0,1),(2534,'/uploads/product-media/848811/56cc4a0f3881aa1322dfff1e90536166.png','Product',749,NULL,0,0),(2535,'/uploads/product-media/848811/e340145d3f5ee32c6c013819c46fcd41.png','Product',749,NULL,0,0),(2536,'/uploads/product-media/848811/42f60861a8c922b28ccc5ee7c7f6f632.png','Product',749,NULL,0,0),(2537,'/uploads/product-media/848811/4b7ec75239069859207416e9a76758d8.png','Product',749,NULL,0,0),(2538,'/uploads/product-media/848811/c0d89858216457dc96b6e2347d53531c.png','Product',749,1739808075,0,0),(2539,'/uploads/product-media/848811/5bd0bbb841c681aef01c180137d8528a.png','Product',749,1739808332,0,0),(2541,'/uploads/product-media/848811/7fa8d835bc44d33179d75ff9d211e751.png','Product',749,1739808436,0,0),(2542,'/uploads/product-media/848811/65b0026f6bb8c9bbf7f0da747a748e40.jpg','Product',0,345678,0,1),(2543,'/uploads/product-media/848811/88bf8afafab94a6c60be1f9d51ec6a6a.png','Product',0,1739843771,0,1),(2544,'/uploads/product-media/848811/88bf8afafab94a6c60be1f9d51ec6a6a.png','Product',0,1739843771,0,1),(2545,'/uploads/product-media/848811/40062a193fee94c701c0b879c97fdd70.png','Product',0,1739843771,0,1),(2546,'/uploads/product-media/848811/40062a193fee94c701c0b879c97fdd70.png','Product',0,1739843771,0,1),(2547,'/uploads/product-media/848811/01f4604f26c581268ebaa4958da26950.jpeg','Product',769,1739852236,0,1),(2548,'/uploads/product-media/848811/338739e72eefcab8d03909773768d805.png','Product',749,1739852778,0,1),(2549,'/uploads/product-media/848811/679b3cdad4810d710c80e4c25f1a45ec.png','Product',749,1739852809,0,1),(2550,'/uploads/product-media/848811/90e9c9c7ed46504224db3039b967bba7.jpg','Product',0,345678,0,1),(2551,'/uploads/product-media/848811/0ef255b2a7047723a05453bcd894ad9f.png','Product',770,1739874627,0,1),(2552,'/uploads/product-media/848811/12f30a541a8fbcb184ff87158dfc533e.png','Product',771,1739875686,0,1),(2553,'/uploads/product-media/848811/5f0ce2647140d46932b1c49db4939c5c.png','Product',771,1739875686,0,1),(2554,'/uploads/product-media/848811/12f30a541a8fbcb184ff87158dfc533e.png','Product',771,1739875686,0,1),(2555,'/uploads/product-media/848811/5f0ce2647140d46932b1c49db4939c5c.png','Product',771,1739875686,0,1),(2556,'/uploads/product-media/848811/387beefe7efdb28de54f458b570ad2f1.jpg','Product',772,1739896635,0,1),(2557,'/uploads/product-media/848811/085ccf8878b52af5a034188df2cab79c.jpg','Product',773,1740644078,0,1),(2558,'/uploads/product-media/851299/e885f0fa6be2d6022baa1799e17c5e90.jpg','Product',0,1745565252,0,1);
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `service_id` int DEFAULT '0',
  `quality_in_stock` int DEFAULT '0',
  `total_import_stock` int DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `provider_id` int DEFAULT NULL,
  `unit` varchar(100) DEFAULT NULL,
  `price` int DEFAULT '0',
  `price_ref` int DEFAULT '0',
  `seo_title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `seo_description` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `seo_content` text,
  `image` varchar(255) DEFAULT NULL,
  `social` varchar(255) DEFAULT '{"view":139,"like":0,"shear":49}',
  `status` int DEFAULT '0',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=778 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (749,'FLJAJBCK',1,NULL,NULL,'Logo',NULL,10008,'Ấn phẩm',NULL,NULL,NULL,NULL,NULL,'/uploads/product-media/848811/18e1bbe27d4c18ca26235199af2971ba.jpg',NULL,1,NULL,'2025-07-18 04:23:15'),(769,'FLJANWCH',1,NULL,NULL,'Hộp Mềm',NULL,10008,'Hộp',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'2025-07-18 04:23:15'),(770,'FLJAORXZ',1,NULL,NULL,'Áo Phông',NULL,10008,'Bộ',NULL,NULL,NULL,NULL,NULL,'/uploads/product-media/848811/0ef255b2a7047723a05453bcd894ad9f.png',NULL,1,'2025-02-20 10:37:45','2025-07-18 04:23:15'),(771,'FLJANEMR',1,NULL,NULL,'Hộp cứng',NULL,10008,'SET',NULL,NULL,NULL,NULL,NULL,'/uploads/product-media/848811/12f30a541a8fbcb184ff87158dfc533e.png',NULL,1,'2025-02-18 04:51:50','2025-02-18 04:51:50'),(772,'MRJAHOMD',10014,NULL,NULL,'MRPO Duy Tân',NULL,10009,'Phòng',NULL,NULL,NULL,NULL,NULL,'/uploads/product-media/848811/387beefe7efdb28de54f458b570ad2f1.jpg',NULL,1,NULL,'2025-03-08 03:52:45'),(773,'FLJAXZGI',1,NULL,NULL,'Hộp yến sào Nam Bắc',NULL,10008,'Hộp',NULL,NULL,NULL,NULL,NULL,'/uploads/product-media/848811/085ccf8878b52af5a034188df2cab79c.jpg',NULL,1,NULL,'2025-03-21 02:53:45'),(774,'FLJAVXHD',1,NULL,NULL,'Hộp Mềm',NULL,10008,'Hộp',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'2025-03-06 01:42:33','2025-07-18 04:22:23'),(775,'FLJAIJLP',1,NULL,NULL,'Sách giáo khoa toán 1',NULL,10008,'Quyển',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'2025-05-07 10:39:27','2025-05-07 10:39:27'),(776,'FLLYYEECPM',1,NULL,NULL,'Hộp carton lạnh',NULL,10008,'Hộp',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'2025-07-15 03:54:19'),(777,'FLJAPFLK',1,NULL,NULL,'Bánh Chocopie',NULL,10008,'Hộp',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'2025-06-13 08:46:45');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_attributed`
--

DROP TABLE IF EXISTS `product_attributed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_attributed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `attributed_id` int DEFAULT NULL,
  `attributed_value_id` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10462 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_attributed`
--

LOCK TABLES `product_attributed` WRITE;
/*!40000 ALTER TABLE `product_attributed` DISABLE KEYS */;
INSERT INTO `product_attributed` VALUES (10250,771,10008,10025,'PHong cách','Sang trọng'),(10251,771,10009,10010,'Kích thước','30x55x30'),(10279,770,10008,10009,'PHong cách','Tối giản B+'),(10280,770,10008,10014,'PHong cách','hjhhhh'),(10321,769,10008,10009,'PHong cách','Tối giản B+'),(10322,774,10008,10009,'PHong cách','Tối giản B+'),(10326,772,10013,10054,'Số lượng','Dành cho 4-5 nhân sự'),(10327,772,10014,10055,'Diện tích','13-14.5 m2'),(10328,772,10015,10056,'Giá','8-9 triệu vnđ/tháng'),(10329,773,10018,10068,'Thuôc tính chung',' Kích thước: 360x360x95'),(10330,773,10018,10077,'Thuôc tính chung',' Dạng kết cấu: Âm dương nắp lửng'),(10331,773,10017,10067,'Vật liệu',' Vỏ ngoài: Giấy C150 (N/A gsm)'),(10332,773,10017,10069,'Vật liệu','- Cốt vỏ ngoài: Carton thường 2,5 mm'),(10333,773,10017,10070,'Vật liệu','- Tờ gác vỏ hộp: N/A (N/A gsm)'),(10334,773,10017,10071,'Vật liệu','- Khay định hình: Carton lạnh (N/A gsm)'),(10335,773,10017,10072,'Vật liệu','- Cốt khay định hình: N/A N/A'),(10336,773,10019,10073,'In ấn','Công nghệ in: In Offset'),(10337,773,10019,10074,'In ấn',' Kiểu in: N/A'),(10338,773,10020,10075,'Gia công:',' UV Toàn phần: Toàn bộ hoạt tiết trên mặt hộp'),(10339,773,10020,10076,'Gia công:','Cán mờ'),(10343,749,10008,10009,'PHong cách','Tối giản B+'),(10344,749,10009,10010,'Kích thước','30x55x30'),(10345,749,10009,10011,'Kích thước','10x40x55'),(10346,749,10011,10080,'Khối lượng','45kg'),(10347,775,10022,10081,'Học kỳ','Kỳ 1'),(10348,775,10022,10082,'Học kỳ','Kỳ 2'),(10349,775,10009,10011,'Kích thước','10x40x55'),(10361,777,10025,10092,'Loại','12 bánh'),(10362,777,10025,10093,'Loại','24 bánh'),(10453,776,10023,10083,'Dạng kết cấu','Âm dương nắp lửng'),(10454,776,10023,10084,'Dạng kết cấu','Âm dương nắp lật'),(10455,776,10023,10085,'Dạng kết cấu','Nam châm nắp phẳng'),(10456,776,10023,10086,'Dạng kết cấu','Hai tầng'),(10457,776,10023,10087,'Dạng kết cấu','Hai cánh mở giữa'),(10458,776,10024,10088,'Bồi vỏ','Giấy C150'),(10459,776,10024,10089,'Bồi vỏ','Giấy Crat'),(10460,776,10024,10090,'Bồi vỏ','Giấy Mỹ thuật'),(10461,776,10024,10091,'Bồi vỏ','Giấy Op/Ford');
/*!40000 ALTER TABLE `product_attributed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-cmp-category_id` (`category_id`),
  KEY `idx-cmp-product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=308 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int unsigned DEFAULT '0',
  `file_name` varchar(255) DEFAULT '',
  `is_slideshow` int unsigned DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `product_id_index` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21589 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_material`
--

DROP TABLE IF EXISTS `product_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `material_id` bigint NOT NULL,
  `material_unit` varchar(100) DEFAULT NULL,
  `product_id` int unsigned NOT NULL,
  `sku_id` int NOT NULL,
  `quantity` decimal(15,2) NOT NULL,
  `width` decimal(15,2) DEFAULT NULL,
  `height` decimal(15,2) DEFAULT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `sso_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_id` (`product_id`),
  KEY `idx-cmp-material_id` (`material_id`),
  CONSTRAINT `fk_product_material_material_id` FOREIGN KEY (`material_id`) REFERENCES `materials` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_product_material_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=309 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_material`
--

LOCK TABLES `product_material` WRITE;
/*!40000 ALTER TABLE `product_material` DISABLE KEYS */;
INSERT INTO `product_material` VALUES (308,3,'DIMENSION',777,10210,1.00,30.00,40.00,144000.00,'Administrator');
/*!40000 ALTER TABLE `product_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_property`
--

DROP TABLE IF EXISTS `product_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_property` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10148 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_property`
--

LOCK TABLES `product_property` WRITE;
/*!40000 ALTER TABLE `product_property` DISABLE KEYS */;
INSERT INTO `product_property` VALUES (10094,771,'đáy','thêm lớp lót'),(10107,770,'Môi trường','Ánh sáng dưới 30C'),(10120,769,'Môi trường','Ánh sáng dưới 30C'),(10121,774,'Môi trường','Ánh sáng dưới 30C'),(10126,772,'Tiện ích','Lễ tân chuyên nghiệp, thành thạo ít nhất hai ngôn ngữ Anh/ Việt giao tiếp tốt, sẵn sàng phục vụ khách hàng các công việc hành chính văn phòng như: tiếp nhận, chuyển phát nhanh thư, bưu phẩm, đặt xe, khách sạn.'),(10127,772,'Tiện ích','Khu vực lễ tân, sảnh tiếp khách sang trọng, lịch sự, khu vực office lounge miễn phí trà, nước, café….'),(10128,772,'Tiện ích','Được trang bị đầy đủ nội thất văn phòng: hệ thống bảng tên Công ty, bàn ghế, tủ tài liệu..'),(10129,772,'Tiện ích','Được sử dụng địa chỉ toà nhà để làm Giấy phép đăng ký kinh doanh.'),(10130,773,' Khay định hình hũ hạt:','c150 in bệt 1 màu, bồi carton 1,5mm bế định hình sản phẩm'),(10131,773,'Quy cách túi:','Kích thước: 38x37x12 cm'),(10132,773,'Quy cách túi:','In 4 màu giấy Ivo300 cán mờ, bế dán tp'),(10134,749,'Môi trường','Ánh sáng dưới 30C'),(10135,775,'Tiện ích','thêm lớp lót'),(10147,776,' Khay định hình ','Thêm lớp lót');
/*!40000 ALTER TABLE `product_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_skus`
--

DROP TABLE IF EXISTS `product_skus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_skus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `del` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10212 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_skus`
--

LOCK TABLES `product_skus` WRITE;
/*!40000 ALTER TABLE `product_skus` DISABLE KEYS */;
INSERT INTO `product_skus` VALUES (10016,749,'Thuộc tính gia công và đóng gói',0),(10197,749,'test 2',1),(10198,774,'Thuộc tính gia công và đóng gói',0),(10199,770,'Thuộc tính gia công và đóng gói',0),(10200,771,'Hộp cứng vát',0),(10201,772,'Phòng làm việc',0),(10202,772,'Phòng làm việc',1),(10203,772,'Phòng làm việc',1),(10204,772,'Phòng làm việc',1),(10205,773,'Hộp yến sào',0),(10206,773,'Hộp yến sào loại 2',0),(10207,775,'Sách kỳ 1',0),(10208,776,'Hộp 1.3mm',0),(10209,776,'Hộp 1.5mm',0),(10210,777,'Loại 12 bánh',0),(10211,777,'Loại 24 bánh',0);
/*!40000 ALTER TABLE `product_skus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_skus_details`
--

DROP TABLE IF EXISTS `product_skus_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_skus_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `sku_id` int NOT NULL DEFAULT '0',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `attributed_id` int NOT NULL,
  `attributed_value_id` int NOT NULL,
  `del` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10402 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_skus_details`
--

LOCK TABLES `product_skus_details` WRITE;
/*!40000 ALTER TABLE `product_skus_details` DISABLE KEYS */;
INSERT INTO `product_skus_details` VALUES (10010,749,10016,'PHong cách','Tối giản B+',10008,10009,0),(10011,749,10016,'Kích thước','30x55x30',10009,10010,0),(10340,749,10197,'PHong cách','Tối giản B+',10008,10009,0),(10341,774,10198,'PHong cách','Tối giản B+',10008,10009,0),(10342,770,10199,'PHong cách','Tối giản B+',10008,10009,0),(10343,770,10199,'PHong cách','hjhhhh',10008,10014,0),(10344,771,10200,'PHong cách','Sang trọng',10008,10025,0),(10345,771,10200,'Kích thước','30x55x30',10009,10010,0),(10346,772,10201,'Số lượng','Dành cho 4-5 nhân sự',10013,10054,0),(10347,772,10201,'Diện tích','13-14.5 m2',10014,10055,0),(10348,772,10201,'Giá','8-9 triệu vnđ/tháng',10015,10056,0),(10349,772,10202,'Số lượng','Dành cho 7-8 nhân sự',10013,10057,0),(10350,772,10202,'Diện tích','19.5-24 m2',10014,10060,0),(10351,772,10202,'Giá','11-16.5 triệu vnđ/tháng',10015,10063,0),(10352,772,10203,'Số lượng','Dành cho 9-11 nhân sự',10013,10058,0),(10353,772,10203,'Diện tích','24.5-34.5 m2',10014,10061,0),(10354,772,10203,'Giá','14.8-23 triệu vnđ/tháng',10015,10064,0),(10355,772,10204,'Số lượng','Dành cho 15-17 nhân sự',10013,10059,0),(10356,772,10204,'Diện tích','53m2',10014,10062,0),(10357,772,10204,'Giá','32 triệu vnđ/tháng',10015,10065,0),(10358,773,10205,'Thuôc tính chung',' Kích thước: 360x360x95',10018,10068,0),(10359,773,10205,'Vật liệu',' Vỏ ngoài: Giấy C150 (N/A gsm)',10017,10067,0),(10360,773,10205,'Vật liệu','- Cốt vỏ ngoài: Carton thường 2,5 mm',10017,10069,0),(10361,773,10205,'Vật liệu','- Tờ gác vỏ hộp: N/A (N/A gsm)',10017,10070,0),(10362,773,10205,'Vật liệu','- Khay định hình: Carton lạnh (N/A gsm)',10017,10071,0),(10363,773,10205,'Vật liệu','- Cốt khay định hình: N/A N/A',10017,10072,0),(10364,773,10205,'In ấn','Công nghệ in: In Offset',10019,10073,0),(10365,773,10205,'In ấn',' Kiểu in: N/A',10019,10074,0),(10366,773,10205,'Gia công:',' UV Toàn phần: Toàn bộ hoạt tiết trên mặt hộp',10020,10075,0),(10367,773,10205,'Gia công:','Cán mờ',10020,10076,0),(10368,773,10205,'Thuôc tính chung',' Dạng kết cấu: Âm dương nắp lửng',10018,10077,0),(10369,773,10206,'Thuôc tính chung',' Kích thước: 360x360x95',10018,10068,0),(10370,773,10206,'Thuôc tính chung',' Dạng kết cấu: Âm dương nắp lửng',10018,10077,0),(10371,773,10206,'Vật liệu',' Vỏ ngoài: Giấy C150 (N/A gsm)',10017,10067,0),(10372,773,10206,'Vật liệu','- Cốt vỏ ngoài: Carton thường 2,5 mm',10017,10069,0),(10373,773,10206,'Vật liệu','- Tờ gác vỏ hộp: N/A (N/A gsm)',10017,10070,0),(10374,773,10206,'Vật liệu','- Khay định hình: Carton lạnh (N/A gsm)',10017,10071,0),(10375,773,10206,'Vật liệu','- Cốt khay định hình: N/A N/A',10017,10072,0),(10376,773,10206,'In ấn','Công nghệ in: In Offset',10019,10073,0),(10377,773,10206,'In ấn',' Kiểu in: N/A',10019,10074,0),(10378,773,10206,'Gia công:',' UV Toàn phần: Toàn bộ hoạt tiết trên mặt hộp',10020,10075,0),(10379,773,10206,'Gia công:','Cán mờ',10020,10076,0),(10380,775,10207,'Học kỳ','Kỳ 1',10022,10081,0),(10381,775,10207,'Kích thước','10x40x55',10009,10011,0),(10382,776,10208,'Dạng kết cấu','Âm dương nắp lửng',10023,10083,0),(10383,776,10208,'Dạng kết cấu','Âm dương nắp lật',10023,10084,0),(10384,776,10208,'Dạng kết cấu','Nam châm nắp phẳng',10023,10085,0),(10385,776,10208,'Dạng kết cấu','Hai tầng',10023,10086,0),(10386,776,10208,'Dạng kết cấu','Hai cánh mở giữa',10023,10087,0),(10387,776,10208,'Bồi vỏ','Giấy C150',10024,10088,0),(10388,776,10208,'Bồi vỏ','Giấy Crat',10024,10089,0),(10389,776,10208,'Bồi vỏ','Giấy Mỹ thuật',10024,10090,0),(10390,776,10208,'Bồi vỏ','Giấy Op/Ford',10024,10091,0),(10391,776,10209,'Dạng kết cấu','Âm dương nắp lửng',10023,10083,0),(10392,776,10209,'Dạng kết cấu','Âm dương nắp lật',10023,10084,0),(10393,776,10209,'Dạng kết cấu','Nam châm nắp phẳng',10023,10085,0),(10394,776,10209,'Dạng kết cấu','Hai tầng',10023,10086,0),(10395,776,10209,'Dạng kết cấu','Hai cánh mở giữa',10023,10087,0),(10396,776,10209,'Bồi vỏ','Giấy C150',10024,10088,0),(10397,776,10209,'Bồi vỏ','Giấy Crat',10024,10089,0),(10398,776,10209,'Bồi vỏ','Giấy Mỹ thuật',10024,10090,0),(10399,776,10209,'Bồi vỏ','Giấy Op/Ford',10024,10091,0),(10400,777,10210,'Loại','12 bánh',10025,10092,0),(10401,777,10211,'Loại','24 bánh',10025,10093,0);
/*!40000 ALTER TABLE `product_skus_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_skus_price`
--

DROP TABLE IF EXISTS `product_skus_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_skus_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sku_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity_from` int NOT NULL,
  `quantity_to` int NOT NULL,
  `price_ref` int NOT NULL DEFAULT '0',
  `price` int NOT NULL DEFAULT '0',
  `price_import` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10312 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_skus_price`
--

LOCK TABLES `product_skus_price` WRITE;
/*!40000 ALTER TABLE `product_skus_price` DISABLE KEYS */;
INSERT INTO `product_skus_price` VALUES (10240,10200,771,1,10,350000,500000,0),(10241,10199,770,1,20,0,2000000,0),(10255,10198,769,20,200,0,400000,0),(10256,10198,774,20,200,0,400000,0),(10260,10206,773,1,10,450000,600000,0),(10261,10206,773,11,50,430000,580000,0),(10262,10206,773,51,200,420000,560000,0),(10264,10016,749,2,20,0,2000000,0),(10265,10207,775,1,10,20000,35000,0),(10266,10207,775,11,50,20000,33000,0),(10267,10207,775,51,100,20000,31000,0),(10274,10211,777,1,10,100000,110000,0),(10309,10208,776,1,20,120000,100000,0),(10310,10209,776,1,100,55000,130000,0),(10311,10209,776,101,500,45000,115000,0);
/*!40000 ALTER TABLE `product_skus_price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10018 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_type`
--

LOCK TABLES `product_type` WRITE;
/*!40000 ALTER TABLE `product_type` DISABLE KEYS */;
INSERT INTO `product_type` VALUES (10008,'Software'),(10009,'Hardware'),(10010,'Co.Center'),(10014,'Phòng làm việc'),(10015,'Tools');
/*!40000 ALTER TABLE `product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provider` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_contact` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `geolocation` varchar(50) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `representative` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `email_manufacture` varchar(50) DEFAULT NULL,
  `bank_code` varchar(50) DEFAULT NULL,
  `bank_name` varchar(50) DEFAULT NULL,
  `bank_owner` varchar(50) DEFAULT NULL,
  `strengths` varchar(50) DEFAULT NULL,
  `note` varchar(50) DEFAULT NULL,
  `province_id` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `status` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10010 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--

LOCK TABLES `provider` WRITE;
/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
INSERT INTO `provider` VALUES (10008,'Flast Solution','35  Lê Văn Lương','098793891',NULL,NULL,NULL,'H.T.M.D',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(10009,'MRPO Duy Tân','Tầng 7 Tòa nhà Việt Á, Số 9 Duy Tân, Q. Cầu Giấy','098793891',NULL,NULL,NULL,'H.T.M.D',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `province` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT '',
  `parent_id` int unsigned DEFAULT '0',
  `order` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=853 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province`
--

LOCK TABLES `province` WRITE;
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
INSERT INTO `province` VALUES (1,'Tuyên Quang',0,0),(2,'Lào Cai',0,0),(3,'Thái Nguyên',0,0),(4,'Bắc Ninh',0,0),(5,'Hưng Yên',0,0),(6,'Phú Thọ',0,0),(7,'Hải Phòng',0,0),(8,'Ninh Bình',0,0),(9,'Quảng Trị',0,0),(10,'Đà Nẵng',0,0),(11,'Quảng Ngãi',0,0),(12,'Gia Lai',0,0),(13,'Khánh Hoà',0,0),(14,'Lâm Đồng',0,0),(15,'Đắk Lắk',0,0),(16,'Hồ Chí Minh',0,0),(17,'Đồng Nai',0,0),(18,'Tây Ninh',0,0),(19,'Cần Thơ',0,0),(20,'Vĩnh Long',0,0),(21,'Đồng Tháp',0,0),(22,'Cà Mau',0,0),(23,'An Giang',0,0),(24,'Hà Nội',0,0),(25,'Thành phố Huế',0,0),(26,'Lai Châu',0,0),(27,'Điện Biên',0,0),(28,'Sơn La',0,0),(29,'Lạng Sơn',0,0),(30,'Quảng Ninh',0,0),(31,'Thanh Hoá',0,0),(32,'Nghệ An',0,0),(33,'Hà Tĩnh',0,0),(34,'Cao Bằng',0,0),(37,'Ba Đình',24,0),(38,'Ngọc Hà',24,0),(39,'Giảng Võ',24,0),(40,'Hoàn Kiếm',24,0),(41,'Cửa Nam',24,0),(42,'Phú Thượng',24,0),(43,'Hồng Hà',24,0),(44,'Tây Hồ',24,0),(45,'Bồ Đề',24,0),(46,'Việt Hưng',24,0),(47,'Phúc Lợi',24,0),(48,'Long Biên',24,0),(49,'Nghĩa Đô',24,0),(50,'Cầu Giấy',24,0),(51,'Yên Hòa',24,0),(52,'Ô Chợ Dừa',24,0),(53,'Láng',24,0),(54,'Văn Miếu - Quốc Tử Giám',24,0),(55,'Kim Liên',24,0),(56,'Đống Đa',24,0),(57,'Hai Bà Trưng',24,0),(58,'Vĩnh Tuy',24,0),(59,'Bạch Mai',24,0),(60,'Vĩnh Hưng',24,0),(61,'Định Công',24,0),(62,'Tương Mai',24,0),(63,'Lĩnh Nam',24,0),(64,'Hoàng Mai',24,0),(65,'Hoàng Liệt',24,0),(66,'Yên Sở',24,0),(67,'Phương Liệt',24,0),(68,'Khương Đình',24,0),(69,'Thanh Xuân',24,0),(70,'Sóc Sơn',24,0),(71,'Kim Anh',24,0),(72,'Trung Giã',24,0),(73,'Đa Phúc',24,0),(74,'Nội Bài',24,0),(75,'Đông Anh',24,0),(76,'Phúc Thịnh',24,0),(77,'Thư Lâm',24,0),(78,'Thiên Lộc',24,0),(79,'Vĩnh Thanh',24,0),(80,'Phù Đổng',24,0),(81,'Thuận An',24,0),(82,'Gia Lâm',24,0),(83,'Bát Tràng',24,0),(84,'Từ Liêm',24,0),(85,'Thượng Cát',24,0),(86,'Đông Ngạc',24,0),(87,'Xuân Đỉnh',24,0),(88,'Tây Tựu',24,0),(89,'Phú Diễn',24,0),(90,'Xuân Phương',24,0),(91,'Tây Mỗ',24,0),(92,'Đại Mỗ',24,0),(93,'Thanh Trì',24,0),(94,'Thanh Liệt',24,0),(95,'Đại Thanh',24,0),(96,'Ngọc Hồi',24,0),(97,'Nam Phù',24,0),(98,'Yên Xuân',24,0),(99,'Quang Minh',24,0),(100,'Yên Lãng',24,0),(101,'Tiến Thắng',24,0),(102,'Mê Linh',24,0),(103,'Kiến Hưng',24,0),(104,'Hà Đông',24,0),(105,'Yên Nghĩa',24,0),(106,'Phú Lương',24,0),(107,'Sơn Tây',24,0),(108,'Tùng Thiện',24,0),(109,'Đoài Phương',24,0),(110,'Quảng Oai',24,0),(111,'Cổ Đô',24,0),(112,'Minh Châu',24,0),(113,'Vật Lại',24,0),(114,'Bất Bạt',24,0),(115,'Suối Hai',24,0),(116,'Ba Vì',24,0),(117,'Yên Bài',24,0),(118,'Phúc Thọ',24,0),(119,'Phúc Lộc',24,0),(120,'Hát Môn',24,0),(121,'Đan Phượng',24,0),(122,'Liên Minh',24,0),(123,'Ô Diên',24,0),(124,'Hoài Đức',24,0),(125,'Dương Hòa',24,0),(126,'Sơn Đồng',24,0),(127,'Dương Nội',24,0),(128,'Quốc Oai',24,0),(129,'Kiều Phú',24,0),(130,'Hưng Đạo',24,0),(131,'Phú Cát',24,0),(132,'Thạch Thất',24,0),(133,'Hạ Bằng',24,0),(134,'Hòa Lạc',24,0),(135,'Tây Phương',24,0),(136,'Chương Mỹ',24,0),(137,'Phú Nghĩa',24,0),(138,'Xuân Mai',24,0),(139,'Quảng Bị',24,0),(140,'Trần Phú',24,0),(141,'Hòa Phú',24,0),(142,'Thanh Oai',24,0),(143,'Bình Minh',24,0),(144,'Tam Hưng',24,0),(145,'Dân Hòa',24,0),(146,'Thường Tín',24,0),(147,'Hồng Vân',24,0),(148,'Thượng Phúc',24,0),(149,'Chương Dương',24,0),(150,'Phú Xuyên',24,0),(151,'Phượng Dực',24,0),(152,'Chuyên Mỹ',24,0),(153,'Đại Xuyên',24,0),(154,'Vân Đình',24,0),(155,'Ứng Thiên',24,0),(156,'Ứng Hòa',24,0),(157,'Hòa Xá',24,0),(158,'Mỹ Đức',24,0),(159,'Phúc Sơn',24,0),(160,'Hồng Sơn',24,0),(161,'Hương Sơn',24,0),(162,'Thục Phán',34,0),(163,'Núi Trí Cao',34,0),(164,'Tân Giang',34,0),(165,'Bảo Lâm',34,0),(166,'Nam Quang',34,0),(167,'Quảng Lâm',34,0),(168,'Yên Thổ',34,0),(169,'Bảo Lạc',34,0),(170,'Cốc Pàng',34,0),(171,'Cô Ba',34,0),(172,'Khánh Xuân',34,0),(173,'Xuân Trường',34,0),(174,'Huy Giáp',34,0),(175,'Sơn Lộ',34,0),(176,'Thông Nông',34,0),(177,'Cần Yên',34,0),(178,'Thanh Long',34,0),(179,'Tường Hà',34,0),(180,'Lũng Nậm',34,0),(181,'Tổng Cọt',34,0),(182,'Hà Quảng',34,0),(183,'Trà Linh',34,0),(184,'Quang Hán',34,0),(185,'Quang Trung',34,0),(186,'Trùng Khánh',34,0),(187,'Đình Phong',34,0),(188,'Đam Thủy',34,0),(189,'Đoài Dương',34,0),(190,'Lý Quốc',34,0),(191,'Quang Long',34,0),(192,'Hạ Lang',34,0),(193,'Vĩnh Quý',34,0),(194,'Quảng Uyên',34,0),(195,'Độc Lập',34,0),(196,'Hạnh Phúc',34,0),(197,'Bế Văn Đàn',34,0),(198,'Phục Hòa',34,0),(199,'Hòa An',34,0),(200,'Nam Tuấn',34,0),(201,'Nguyên Huệ',34,0),(202,'Bạch Đằng',34,0),(203,'Nguyên Bình',34,0),(204,'Tính Túc',34,0),(205,'Ca Thành',34,0),(206,'Minh Tâm',34,0),(207,'Phan Thanh',34,0),(208,'Tam Kim',34,0),(209,'Thành Công',34,0),(210,'Đông Khê',34,0),(211,'Canh Tân',34,0),(212,'Kim Đồng',34,0),(213,'Minh Khai',34,0),(214,'Thạch An',34,0),(215,'Đức Long',34,0),(216,'Hà Giang 2',1,0),(217,'Hà Giang 1',1,0),(218,'Ngọc Đường',1,0),(219,'Phú Linh',1,0),(220,'Lũng Cú',1,0),(221,'Đồng Văn',1,0),(222,'Sà Phìn',1,0),(223,'Phố Bảng',1,0),(224,'Lũng Phìn',1,0),(225,'Mèo Vạc',1,0),(226,'Sơn Vĩ',1,0),(227,'Sủng Máng',1,0),(228,'Khâu Vai',1,0),(229,'Tát Ngà',1,0),(230,'Niêm Sơn',1,0),(231,'Yên Minh',1,0),(232,'Thắng Mố',1,0),(233,'Bạch Đích',1,0),(234,'Mậu Duệ',1,0),(235,'Ngọc Long',1,0),(236,'Đường Thượng',1,0),(237,'Du Già',1,0),(238,'Quản Bạ',1,0),(239,'Cán Tỷ',1,0),(240,'Nghĩa Thuận',1,0),(241,'Tùng Vài',1,0),(242,'Lùng Tám',1,0),(243,'Vị Xuyên',1,0),(244,'Minh Tân',1,0),(245,'Thuận Hoà',1,0),(246,'Tùng Bá',1,0),(247,'Thanh Thủy',1,0),(248,'Lao Chải',1,0),(249,'Cao Bồ',1,0),(250,'Thượng Sơn',1,0),(251,'Việt Lâm',1,0),(252,'Linh Hồ',1,0),(253,'Bạch Ngọc',1,0),(254,'Minh Sơn',1,0),(255,'Giáp Trung',1,0),(256,'Bắc Mê',1,0),(257,'Yên Cường',1,0),(258,'Đường Hồng',1,0),(259,'Hoàng Su Phì',1,0),(260,'Pờ Ly Ngài',1,0),(261,'Nậm Dịch',1,0),(262,'Hồ Thầu',1,0),(263,'Thông Nguyên',1,0),(264,'Pà Vầy Sủ',1,0),(265,'Xín Mần',1,0),(266,'Trung Thịnh',1,0),(267,'Nấm Dẩn',1,0),(268,'Quảng Nguyên',1,0),(269,'Khuôn Lùng',1,0),(270,'Bắc Quang',1,0),(271,'Vĩnh Tuy',1,0),(272,'Đông Tâm',1,0),(273,'Tân Quang',1,0),(274,'Bằng Hành',1,0),(275,'Liên Hiệp',1,0),(276,'Hùng An',1,0),(277,'Đông Yên',1,0),(278,'Tiên Nguyên',1,0),(279,'Yên Thành',1,0),(280,'Quang Bình',1,0),(281,'Tân Trịnh',1,0),(282,'Bằng Lang',1,0),(283,'Xuân Giang',1,0),(284,'Tiên Yên',1,0),(285,'Nông Tiến',1,0),(286,'Minh Xuân',1,0),(287,'Mỹ Lâm',1,0),(288,'An Tường',1,0),(289,'An Thuận',1,0),(290,'Nhữ Khê',1,0),(291,'Sơn Dương',1,0),(292,'Tân Trào',1,0),(293,'Bình Ca',1,0),(294,'Minh Thanh',1,0),(295,'Đông Thọ',1,0),(296,'Tân Thanh',1,0),(297,'Hồng Sơn',1,0),(298,'Phú Lương',1,0),(299,'Sơn Thuỷ',1,0),(300,'Trường Sinh',1,0),(301,'Hà Giang 2',1,0),(302,'Hà Giang 1',1,0),(303,'Ngọc Đường',1,0),(304,'Phú Linh',1,0),(305,'Lũng Cú',1,0),(306,'Đồng Văn',1,0),(307,'Sà Phìn',1,0),(308,'Phố Bảng',1,0),(309,'Lũng Phìn',1,0),(310,'Mèo Vạc',1,0),(311,'Sơn Vĩ',1,0),(312,'Sủng Máng',1,0),(313,'Khâu Vai',1,0),(314,'Tát Ngà',1,0),(315,'Niêm Sơn',1,0),(316,'Yên Minh',1,0),(317,'Thắng Mố',1,0),(318,'Bạch Đích',1,0),(319,'Mậu Duệ',1,0),(320,'Ngọc Long',1,0),(321,'Đường Thượng',1,0),(322,'Du Già',1,0),(323,'Quản Bạ',1,0),(324,'Cán Tỷ',1,0),(325,'Nghĩa Thuận',1,0),(326,'Tùng Vài',1,0),(327,'Lùng Tám',1,0),(328,'Vị Xuyên',1,0),(329,'Minh Tân',1,0),(330,'Thuận Hoà',1,0),(331,'Tùng Bá',1,0),(332,'Thanh Thủy',1,0),(333,'Lao Chải',1,0),(334,'Cao Bồ',1,0),(335,'Thượng Sơn',1,0),(336,'Việt Lâm',1,0),(337,'Linh Hồ',1,0),(338,'Bạch Ngọc',1,0),(339,'Minh Sơn',1,0),(340,'Giáp Trung',1,0),(341,'Bắc Mê',1,0),(342,'Yên Cường',1,0),(343,'Đường Hồng',1,0),(344,'Hoàng Su Phì',1,0),(345,'Pờ Ly Ngài',1,0),(346,'Nậm Dịch',1,0),(347,'Hồ Thầu',1,0),(348,'Thông Nguyên',1,0),(349,'Pà Vầy Sủ',1,0),(350,'Xín Mần',1,0),(351,'Trung Thịnh',1,0),(352,'Nấm Dẩn',1,0),(353,'Quảng Nguyên',1,0),(354,'Khuôn Lùng',1,0),(355,'Bắc Quang',1,0),(356,'Vĩnh Tuy',1,0),(357,'Đông Tâm',1,0),(358,'Tân Quang',1,0),(359,'Bằng Hành',1,0),(360,'Liên Hiệp',1,0),(361,'Hùng An',1,0),(362,'Đông Yên',1,0),(363,'Tiên Nguyên',1,0),(364,'Yên Thành',1,0),(365,'Quang Bình',1,0),(366,'Tân Trịnh',1,0),(367,'Bằng Lang',1,0),(368,'Xuân Giang',1,0),(369,'Tiên Yên',1,0),(370,'Nông Tiến',1,0),(371,'Minh Xuân',1,0),(372,'Mỹ Lâm',1,0),(373,'An Tường',1,0),(374,'An Thuận',1,0),(375,'Nhữ Khê',1,0),(376,'Sơn Dương',1,0),(377,'Tân Trào',1,0),(378,'Bình Ca',1,0),(379,'Minh Thanh',1,0),(380,'Đông Thọ',1,0),(381,'Tân Thanh',1,0),(382,'Hồng Sơn',1,0),(383,'Phú Lương',1,0),(384,'Sơn Thuỷ',1,0),(385,'Trường Sinh',1,0),(386,'Điện Biên Phủ',27,0),(387,'Mường Lay',27,0),(388,'Sín Thầu',27,0),(389,'Mường Nhé',27,0),(390,'Nậm Kè',27,0),(391,'Mường Toong',27,0),(392,'Quảng Lâm',27,0),(393,'Mường Chà',27,0),(394,'Nà Hỳ',27,0),(395,'Na Sang',27,0),(396,'Chà Tở',27,0),(397,'Nà Bủng',27,0),(398,'Mường Tùng',27,0),(399,'Pa Ham',27,0),(400,'Nậm Nèn',27,0),(401,'Si Pa Phìn',27,0),(402,'Mường Pồn',27,0),(403,'Na Sơn',27,0),(404,'Mường Luân',27,0),(405,'Tủa Chùa',27,0),(406,'Tủa Thàng',27,0),(407,'Sin Chải',27,0),(408,'Sinh Phình',27,0),(409,'Sáng Nhè',27,0),(410,'Tuần Giáo',27,0),(411,'Mường Ảng',27,0),(412,'Pú Nhung',27,0),(413,'Mường Mùn',27,0),(414,'Chiềng Sinh',27,0),(415,'Quài Tở',27,0),(416,'Búng Lao',27,0),(417,'Mường Lạn',27,0),(418,'Nà Tấu',27,0),(419,'Mường Phăng',27,0),(420,'Thanh Nưa',27,0),(421,'Mường Thanh',27,0),(422,'Thanh Yên',27,0),(423,'Thanh An',27,0),(424,'Sam Mứn',27,0),(425,'Núa Ngam',27,0),(426,'Mường Nhà',27,0),(427,'Pu Nhi',27,0),(428,'Phình Giàng',27,0),(429,'Tìa Dình',27,0),(430,'Đoàn Kết',26,0),(431,'Bình Lư',26,0),(432,'Sin Suối Hồ',26,0),(433,'Tả Lèng',26,0),(434,'Tân Phong',26,0),(435,'Bản Bo',26,0),(436,'Khun Há',26,0),(437,'Bum Tở',26,0),(438,'Nậm Hàng',26,0),(439,'Thu Lũm',26,0),(440,'Pa Ủ',26,0),(441,'Mường Tè',26,0),(442,'Mù Cả',26,0),(443,'Hua Bum',26,0),(444,'Tà Tổng',26,0),(445,'Bum Nưa',26,0),(446,'Mường Mô',26,0),(447,'Sìn Hồ',26,0),(448,'Lê Lợi',26,0),(449,'Pa Tần',26,0),(450,'Hồng Thu',26,0),(451,'Nậm Tăm',26,0),(452,'Tủa Sín Chải',26,0),(453,'Pu Sam Cáp',26,0),(454,'Nậm Mạ',26,0),(455,'Nậm Cuổi',26,0),(456,'Phong Thổ',26,0),(457,'Sì Lở Lầu',26,0),(458,'Dào San',26,0),(459,'Khổng Lào',26,0),(460,'Than Uyên',26,0),(461,'Tân Uyên',26,0),(462,'Mường Khoa',26,0),(463,'Nậm Sỏ',26,0),(464,'Pắc Ta',26,0),(465,'Mường Than',26,0),(466,'Mường Kim',26,0),(467,'Khoen On',26,0),(468,'Tô Hiệu',28,0),(469,'Chiềng An',28,0),(470,'Chiềng Cơi',28,0),(471,'Chiềng Sinh',28,0),(472,'Mường Chiên',28,0),(473,'Mường Giôn',28,0),(474,'Quỳnh Nhai',28,0),(475,'Mường Sại',28,0),(476,'Thuận Châu',28,0),(477,'Bình Thuận',28,0),(478,'Mường É',28,0),(479,'Chiềng La',28,0),(480,'Mường Khiêng',28,0),(481,'Mường Bám',28,0),(482,'Long Hẹ',28,0),(483,'Co Mạ',28,0),(484,'Nậm Lầu',28,0),(485,'Muổi Nọi',28,0),(486,'Mường La',28,0),(487,'Chiềng Lao',28,0),(488,'Ngọc Chiến',28,0),(489,'Mường Bú',28,0),(490,'Chiềng Hoa',28,0),(491,'Bắc Yên',28,0),(492,'Xím Vàng',28,0),(493,'Tà Xùa',28,0),(494,'Pắc Ngà',28,0),(495,'Tạ Khoa',28,0),(496,'Chiềng Sại',28,0),(497,'Suối Tọ',28,0),(498,'Mường Cơi',28,0),(499,'Phù Yên',28,0),(500,'Gia Phù',28,0),(501,'Mường Bang',28,0),(502,'Tường Hạ',28,0),(503,'Kim Bon',28,0),(504,'Tân Phong',28,0),(505,'Mộc Sơn',28,0),(506,'Mộc Châu',28,0),(507,'Thảo Nguyên',28,0),(508,'Chiềng Sơn',28,0),(509,'Tân Yên',28,0),(510,'Đoàn Kết',28,0),(511,'Song Khủa',28,0),(512,'Tô Múa',28,0),(513,'Vân Sơn',28,0),(514,'Lóng Sập',28,0),(515,'Vân Hồ',28,0),(516,'Xuân Nha',28,0),(517,'Yên Châu',28,0),(518,'Chiềng Hặc',28,0),(519,'Yên Sơn',28,0),(520,'Lóng Phiêng',28,0),(521,'Phiêng Khoài',28,0),(522,'Mai Sơn',28,0),(523,'Chiềng Sung',28,0),(524,'Mường Chanh',28,0),(525,'Chiềng Mung',28,0),(526,'Chiềng Mai',28,0),(527,'Tà Hộc',28,0),(528,'Phiêng Cằm',28,0),(529,'Phiêng Pằn',28,0),(530,'Sông Mã',28,0),(531,'Bó Sinh',28,0),(532,'Mường Lầm',28,0),(533,'Nậm Ty',28,0),(534,'Chiềng Sơ',28,0),(535,'Chiềng Khoong',28,0),(536,'Huổi Một',28,0),(537,'Mường Hung',28,0),(538,'Chiềng Khương',28,0),(539,'Púng Bánh',28,0),(540,'Sốp Cộp',28,0),(541,'Mường Lèo',28,0),(542,'Mường Lạn',28,0),(543,'Lào Cai',2,0),(544,'Cam Đường',2,0),(545,'Hợp Thành',2,0),(546,'Bát Xát',2,0),(547,'A Mú Sung',2,0),(548,'Trịnh Tường',2,0),(549,'Y Tý',2,0),(550,'Dền Sáng',2,0),(551,'Bản Xèo',2,0),(552,'Mường Hum',2,0),(553,'Cốc San',2,0),(554,'Pha Long',2,0),(555,'Mường Khương',2,0),(556,'Cao Sơn',2,0),(557,'Bản Lầu',2,0),(558,'Si Ma Cai',2,0),(559,'Sín Chéng',2,0),(560,'Bắc Hà',2,0),(561,'Tả Củ Tỷ',2,0),(562,'Lùng Phình',2,0),(563,'Bản Liền',2,0),(564,'Bảo Nhai',2,0),(565,'Cốc Lầu',2,0),(566,'Phong Hải',2,0),(567,'Bảo Thắng',2,0),(568,'Tằng Loỏng',2,0),(569,'Gia Phú',2,0),(570,'Xuân Quang',2,0),(571,'Bảo Yên',2,0),(572,'Nghĩa Đô',2,0),(573,'Xuân Hòa',2,0),(574,'Thượng Hà',2,0),(575,'Bảo Hà',2,0),(576,'Phúc Khánh',2,0),(577,'Ngũ Chỉ Sơn',2,0),(578,'Sa Pa',2,0),(579,'Tả Phìn',2,0),(580,'Tả Van',2,0),(581,'Mường Bo',2,0),(582,'Bản Hồ',2,0),(583,'Võ Lao',2,0),(584,'Nậm Chày',2,0),(585,'Văn Bàn',2,0),(586,'Nậm Xé',2,0),(587,'Chiềng Ken',2,0),(588,'Khánh Yên',2,0),(589,'Dương Quỳ',2,0),(590,'Minh Lương',2,0),(591,'Yên Bái',2,0),(592,'Nam Cường',2,0),(593,'Văn Phú',2,0),(594,'Nghĩa Lộ',2,0),(595,'Lục Yên',2,0),(596,'Lâm Thượng',2,0),(597,'Tân Lĩnh',2,0),(598,'Khánh Hòa',2,0),(599,'Mường Lai',2,0),(600,'Phúc Lợi',2,0),(601,'Mậu A',2,0),(602,'Lâm Giang',2,0),(603,'Châu Quế',2,0),(604,'Đông Cuông',2,0),(605,'Phong Dụ Hạ',2,0),(606,'Phong Dụ Thượng',2,0),(607,'Tân Hợp',2,0),(608,'Xuân Ái',2,0),(609,'Mỏ Vàng',2,0),(610,'Mù Cang Chải',2,0),(611,'Nậm Có',2,0),(612,'Khao Mang',2,0),(613,'Lao Chải',2,0),(614,'Chế Tạo',2,0),(615,'Púng Luông',2,0),(616,'Trấn Yên',2,0),(617,'Quy Mông',2,0),(618,'Lương Thịnh',2,0),(619,'Âu Lâu',2,0),(620,'Việt Hồng',2,0),(621,'Hưng Khánh',2,0),(622,'Hạnh Phúc',2,0),(623,'Tà Xi Láng',2,0),(624,'Trạm Tấu',2,0),(625,'Phình Hồ',2,0),(626,'Tú Lệ',2,0),(627,'Gia Hội',2,0),(628,'Sơn Lương',2,0),(629,'Liên Sơn',2,0),(630,'Trung Tâm',2,0),(631,'Văn Chấn',2,0),(632,'Cầu Thia',2,0),(633,'Cát Thịnh',2,0),(634,'Chấn Thịnh',2,0),(635,'Thượng Bằng La',2,0),(636,'Nghĩa Tâm',2,0),(637,'Yên Bình',2,0),(638,'Thác Bà',2,0),(639,'Cảm Nhân',2,0),(640,'Yên Thành',2,0),(641,'Bảo Ái',2,0),(642,'Đức Xuân',3,0),(643,'Bắc Kạn',3,0),(644,'Phong Quang',3,0),(645,'Bằng Thành',3,0),(646,'Cao Minh',3,0),(647,'Nghiên Loan',3,0),(648,'Phúc Lộc',3,0),(649,'Ba Bể',3,0),(650,'Chợ Rã',3,0),(651,'Thượng Minh',3,0),(652,'Đồng Phúc',3,0),(653,'Nà Phặc',3,0),(654,'Bằng Vân',3,0),(655,'Ngân Sơn',3,0),(656,'Thượng Quan',3,0),(657,'Hiệp Lực',3,0),(658,'Phủ Thông',3,0),(659,'Vĩnh Thông',3,0),(660,'Cẩm Giàng',3,0),(661,'Bạch Thông',3,0),(662,'Chợ Đồn',3,0),(663,'Nam Cường',3,0),(664,'Quảng Bạch',3,0),(665,'Yên Thịnh',3,0),(666,'Nghĩa Tá',3,0),(667,'Yên Phong',3,0),(668,'Chợ Mới',3,0),(669,'Thanh Mai',3,0),(670,'Tân Kỳ',3,0),(671,'Thanh Thịnh',3,0),(672,'Yên Bình',3,0),(673,'Văn Lang',3,0),(674,'Cường Lợi',3,0),(675,'Na Rì',3,0),(676,'Trần Phú',3,0),(677,'Côn Minh',3,0),(678,'Xuân Dương',3,0),(679,'Phan Đình Phùng',3,0),(680,'Quyết Thắng',3,0),(681,'Gia Sàng',3,0),(682,'Quan Triều',3,0),(683,'Đại Phúc',3,0),(684,'Tích Lương',3,0),(685,'Tân Cương',3,0),(686,'Sông Công',3,0),(687,'Bách Quang',3,0),(688,'Bá Xuyên',3,0),(689,'Lam Vỹ',3,0),(690,'Kim Phượng',3,0),(691,'Phượng Tiến',3,0),(692,'Định Hóa',3,0),(693,'Trung Hội',3,0),(694,'Bình Yên',3,0),(695,'Phú Đình',3,0),(696,'Bình Thành',3,0),(697,'Phú Lương',3,0),(698,'Yên Trạch',3,0),(699,'Hợp Thành',3,0),(700,'Vô Tranh',3,0),(701,'Trại Cau',3,0),(702,'Văn Lăng',3,0),(703,'Quang Sơn',3,0),(704,'Văn Hán',3,0),(705,'Đồng Hỷ',3,0),(706,'Nam Hòa',3,0),(707,'Linh Sơn',3,0),(708,'Võ Nhai',3,0),(709,'Sảng Mộc',3,0),(710,'Nghinh Tường',3,0),(711,'Thần Sa',3,0),(712,'La Hiên',3,0),(713,'Tràng Xá',3,0),(714,'Dân Tiến',3,0),(715,'Phú Xuyên',3,0),(716,'Đức Lương',3,0),(717,'Phú Lạc',3,0),(718,'Phú Thịnh',3,0),(719,'An Khánh',3,0),(720,'La Bằng',3,0),(721,'Đại Từ',3,0),(722,'Vạn Phú',3,0),(723,'Quân Chu',3,0),(724,'Phúc Thuận',3,0),(725,'Phổ Yên',3,0),(726,'Thành Công',3,0),(727,'Vạn Xuân',3,0),(728,'Trung Thành',3,0),(729,'Phú Bình',3,0),(730,'Tân Khánh',3,0),(731,'Tân Thành',3,0),(732,'Điềm Thụy',3,0),(733,'Kha Sơn',3,0),(734,'Đông Kinh',29,0),(735,'Lương Văn Tri',29,0),(736,'Tam Thanh',29,0),(737,'Đoàn Kết',29,0),(738,'Quốc Khánh',29,0),(739,'Tân Tiến',29,0),(740,'Kháng Chiến',29,0),(741,'Thất Khê',29,0),(742,'Tràng Định',29,0),(743,'Quốc Việt',29,0),(744,'Hoa Thám',29,0),(745,'Quý Hòa',29,0),(746,'Hồng Phong',29,0),(747,'Thiện Hòa',29,0),(748,'Thiện Thuật',29,0),(749,'Thiện Long',29,0),(750,'Bình Gia',29,0),(751,'Tân Văn',29,0),(752,'Na Sầm',29,0),(753,'Thụy Hùng',29,0),(754,'Hội Hoan',29,0),(755,'Văn Lãng',29,0),(756,'Hoàng Văn Thụ',29,0),(757,'Đồng Đăng',29,0),(758,'Kỳ Lừa',29,0),(759,'Ba Sơn',29,0),(760,'Cao Lộc',29,0),(761,'Công Sơn',29,0),(762,'Văn Quan',29,0),(763,'Điềm He',29,0),(764,'Khánh Khê',29,0),(765,'Yên Phúc',29,0),(766,'Tri Lễ',29,0),(767,'Tân Đoàn',29,0),(768,'Bắc Sơn',29,0),(769,'Tân Tri',29,0),(770,'Hưng Vũ',29,0),(771,'Vũ Lễ',29,0),(772,'Vũ Lăng',29,0),(773,'Nhất Hòa',29,0),(774,'Hữu Lũng',29,0),(775,'Yên Bình',29,0),(776,'Hữu Liên',29,0),(777,'Vân Nham',29,0),(778,'Cai Kinh',29,0),(779,'Thiện Tân',29,0),(780,'Tân Thành',29,0),(781,'Tuấn Sơn',29,0),(782,'Chi Lăng',29,0),(783,'Bằng Mạc',29,0),(784,'Chiến Thắng',29,0),(785,'Nhân Lý',29,0),(786,'Vạn Linh',29,0),(787,'Quan Sơn',29,0),(788,'Na Dương',29,0),(789,'Lộc Bình',29,0),(790,'Mẫu Sơn',29,0),(791,'Khuất Xá',29,0),(792,'Thống Nhất',29,0),(793,'Lợi Bác',29,0),(794,'Xuân Dương',29,0),(795,'Đình Lập',29,0),(796,'Thái Bình',29,0),(797,'Kiên Mộc',29,0),(798,'Châu Sơn',29,0),(799,'Hà Tu',30,0),(800,'Cao Xanh',30,0),(801,'Việt Hưng',30,0),(802,'Bãi Cháy',30,0),(803,'Hà Lầm',30,0),(804,'Hồng Gai',30,0),(805,'Hạ Long',30,0),(806,'Tuần Châu',30,0),(807,'Móng Cái 2',30,0),(808,'Móng Cái 1',30,0),(809,'Hải Sơn',30,0),(810,'Hải Ninh',30,0),(811,'Móng Cái 3',30,0),(812,'Vĩnh Thực',30,0),(813,'Mông Dương',30,0),(814,'Quang Hanh',30,0),(815,'Cửa Ông',30,0),(816,'Cẩm Phả',30,0),(817,'Hải Hòa',30,0),(818,'Uông Bí',30,0),(819,'Vàng Danh',30,0),(820,'Yên Tử',30,0),(821,'Bình Liêu',30,0),(822,'Hoành Mô',30,0),(823,'Lục Hồn',30,0),(824,'Tiên Yên',30,0),(825,'Điền Xá',30,0),(826,'Đông Ngũ',30,0),(827,'Hải Lạng',30,0),(828,'Đầm Hà',30,0),(829,'Quảng Tân',30,0),(830,'Quảng Hà',30,0),(831,'Quảng Đức',30,0),(832,'Đường Hoa',30,0),(833,'Cái Chiên',30,0),(834,'Ba Chẽ',30,0),(835,'Kỳ Thượng',30,0),(836,'Lương Minh',30,0),(837,'Đặc khu Vân Đồn',30,0),(838,'Hoành Bồ',30,0),(839,'Quảng La',30,0),(840,'Thống Nhất',30,0),(841,'Mạo Khê',30,0),(842,'Bình Khê',30,0),(843,'An Sinh',30,0),(844,'Đông Triều',30,0),(845,'Hoàng Quế',30,0),(846,'Quảng Yên',30,0),(847,'Đông Mai',30,0),(848,'Hiệp Hòa',30,0),(849,'Hà An',30,0),(850,'Liên Hòa',30,0),(851,'Phong Cốc',30,0),(852,'Đặc khu Cô Tô',30,0);
/*!40000 ALTER TABLE `province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '',
  `parent_id` int unsigned DEFAULT '0',
  `level` tinyint unsigned DEFAULT '0',
  `type` tinyint unsigned DEFAULT '0',
  `status` tinyint unsigned DEFAULT '0',
  `created_at` int unsigned DEFAULT '0',
  `updated_at` int unsigned DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'Printgo',0,1,0,1,0,0),(2,'Pakgo',0,1,0,1,0,0),(3,'Checkgo',0,1,0,1,0,0);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_history`
--

DROP TABLE IF EXISTS `shipping_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sso_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `receiver_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `receiver_mobile` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `detail_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `warehouse_id` int DEFAULT NULL,
  `transport_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `transport_id` int DEFAULT NULL,
  `transport_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `shipping_cost` int DEFAULT '0',
  `quantity` int DEFAULT '0',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'thoi gian giao',
  `province_id` int DEFAULT NULL,
  `ward_id` int DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_code_index` (`order_code`)
) ENGINE=InnoDB AUTO_INCREMENT=11467 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_history`
--

LOCK TABLES `shipping_history` WRITE;
/*!40000 ALTER TABLE `shipping_history` DISABLE KEYS */;
INSERT INTO `shipping_history` VALUES (11464,'Administrator','Pép DiaLa','098545555','OWLD2725IWD','OWLD2725IWD-1',276,'08.08',10008,'Viettel',20000,20,'2025-08-09 23:46:39',1,216,'CT07','Giao 10 - 12h',4),(11465,'ThanhTrung','Thu Thao Thu Bui','0979046332','ORHX3525TDG','ORHX3525TDG-1',279,'VT-123445663',10008,'Viettel',245000,100,'2025-08-27 15:48:46',24,50,'Xuân Quỳnh Trung Hòa',NULL,1),(11466,'LongHuu','Chị Uyên','0976265571','OGFW3625HTF','OGFW3625HTF-1',279,'VT-123445662',10008,'Viettel',400000,200,'2025-08-28 02:42:25',24,50,'54 Trương Công Giai, Cầu Giấy, Hà Nội, Việt Nam  ',NULL,2);
/*!40000 ALTER TABLE `shipping_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_status`
--

DROP TABLE IF EXISTS `shipping_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_status`
--

LOCK TABLES `shipping_status` WRITE;
/*!40000 ALTER TABLE `shipping_status` DISABLE KEYS */;
INSERT INTO `shipping_status` VALUES (1,'Đóng hàng',0),(2,'Giao nv giao hàng',0),(3,'Trung chuyển',0),(4,'Giao hàng cho KH',0);
/*!40000 ALTER TABLE `shipping_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transporter`
--

DROP TABLE IF EXISTS `transporter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transporter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10010 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transporter`
--

LOCK TABLES `transporter` WRITE;
/*!40000 ALTER TABLE `transporter` DISABLE KEYS */;
INSERT INTO `transporter` VALUES (10008,'Viettel'),(10009,'Văn Phòng');
/*!40000 ALTER TABLE `transporter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sso_id` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firebase_token` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `layout` varchar(255) DEFAULT '',
  `full_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone` varchar(20) DEFAULT '',
  `email` varchar(30) NOT NULL,
  `status` int DEFAULT '0',
  `avatar` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sso_id` (`sso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1645 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'Administrator','$2a$10$GhyjCt8X1xA/staPlqAMFOOqqbMB3qKVAkSI56GJf8PT/txXARC8.','fqSSpT_mou2h1B_ygwekc1:APA91bE3xxIiIMzFivcG6liPBlW-6CspSPwAo4yQ6bXY8h4Y_Y9XdoITmRF-URsXm8KUhS71f6km37Kx8JnDeIJ8e2E21-4Wt9X-e7p2aL6YnIPTAmgRnv4qF16aR6vBrxQNtuZ6WIRc','UserLayout','Administrator','0987938491','flast.vn@gmail.com',1,NULL,NULL),(67,'LongHuu','$2a$10$GhyjCt8X1xA/staPlqAMFOOqqbMB3qKVAkSI56GJf8PT/txXARC8.','d-y-L_k6plm_7iOjoPY--v:APA91bHNtUmk-Yitl0xGIc3lRCgzQRH7ySXFhV5IhTunuk6vbeYFtuDpiGMdqTW8rtWJxZgFDjyJpGHPhKhG5g33KNOH7IafbPp8S9MFIGruIU0ZJaW5VpNJ6jToBIKyj4JNM8BROxqv','SaleLayout','Hữu Long','0987938491','long.huu.100@gmail.com',1,NULL,NULL),(1638,'ThanhTrung','$2a$10$i06JFYyA/3sIzdo32Fo3uumAOUGYoYDSupXvUPRemL3VByp6XZAyu',NULL,'SaleLayout','Thành Trung','03435223731','thanhtrung@gmail.com',1,NULL,NULL),(1639,'HungDB','$2a$10$ua0R48oisKWrAhkzAd9IO.hlWcmzyTumo7QTqWzGFf309e6skp8s.',NULL,'saleLayout','Bùi Đức Hùng','09634','hungbd271276@gmail.com',1,NULL,NULL),(1643,'AgentA2A','$2a$10$GhyjCt8X1xA/staPlqAMFOOqqbMB3qKVAkSI56GJf8PT/txXARC8.','d-y-L_k6plm_7iOjoPY--v:APA91bHNtUmk-Yitl0xGIc3lRCgzQRH7ySXFhV5IhTunuk6vbeYFtuDpiGMdqTW8rtWJxZgFDjyJpGHPhKhG5g33KNOH7IafbPp8S9MFIGruIU0ZJaW5VpNJ6jToBIKyj4JNM8BROxqv','SaleLayout','AiAgent','','saleai@gmail.com',1,NULL,NULL),(1644,'MoNT','$2a$10$Bymdaysn36Fs116o.j7sfuJCebh5AIW1AttJ9ZZ8E3kzDlgy0/YXG',NULL,'SaleLayout','NGUYỄN THỊ MƠ','0911837658','mont@gmail.com',2,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `member_number` int DEFAULT '0',
  `member_list` json NOT NULL,
  `leader_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `department` varchar(100) NOT NULL,
  `leader_id` int DEFAULT '0',
  `status` tinyint(1) DEFAULT '0',
  `in_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `leader_id` (`leader_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` VALUES (16,'SAM - TrangBTH',2,'[2, 67]','LongHuu','Kinhdoanh',67,1,'2020-02-03 21:25:50'),(18,'SAM - LuuHT',3,'[1638, 1643, 1639]','Administrator','Kinhdoanh',2,2,'2020-02-03 21:25:50'),(19,'SALE - HaNoi',2,'[1638, 1643]','ThanhTrung','Kinhdoanh',1638,2,'2025-05-14 22:34:34'),(20,'Kevin',3,'[67, 1638, 1639]','Kevin2','Kinhdoanh',1639,1,'2025-05-17 23:24:58');
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_kpi`
--

DROP TABLE IF EXISTS `user_kpi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_kpi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `user_id` int NOT NULL DEFAULT '0',
  `month` int NOT NULL DEFAULT '0',
  `year` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1025 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_kpi`
--

LOCK TABLES `user_kpi` WRITE;
/*!40000 ALTER TABLE `user_kpi` DISABLE KEYS */;
INSERT INTO `user_kpi` VALUES (1021,'[{\"name\":\"doanhso\",\"target\":200000000,\"current\":0},{\"name\":\"sql\",\"target\":300,\"current\":0}]',67,8,2025),(1022,'[{\"name\":\"doanhso\",\"target\":500000000,\"current\":0},{\"name\":\"sql\",\"target\":500,\"current\":0}]',1638,8,2025),(1023,'[{\"name\":\"trafic\",\"target\":1000000,\"current\":0},{\"name\":\"sql\",\"target\":50,\"current\":0}]',1639,8,2025),(1024,'[{\"name\":\"sql\",\"target\":300,\"current\":0},{\"name\":\"trafic\",\"target\":200000,\"current\":0}]',1643,8,2025);
/*!40000 ALTER TABLE `user_kpi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_link_profile`
--

DROP TABLE IF EXISTS `user_link_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_link_profile` (
  `user_id` bigint NOT NULL,
  `user_profile_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`user_profile_id`),
  KEY `FK_USER_PROFILE` (`user_profile_id`),
  CONSTRAINT `FK_APP_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_USER_PROFILE` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_link_profile`
--

LOCK TABLES `user_link_profile` WRITE;
/*!40000 ALTER TABLE `user_link_profile` DISABLE KEYS */;
INSERT INTO `user_link_profile` VALUES (67,1),(1638,1),(2,2),(67,2),(1639,2),(1638,5),(1639,5),(1643,5),(1644,5),(67,13),(67,16);
/*!40000 ALTER TABLE `user_link_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_permision`
--

DROP TABLE IF EXISTS `user_permision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_permision` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(100) NOT NULL,
  `roles` varchar(255) NOT NULL DEFAULT 'hasRole(''USER'')',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permision`
--

LOCK TABLES `user_permision` WRITE;
/*!40000 ALTER TABLE `user_permision` DISABLE KEYS */;
INSERT INTO `user_permision` VALUES (16,'/data/**','any'),(20,'/user-group/**','any'),(28,'/sale/list-file','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(38,'/sale/lists-data','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(39,'/sale/lists-order','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(40,'/sale/list-order-types','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(42,'/sale/create-order-detail','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(43,'/sale/update-order-detail','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(44,'/sale/update-order','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(46,'/sale/delete-order','hasRole(\'ADMIN\')'),(47,'/sale/delete-order-detail','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(51,'/sale/report','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(52,'/sale/uploads','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(56,'/sale/month-sale-report','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(57,'/sale/weekly-sale-report','hasRole(\'ADMIN\') or hasRole(\'SALE\')'),(63,'/customer/**','any'),(64,'/admin/**','hasRole(\'ADMIN\') '),(67,'/sale/lists-cohoi','hasRole(\'ADMIN\') or hasRole(\'SALE\')');
/*!40000 ALTER TABLE `user_permision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (2,'ROLE_ADMIN'),(16,'ROLE_CSKH'),(3,'ROLE_DBA'),(5,'ROLE_SALE'),(13,'ROLE_SALE_MANAGER'),(1,'ROLE_USER'),(17,'ROLE_WAREHOUSE');
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_exchange`
--

DROP TABLE IF EXISTS `warehouse_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_exchange` (
  `id` int NOT NULL AUTO_INCREMENT,
  `warehouse_target_id` int NOT NULL,
  `warehouse_source_id` int NOT NULL,
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `quantity` int NOT NULL,
  `sso_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `in_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_exchange`
--

LOCK TABLES `warehouse_exchange` WRITE;
/*!40000 ALTER TABLE `warehouse_exchange` DISABLE KEYS */;
INSERT INTO `warehouse_exchange` VALUES (48,4,274,NULL,2,'Administrator','2025-08-01 02:40:06','2025-08-01 02:40:06',0),(49,4,274,NULL,2,'Administrator','2025-08-01 03:01:30','2025-08-01 03:01:30',0);
/*!40000 ALTER TABLE `warehouse_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_history`
--

DROP TABLE IF EXISTS `warehouse_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_history` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `warehouser_product_id` bigint NOT NULL,
  `user_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `sku_info` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `fee` int DEFAULT '0',
  `provider_id` int DEFAULT NULL,
  `stock_id` int DEFAULT NULL,
  `stock_name` varchar(50) DEFAULT NULL,
  `quality` int NOT NULL,
  `status` int DEFAULT NULL,
  `in_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `product_retain_idx` (`warehouser_product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_history`
--

LOCK TABLES `warehouse_history` WRITE;
/*!40000 ALTER TABLE `warehouse_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_product`
--

DROP TABLE IF EXISTS `warehouse_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_product` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `stock_id` int DEFAULT NULL,
  `stock_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `product_id` int NOT NULL,
  `sku_id` int NOT NULL,
  `sku_info` varchar(255) DEFAULT NULL,
  `fee` int DEFAULT '0',
  `quantity` int DEFAULT '0',
  `total` int DEFAULT '0',
  `sku_hash` varchar(500) NOT NULL,
  `sku_name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `provider_id` int DEFAULT NULL,
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `product_idx` (`product_id`),
  KEY `sku_idx` (`sku_id`),
  KEY `stock_idx` (`stock_id`),
  KEY `index_stock_hash` (`sku_hash`,`stock_id`)
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_product`
--

LOCK TABLES `warehouse_product` WRITE;
/*!40000 ALTER TABLE `warehouse_product` DISABLE KEYS */;
INSERT INTO `warehouse_product` VALUES (274,5,'Kho Nam Từ Liêm','Administrator',776,10208,'[{\"text\":\"Dạng kết cấu\",\"values\":[{\"id\":10385,\"text\":\"Hai tầng\"},{\"id\":10383,\"text\":\"Âm dương nắp lật\"}]},{\"text\":\"Bồi vỏ\",\"values\":[{\"id\":10389,\"text\":\"Giấy Mỹ thuật\"},{\"id\":10388,\"text\":\"Giấy Crat\"}]}]',0,11,11,'5d5c2436157d19043dcf4857ae9a258f6aae27b5','Hộp 1.3mm',10008,'2025-07-14 04:04:44'),(275,1,'Kho Trung Hòa','Administrator',776,10209,'[{\"text\":\"Dạng kết cấu\",\"values\":[{\"id\":10395,\"text\":\"Hai cánh mở giữa\"}]},{\"text\":\"Bồi vỏ\",\"values\":[{\"id\":10399,\"text\":\"Giấy Op/Ford\"}]}]',0,20,200,'8c34ae8d93c5210260e785b246821897d5f862b0','Hộp 1.5mm',10009,'2025-07-14 21:55:56'),(276,4,'Kho Trung Hòa 3','Administrator',774,10198,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10341,\"text\":\"Tối giản B+\"}]}]',0,78,100,'1b62a89accfa05b69453e6fd0171691f40c7b626','Thuộc tính gia công và đóng gói',10009,'2025-07-16 03:51:20'),(278,4,'Kho Trung Hòa 3','Administrator',776,10208,'[{\"text\":\"Dạng kết cấu\",\"values\":[{\"id\":10385,\"text\":\"Hai tầng\"},{\"id\":10383,\"text\":\"Âm dương nắp lật\"}]},{\"text\":\"Bồi vỏ\",\"values\":[{\"id\":10389,\"text\":\"Giấy Mỹ thuật\"},{\"id\":10388,\"text\":\"Giấy Crat\"}]}]',0,4,4,'5d5c2436157d19043dcf4857ae9a258f6aae27b5','Hộp 1.3mm',10008,'2025-08-01 02:40:06'),(279,1,'Kho Trung Hòa','LongHuu',771,10200,'[{\"text\":\"PHong cách\",\"values\":[{\"id\":10344,\"text\":\"Sang trọng\"}]},{\"text\":\"Kích thước\",\"values\":[{\"id\":10345,\"text\":\"30x55x30\"}]}]',0,1700,2000,'8173ceeb5fdf262d13ae410b52d7f90c2014e443','Hộp cứng vát',10008,'2025-08-27 10:22:39');
/*!40000 ALTER TABLE `warehouse_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_status`
--

DROP TABLE IF EXISTS `warehouse_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_status`
--

LOCK TABLES `warehouse_status` WRITE;
/*!40000 ALTER TABLE `warehouse_status` DISABLE KEYS */;
INSERT INTO `warehouse_status` VALUES (3,'Cần Duyệt',1,1),(4,'Đã Duyệt',1,0);
/*!40000 ALTER TABLE `warehouse_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_stock`
--

DROP TABLE IF EXISTS `warehouse_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `area` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_stock`
--

LOCK TABLES `warehouse_stock` WRITE;
/*!40000 ALTER TABLE `warehouse_stock` DISABLE KEYS */;
INSERT INTO `warehouse_stock` VALUES (1,'Kho Trung Hòa','100m2','0345223731','Trung Hòa, Cầu Giấy, Hà Nội',1),(4,'Kho Trung Hòa 3','100m2','0345223732','Trung Hòa, Cầu Giấy, Hà Nội',1),(5,'kho Nam Từ Liêm','200m','0963484761','Nam Từ Liêm Hà Nội 2',1),(6,'kho Nam Từ Liêm 4','300m','0963484766','Nam Từ Liêm Hà Nội 4',1),(7,'kho Nam Từ Liêm5','300m','0963484764','Nam Từ Liêm Hà Nội 5',1);
/*!40000 ALTER TABLE `warehouse_stock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-21 17:51:54
