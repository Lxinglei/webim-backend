/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.21 : Database - webim
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`webim` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `webim`;

/*Table structure for table `t_friendship` */

DROP TABLE IF EXISTS `t_friendship`;

CREATE TABLE `t_friendship` (
  `friendship_id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `friend_id` bigint(20) NOT NULL COMMENT '好友ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`friendship_id`),
  KEY `fk_friendship_friend_id` (`friend_id`),
  KEY `fk_friendship_user_id` (`user_id`),
  CONSTRAINT `fk_friendship_friend_id` FOREIGN KEY (`friend_id`) REFERENCES `t_user_info` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_friendship_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user_info` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_friendship` */

insert  into `t_friendship`(`friendship_id`,`user_id`,`friend_id`,`create_time`,`last_update_time`) values ('1',13,14,'2018-07-07 20:54:49','2018-07-07 20:54:51'),('2',13,16,'2018-07-07 20:55:28','2018-07-07 20:55:30');

/*Table structure for table `t_local_auth` */

DROP TABLE IF EXISTS `t_local_auth`;

CREATE TABLE `t_local_auth` (
  `local_auth_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`local_auth_id`),
  KEY `fk_local_auth_user_id` (`user_id`),
  CONSTRAINT `fk_local_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user_info` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `t_local_auth` */

insert  into `t_local_auth`(`local_auth_id`,`user_id`,`username`,`password`,`create_time`,`last_update_time`) values (1,13,'Meteor','123456','2018-07-06 17:03:01','2018-07-06 17:03:01'),(2,14,'duanqiyan','123456','2018-07-06 17:11:30','2018-07-06 17:11:30'),(3,15,'12','123','2018-07-06 20:17:25','2018-07-06 20:17:25'),(4,16,'1221','123','2018-07-06 20:18:06','2018-07-06 20:18:06'),(5,17,'122121','123','2018-07-06 20:18:14','2018-07-06 20:18:14'),(6,18,'1221211233221312231321','12','2018-07-06 20:21:00','2018-07-06 20:21:00'),(7,19,'Meteor12','123456','2018-07-07 18:15:41','2018-07-07 18:15:41'),(8,20,'Meteor1234','123456','2018-07-07 18:47:01','2018-07-07 18:47:01');

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `message_id` varchar(64) NOT NULL COMMENT '主键',
  `sender_id` bigint(20) NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint(20) NOT NULL COMMENT '接收者ID',
  `sender_name` varchar(100) DEFAULT NULL COMMENT '发送者昵称',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '接收者昵称',
  `msg_content` text NOT NULL COMMENT '消息内容',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '消息状态，0：未读，1：已读，-1：已过期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`message_id`),
  KEY `fk_message_sender_id` (`sender_id`),
  KEY `fk_message_receiver_id` (`receiver_id`),
  CONSTRAINT `fk_message_receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `t_user_info` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_message_sender_id` FOREIGN KEY (`sender_id`) REFERENCES `t_user_info` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_message` */

/*Table structure for table `t_phone_auth` */

DROP TABLE IF EXISTS `t_phone_auth`;

CREATE TABLE `t_phone_auth` (
  `phone_auth_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`phone_auth_id`),
  KEY `fk_phone_auth_user_id` (`user_id`),
  CONSTRAINT `fk_phone_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user_info` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_phone_auth` */

/*Table structure for table `t_user_info` */

DROP TABLE IF EXISTS `t_user_info`;

CREATE TABLE `t_user_info` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `gender` int(2) NOT NULL DEFAULT '0' COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `avatar` varchar(516) DEFAULT NULL COMMENT '用户头像',
  `signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '用户状态,-1：禁用，1：启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `t_user_info` */

insert  into `t_user_info`(`user_id`,`nick_name`,`gender`,`birthday`,`avatar`,`signature`,`status`,`create_time`,`last_update_time`) values (13,NULL,0,'2018-07-06 17:03:01',NULL,NULL,1,'2018-07-06 17:03:01','2018-07-06 17:03:01'),(14,NULL,0,'2018-07-06 17:11:30',NULL,NULL,1,'2018-07-06 17:11:30','2018-07-06 17:11:30'),(15,NULL,0,'2018-07-06 20:17:24',NULL,NULL,1,'2018-07-06 20:17:24','2018-07-06 20:17:24'),(16,NULL,0,'2018-07-06 20:18:06',NULL,NULL,1,'2018-07-06 20:18:06','2018-07-06 20:18:06'),(17,NULL,0,'2018-07-06 20:18:14',NULL,NULL,1,'2018-07-06 20:18:14','2018-07-06 20:18:14'),(18,NULL,0,'2018-07-06 20:20:59',NULL,NULL,1,'2018-07-06 20:20:59','2018-07-06 20:20:59'),(19,NULL,0,'2018-07-07 18:15:41',NULL,NULL,1,'2018-07-07 18:15:41','2018-07-07 18:15:41'),(20,NULL,0,'2018-07-07 18:47:01',NULL,NULL,1,'2018-07-07 18:47:01','2018-07-07 18:47:01'),(21,NULL,0,'2018-07-07 20:01:50',NULL,NULL,1,'2018-07-07 20:01:50','2018-07-07 20:01:50');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
