/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.7.19 : Database - zuul-auth
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zuul-auth` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zuul-auth`;

/*Table structure for table `clientdetails` */

DROP TABLE IF EXISTS `clientdetails`;

CREATE TABLE `clientdetails` (
  `appId` varchar(48) NOT NULL,
  `resourceIds` varchar(256) DEFAULT NULL,
  `appSecret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `grantTypes` varchar(256) DEFAULT NULL,
  `redirectUrl` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `clientdetails` */

/*Table structure for table `oauth_access_token` */

DROP TABLE IF EXISTS `oauth_access_token`;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(48) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_access_token` */

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) values ('app',NULL,'$2a$10$89l48FqMsr7w/NL0Hy.c8eUgu50WwN2apy8P8J.VYYDg8DN3I3rRm','app','password,refresh_token',NULL,NULL,NULL,NULL,NULL,NULL),('webApp',NULL,'$2a$10$89l48FqMsr7w/NL0Hy.c8eUgu50WwN2apy8P8J.VYYDg8DN3I3rRm','app','authorization_code,password,refresh_token,client_credentials','http://www.baidu.com',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `oauth_client_token` */

DROP TABLE IF EXISTS `oauth_client_token`;

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(48) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_token` */

/*Table structure for table `oauth_code` */

DROP TABLE IF EXISTS `oauth_code`;

CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_code` */

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_refresh_token` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编码',
  `p_code` varchar(255) DEFAULT NULL COMMENT '菜单父编码',
  `p_id` varchar(255) DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `url` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `method` varchar(255) DEFAULT NULL COMMENT 'GET,POST,DELETE,PATCH',
  `is_menu` int(11) DEFAULT NULL COMMENT '是否是菜单(1.菜单。2.按钮)',
  `level` int(11) DEFAULT NULL COMMENT '菜单层级',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `status` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `FK_CODE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`code`,`p_code`,`p_id`,`name`,`url`,`method`,`is_menu`,`level`,`sort`,`status`,`icon`,`create_time`,`update_time`) values (1,'0000',NULL,NULL,'主节点','/admin',NULL,1,0,0,0,NULL,NULL,NULL),(2,'00000001','0000','1','子节点','/admin/xx',NULL,0,1,1,0,NULL,NULL,NULL),(3,'/provider/query','0000','1','子节点','/query','POST',0,1,1,0,NULL,NULL,NULL),(4,'/provider/user','0000','1','子节点','/user','GET',0,1,1,0,NULL,NULL,NULL),(5,'/consumer/hello','0000','1','子节点','/hello','GET',0,1,1,0,NULL,NULL,NULL),(6,'/consumer/user','0000','1','子节点','/user','GET',0,1,1,0,NULL,NULL,NULL),(7,'/consumer/authorized','0000','1','子节点','/authorized','GET',0,1,1,0,NULL,NULL,NULL),(8,'/mss-oauth/testzuul','0000','1','子节点','/testzuul','GET',0,1,1,1,NULL,NULL,NULL);

/*Table structure for table `sys_menu_bak` */

DROP TABLE IF EXISTS `sys_menu_bak`;

CREATE TABLE `sys_menu_bak` (
  `id` int(11) NOT NULL DEFAULT '0',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编码',
  `p_code` varchar(255) DEFAULT NULL COMMENT '菜单父编码',
  `p_id` varchar(255) DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `url` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `is_menu` int(11) DEFAULT NULL COMMENT '是否是菜单(1.菜单。2.按钮)',
  `level` int(11) DEFAULT NULL COMMENT '菜单层级',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `status` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu_bak` */

insert  into `sys_menu_bak`(`id`,`code`,`p_code`,`p_id`,`name`,`url`,`is_menu`,`level`,`sort`,`status`,`icon`,`create_time`,`update_time`) values (1,'0000',NULL,NULL,'主节点','/admin',1,0,0,0,NULL,NULL,NULL),(2,'00000001','0000','1','子节点','/admin/xx',0,1,1,0,NULL,NULL,NULL);

/*Table structure for table `sys_privilege` */

DROP TABLE IF EXISTS `sys_privilege`;

CREATE TABLE `sys_privilege` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_privilege` */

insert  into `sys_privilege`(`role_id`,`menu_id`,`create_time`) values (6,1,'2019-01-21 19:19:48'),(6,2,'2019-01-21 19:19:51'),(6,3,'2019-01-20 16:19:51'),(6,4,'2019-02-20 16:55:05'),(6,5,'2019-03-09 16:33:20'),(6,6,'2019-03-09 16:51:03'),(6,7,'2019-03-10 13:09:38'),(6,8,'2021-01-01 13:09:38');

/*Table structure for table `sys_privilege_bak` */

DROP TABLE IF EXISTS `sys_privilege_bak`;

CREATE TABLE `sys_privilege_bak` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_privilege_bak` */

insert  into `sys_privilege_bak`(`role_id`,`menu_id`,`create_time`) values (6,1,'2019-01-21 19:19:48'),(6,2,'2019-01-21 19:19:51');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `tips` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_role_name` (`name`),
  UNIQUE KEY `unique_role_value` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`value`,`tips`,`create_time`,`update_time`,`status`) values (6,'管理员','admin',NULL,'2017-06-20 15:07:13','2017-06-26 12:46:09',1),(8,'超级管理员','super',NULL,'2017-06-20 15:08:45',NULL,1),(17,'用户','user',NULL,'2017-06-28 18:50:39','2017-07-21 09:41:28',1);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(96) DEFAULT NULL,
  `salt` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`avatar`,`username`,`password`,`salt`,`name`,`birthday`,`sex`,`email`,`phone`,`status`,`create_time`,`update_time`) values (46,NULL,'super','$2a$10$89l48FqMsr7w/NL0Hy.c8eUgu50WwN2apy8P8J.VYYDg8DN3I3rRm',NULL,'超级管理员','2017-06-22 14:26:09',1,NULL,NULL,1,'2017-06-20 15:12:16','2017-09-12 14:39:48'),(48,NULL,'admin','$2a$10$89l48FqMsr7w/NL0Hy.c8eUgu50WwN2apy8P8J.VYYDg8DN3I3rRm',NULL,'管理员',NULL,1,NULL,NULL,1,'2017-06-26 17:31:41',NULL),(50,NULL,'test1','$2a$10$89l48FqMsr7w/NL0Hy.c8eUgu50WwN2apy8P8J.VYYDg8DN3I3rRm',NULL,'test1',NULL,1,NULL,NULL,1,'2017-09-18 16:11:15',NULL),(51,NULL,'test2','$2a$10$89l48FqMsr7w/NL0Hy.c8eUgu50WwN2apy8P8J.VYYDg8DN3I3rRm',NULL,'test2',NULL,1,NULL,NULL,1,'2017-09-21 17:09:51',NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`create_time`,`create_by`) values (2,48,6,'2019-01-10 22:09:43','admin');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
