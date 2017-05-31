-- MySQL dump 10.13  Distrib 5.7.16, for Win64 (x86_64)
--
-- Host: localhost    Database: chatdb
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chathistoy`
--

DROP TABLE IF EXISTS `chathistoy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chathistoy` (
  `ch_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_id` int(11) NOT NULL,
  `content` text,
  `time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ch_id`),
  KEY `f_id` (`f_id`),
  CONSTRAINT `chathistoy_ibfk_1` FOREIGN KEY (`f_id`) REFERENCES `friends` (`f_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chathistoy`
--

LOCK TABLES `chathistoy` WRITE;
/*!40000 ALTER TABLE `chathistoy` DISABLE KEYS */;
INSERT INTO `chathistoy` VALUES (222,67,'123','2017-01-06 12:18:38',1),(223,67,'hah ','2017-01-06 12:18:43',1),(224,66,'规范化','2017-01-06 12:18:58',1),(225,66,'你好','2017-01-06 12:22:06',1),(226,67,'123','2017-01-06 12:22:46',1),(227,72,'sdaf','2017-01-06 23:22:38',1),(228,72,'地方撒旦','2017-01-06 23:23:05',1),(229,72,'是打发','2017-01-06 23:23:34',1),(230,66,'阿斯蒂芬','2017-01-06 23:23:37',1),(231,72,'发多少','2017-01-06 23:23:51',1),(232,72,'是打发','2017-01-06 23:23:57',1),(233,72,'阿凡达','2017-01-06 23:24:01',1),(234,66,'啊是打发','2017-01-06 23:24:07',1),(235,72,'方法','2017-01-06 23:24:21',1),(236,72,'啥打法上','2017-01-06 23:24:27',1),(237,72,'阿斯顿发生','2017-01-06 23:24:31',1),(238,66,'是打发','2017-01-06 23:26:30',1),(239,72,'阿斯蒂芬','2017-01-06 23:26:36',1),(240,66,'大概','2017-01-06 23:26:53',1),(241,66,'2123','2017-01-06 23:27:33',1),(242,72,'123','2017-01-06 23:27:36',1),(243,72,'对方公司的','2017-01-06 23:27:53',1),(244,72,'对方公司','2017-01-06 23:27:57',1),(245,72,'个数','2017-01-06 23:28:42',1),(246,66,'啥打法上','2017-01-06 23:29:29',1),(247,72,'是打发','2017-01-06 23:29:34',1),(248,66,'阿斯蒂芬','2017-01-06 23:29:39',1),(249,66,'大师傅','2017-01-06 23:29:54',1),(250,72,'啥打法上','2017-01-06 23:30:05',1),(251,72,'是打发 ','2017-01-06 23:46:53',1),(252,66,'撒旦法','2017-01-06 23:46:58',1),(253,72,' ','2017-01-06 23:47:02',1),(254,66,'阿斯蒂芬','2017-01-06 23:47:05',1),(255,66,'撒旦法','2017-01-06 23:47:12',1),(256,72,'发斯蒂芬','2017-01-06 23:47:20',1),(257,66,'撒旦法','2017-01-06 23:47:27',1),(258,66,'发','2017-01-06 23:47:35',1),(259,72,'头会疼人','2017-01-06 23:47:39',1),(260,72,'二哥','2017-01-06 23:47:45',1),(261,66,'大华股份','2017-01-06 23:47:49',1),(262,72,' ','2017-01-06 23:47:51',1),(263,66,'对方过后','2017-01-06 23:47:58',1),(264,66,'','2017-01-06 23:48:04',0),(265,72,' 电话','2017-01-06 23:48:06',1),(266,72,'123','2017-01-07 00:01:10',1),(267,66,'123','2017-01-07 00:01:13',1),(268,66,'123','2017-01-07 00:01:42',1),(269,72,'123','2017-01-07 00:01:44',1),(270,66,'123','2017-01-07 00:01:47',1),(271,72,'123','2017-01-07 00:01:49',1),(272,72,'5465','2017-01-07 00:01:51',1),(273,66,'sdafad','2017-01-07 00:01:53',1),(274,66,'231','2017-01-07 00:02:08',1),(275,66,'123','2017-01-07 11:03:38',0),(276,66,'test1 to test2','2017-01-07 11:05:15',0);
/*!40000 ALTER TABLE `chathistoy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `my_id` varchar(50) NOT NULL,
  `friend_id` varchar(50) NOT NULL,
  PRIMARY KEY (`f_id`),
  KEY `my_id` (`my_id`),
  KEY `friend_id` (`friend_id`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`my_id`) REFERENCES `users` (`username`),
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (66,'test1','test3'),(67,'test3','test1'),(72,'test2','test3'),(73,'test3','test2'),(74,'test4','test2'),(75,'test2','test4'),(76,'test3','test4'),(77,'test4','test3'),(78,'test1','test4'),(79,'test4','test1'),(82,'test1','test2'),(83,'test2','test1');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `passwd` varchar(1000) NOT NULL,
  `question` varchar(100) NOT NULL,
  `answer` varchar(100) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('test1','123','您目前的姓名是？','wzw'),('test2','123','您目前的姓名是？','wzw'),('test3','123','您目前的姓名是？','123'),('test4','123','您目前的姓名是？','123');
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

-- Dump completed on 2017-01-07 19:49:59
