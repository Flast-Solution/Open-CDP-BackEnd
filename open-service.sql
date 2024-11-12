# ************************************************************
# Sequel Ace SQL dump
# Version 20062
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# Host: 127.0.0.1 (MySQL 8.0.30)
# Database: open_service
# Generation Time: 2024-11-12 11:16:45 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table attributed
# ------------------------------------------------------------

DROP TABLE IF EXISTS `attributed`;

CREATE TABLE `attributed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10009 DEFAULT CHARSET=utf8mb3;



# Dump of table attributed_value
# ------------------------------------------------------------

DROP TABLE IF EXISTS `attributed_value`;

CREATE TABLE `attributed_value` (
  `id` int NOT NULL AUTO_INCREMENT,
  `attributed_id` int NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `status` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10010 DEFAULT CHARSET=utf8mb3;



# Dump of table category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int DEFAULT '0',
  `name` varchar(45) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `status` int DEFAULT '0',
  `icon` varchar(255) DEFAULT '',
  `image` varchar(100) DEFAULT NULL,
  `order_no` int DEFAULT '0',
  `seo_title` varchar(255) DEFAULT '',
  `seo_description` varchar(255) DEFAULT '',
  `seo_keyword` varchar(255) DEFAULT '',
  `seo_content` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8mb3;



# Dump of table customer_address
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer_address`;

CREATE TABLE `customer_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int unsigned DEFAULT '0',
  `name_address` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(100) DEFAULT '',
  `address` varchar(255) DEFAULT NULL,
  `ward_id` int unsigned DEFAULT '0',
  `district_id` int unsigned DEFAULT '0',
  `province_id` int unsigned DEFAULT '0',
  `mobile_phone` varchar(20) DEFAULT NULL,
  `is_default` tinyint unsigned DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8204 DEFAULT CHARSET=utf8mb3;



# Dump of table customer_enterprise
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer_enterprise`;

CREATE TABLE `customer_enterprise` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `total_fee` bigint DEFAULT '0',
  `contact_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `tax_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `director` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `ward_id` int unsigned DEFAULT '0',
  `district_id` int unsigned DEFAULT '0',
  `province_id` int unsigned DEFAULT '0',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `mobile_phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contract_file` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_tax_code_mobile_phone` (`tax_code`,`mobile_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;



# Dump of table customer_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer_order`;

CREATE TABLE `customer_order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `data_id` bigint DEFAULT '0',
  `service_id` int DEFAULT '0',
  `channel_id` int DEFAULT NULL,
  `enterprise_id` bigint DEFAULT '0',
  `enterprise_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `order_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `total_not_vat` bigint DEFAULT '0',
  `customer_id` int unsigned DEFAULT '0',
  `customer_receiver_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `customer_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `customer_ward_id` int unsigned DEFAULT NULL,
  `customer_district_id` int unsigned DEFAULT NULL,
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
  `flag_free_ship` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT 'false',
  `shipping_status` enum('na','danggiao','dagiao','giaoloi') CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT 'na',
  `payment_status` int DEFAULT '0',
  `cancel_at` timestamp NULL DEFAULT NULL,
  `paid_time` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `done_at` timestamp NULL DEFAULT NULL,
  `user_create_id` int unsigned DEFAULT '0',
  `user_create_username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `source` int DEFAULT NULL,
  `faulty` bit(1) DEFAULT b'0',
  `status` int unsigned DEFAULT '0',
  `type` enum('order','cohoi') CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT 'cohoi',
  `opportunity_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `enterprise_id` (`enterprise_id`),
  KEY `order_code_index` (`code`),
  KEY `user_id_index` (`user_create_id`),
  KEY `ship_idx` (`shipping_status`),
  KEY `phone_idx` (`customer_mobile_phone`),
  KEY `data_id_idx` (`data_id`),
  KEY `customer_id_idx` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33820 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;



# Dump of table customer_order_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer_order_detail`;

CREATE TABLE `customer_order_detail` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(100) COLLATE utf8mb3_unicode_ci NOT NULL,
  `customer_order_id` bigint DEFAULT '0',
  `product_id` int NOT NULL,
  `product_info` text COLLATE utf8mb3_unicode_ci,
  `sku_id` int DEFAULT '0',
  `sku_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sku_info` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `price` int DEFAULT '0',
  `price_off` int DEFAULT '0',
  `total` int DEFAULT '0',
  `ship_status` int DEFAULT '0',
  `ship_done_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_order_id` (`customer_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33820 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;



# Dump of table customer_order_note
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer_order_note`;

CREATE TABLE `customer_order_note` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT '',
  `type` varchar(30) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT '',
  `table_name` varchar(255) DEFAULT '',
  `table_id` int DEFAULT '0',
  `note` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=583 DEFAULT CHARSET=utf8mb3;



# Dump of table customer_order_status
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer_order_status`;

CREATE TABLE `customer_order_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `del_flag` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3;



# Dump of table customer_personal
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer_personal`;

CREATE TABLE `customer_personal` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL,
  `id_card` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `sale_id` varchar(100) DEFAULT '0',
  `gender` varchar(20) DEFAULT 'other',
  `source_id` int DEFAULT NULL,
  `level` tinyint(1) DEFAULT '0',
  `facebook_id` varchar(255) DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `province_id` int unsigned DEFAULT NULL,
  `district_id` int unsigned DEFAULT NULL,
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
  PRIMARY KEY (`id`),
  KEY `mobile_index` (`mobile`),
  KEY `saleId_index` (`sale_id`),
  KEY `token_idx` (`token_confirm`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;



# Dump of table data
# ------------------------------------------------------------

DROP TABLE IF EXISTS `data`;

CREATE TABLE `data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `service_id` int DEFAULT '0',
  `level` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `staff` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `province_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `source` tinyint(1) DEFAULT '0',
  `customer_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `customer_mobile` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `customer_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `customer_facebook` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `tags` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `category_id` int DEFAULT '0',
  `sale_id` int DEFAULT '0',
  `note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `assign_to` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  `from_department` tinyint(1) DEFAULT NULL,
  `is_order` tinyint DEFAULT '0',
  PRIMARY KEY (`id`,`in_time`),
  KEY `data_sale_id_index` (`sale_id`),
  KEY `data_assign_to_index` (`assign_to`),
  KEY `status_index` (`status`),
  KEY `department_index` (`from_department`),
  KEY `customer_mobile_idx` (`customer_mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=49735 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci
/*!50100 PARTITION BY RANGE (unix_timestamp(`in_time`))
(PARTITION p2020 VALUES LESS THAN (1609347599) ENGINE = InnoDB,
 PARTITION quimot2021 VALUES LESS THAN (1617209999) ENGINE = InnoDB,
 PARTITION quihai2021 VALUES LESS THAN (1625072399) ENGINE = InnoDB,
 PARTITION quiba2021 VALUES LESS THAN (1633021199) ENGINE = InnoDB,
 PARTITION quitu2021 VALUES LESS THAN (1640969999) ENGINE = InnoDB,
 PARTITION quimot2022 VALUES LESS THAN (1648745999) ENGINE = InnoDB,
 PARTITION quihai2022 VALUES LESS THAN (1656608399) ENGINE = InnoDB,
 PARTITION pfuture VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;



# Dump of table data_media
# ------------------------------------------------------------

DROP TABLE IF EXISTS `data_media`;

CREATE TABLE `data_media` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_id` int DEFAULT '0',
  `session_id` int DEFAULT '0',
  `file` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `dataIdIdx` (`data_id`),
  KEY `sessionIdIdx` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20568 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='store customer files';



# Dump of table data_owner
# ------------------------------------------------------------

DROP TABLE IF EXISTS `data_owner`;

CREATE TABLE `data_owner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_mobile` varchar(20) NOT NULL,
  `sale_id` int DEFAULT '0',
  `department_id` int DEFAULT '0',
  `sale_name` varchar(255) DEFAULT '',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `data_owner_mobile` (`customer_mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=40608 DEFAULT CHARSET=utf8mb3;



# Dump of table media
# ------------------------------------------------------------

DROP TABLE IF EXISTS `media`;

CREATE TABLE `media` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `object` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `object_id` int DEFAULT '0',
  `section_id` int DEFAULT '0',
  `i_resize` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `obj_idx` (`object`),
  KEY `o_id_idx` (`object_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2517 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# Dump of table product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int DEFAULT '0',
  `service_id` int DEFAULT '0',
  `quality_in_stock` int DEFAULT '0',
  `total_import_stock` int DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `provider_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `unit` varchar(100) DEFAULT NULL,
  `price` int DEFAULT '0',
  `price_ref` int DEFAULT '0',
  `seo_title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `seo_description` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `seo_content` text,
  `image` varchar(255) DEFAULT NULL,
  `attributed` json DEFAULT NULL,
  `social` varchar(255) DEFAULT '{"view":139,"like":0,"shear":49}',
  `status` int DEFAULT '0',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=733 DEFAULT CHARSET=utf8mb3;



# Dump of table product_attributed
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_attributed`;

CREATE TABLE `product_attributed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `attributed_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8mb3;



# Dump of table product_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_category`;

CREATE TABLE `product_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx-cmp-category_id` (`category_id`),
  KEY `idx-cmp-product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=308 DEFAULT CHARSET=utf8mb3;



# Dump of table product_image
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_image`;

CREATE TABLE `product_image` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int unsigned DEFAULT '0',
  `file_name` varchar(255) DEFAULT '',
  `section_id` int DEFAULT NULL,
  `is_featured` int unsigned DEFAULT '0',
  `is_slideshow` int unsigned DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `product_id_index` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21589 DEFAULT CHARSET=utf8mb3;



# Dump of table product_property
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_property`;

CREATE TABLE `product_property` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8mb3;



# Dump of table product_skus
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_skus`;

CREATE TABLE `product_skus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `product_id` int NOT NULL,
  `product_property_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8mb3;



# Dump of table product_skus_details
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_skus_details`;

CREATE TABLE `product_skus_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_skus_id` int NOT NULL,
  `product_id` int NOT NULL,
  `priduct_unit` varchar(255) DEFAULT '',
  `quantity_from` int NOT NULL,
  `quantity_to` int NOT NULL,
  `price_ref` int NOT NULL DEFAULT '0',
  `price` int NOT NULL DEFAULT '0',
  `price_import` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8mb3;



# Dump of table product_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_type`;

CREATE TABLE `product_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8mb3;



# Dump of table provider
# ------------------------------------------------------------

DROP TABLE IF EXISTS `provider`;

CREATE TABLE `provider` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `presentation` varchar(255) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `status` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10009 DEFAULT CHARSET=utf8mb3;



# Dump of table shipping
# ------------------------------------------------------------

DROP TABLE IF EXISTS `shipping`;

CREATE TABLE `shipping` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `stock_id` int DEFAULT NULL,
  `customer_phone` varchar(30) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `warehouse_id` int NOT NULL,
  `product_id` int DEFAULT NULL,
  `user_name` varchar(100) NOT NULL,
  `transporter_id` int NOT NULL,
  `transporter_name` varchar(255) NOT NULL,
  `transporter_code` varchar(100) DEFAULT NULL,
  `fee` int DEFAULT '0',
  `cod` bigint DEFAULT '0',
  `quality` int DEFAULT '0',
  `province_id` int DEFAULT NULL,
  `district_id` int DEFAULT NULL,
  `ward_id` int DEFAULT NULL,
  `address` text,
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `retain_idx` (`warehouse_id`),
  KEY `trans_code_idx` (`transporter_code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;



# Dump of table shipping_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `shipping_history`;

CREATE TABLE `shipping_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `detail_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `order_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `customer_mobile_phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `warehouse_id` int DEFAULT NULL,
  `transport_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `transport_id` int DEFAULT NULL,
  `shipping_cost` int DEFAULT '0',
  `quantity` int DEFAULT '0',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'thoi gian giao',
  `province_id` int DEFAULT NULL,
  `district_id` int DEFAULT NULL,
  `ward_id` int DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '[{orderDetailId, quantity}, {}...]',
  `note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `transport_id_UNIQUE` (`transport_code`),
  KEY `order_code_index` (`order_code`)
) ENGINE=InnoDB AUTO_INCREMENT=11459 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;



# Dump of table stock
# ------------------------------------------------------------

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT '',
  `mobile` varchar(20) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `province_id` int DEFAULT NULL,
  `district_id` int DEFAULT NULL,
  `ward_id` int DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `status` int unsigned DEFAULT '0',
  `in_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb3;



# Dump of table transporter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `transporter`;

CREATE TABLE `transporter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8mb3;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `sso_id` (`sso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1637 DEFAULT CHARSET=utf8mb3;



# Dump of table user_kpi
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_kpi`;

CREATE TABLE `user_kpi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `department` int DEFAULT '0',
  `type` int DEFAULT '0',
  `user_id` int NOT NULL DEFAULT '0',
  `kpi_total` int NOT NULL DEFAULT '0',
  `kpi_revenue` int NOT NULL DEFAULT '0',
  `month` int NOT NULL DEFAULT '0',
  `year` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1021 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;



# Dump of table user_link_profile
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_link_profile`;

CREATE TABLE `user_link_profile` (
  `user_id` bigint NOT NULL,
  `user_profile_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`user_profile_id`),
  KEY `FK_USER_PROFILE` (`user_profile_id`),
  CONSTRAINT `FK_APP_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_USER_PROFILE` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;



# Dump of table user_permision
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_permision`;

CREATE TABLE `user_permision` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(100) NOT NULL,
  `roles` varchar(255) NOT NULL DEFAULT 'hasRole(''USER'')',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb3;



# Dump of table user_profile
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_profile`;

CREATE TABLE `user_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;



# Dump of table warehouse
# ------------------------------------------------------------

DROP TABLE IF EXISTS `warehouse`;

CREATE TABLE `warehouse` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `stock_id` int DEFAULT NULL,
  `stock_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `product_id` int NOT NULL,
  `product_sku_id` int NOT NULL,
  `sku_name` varchar(255) DEFAULT NULL,
  `fee` int DEFAULT '0',
  `quality` int DEFAULT '0',
  `total` int DEFAULT '0',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `product_idx` (`product_id`),
  KEY `sku_idx` (`product_sku_id`),
  KEY `stock_idx` (`stock_id`)
) ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8mb3;



# Dump of table warehouse_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `warehouse_history`;

CREATE TABLE `warehouse_history` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `warehouse_retain_id` int NOT NULL,
  `stock_id` int DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `product_id` int NOT NULL,
  `product_sku_id` int NOT NULL,
  `fee` int DEFAULT '0',
  `quality` int DEFAULT '0',
  `in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `product_retain_idx` (`product_id`,`warehouse_retain_id`),
  KEY `sku_idx` (`product_sku_id`),
  KEY `stock_idx` (`stock_id`)
) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8mb3;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
