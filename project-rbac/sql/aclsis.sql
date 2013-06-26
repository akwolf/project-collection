/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : aclsis
Target Host     : localhost:3306
Target Database : aclsis
Date: 2009-11-07 10:12:14
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for t_acl
-- ----------------------------
DROP TABLE IF EXISTS `t_acl`;
CREATE TABLE `t_acl` (
  `id` int(11) NOT NULL auto_increment,
  `principalType` varchar(255) default NULL,
  `principalSn` int(11) default NULL,
  `resourceSn` varchar(255) default NULL,
  `aclState` varchar(255) default NULL,
  `aclTriState` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_acl
-- ----------------------------

-- ----------------------------
-- Table structure for t_module
-- ----------------------------
DROP TABLE IF EXISTS `t_module`;
CREATE TABLE `t_module` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `sn` varchar(255) default NULL,
  `url` varchar(255) default NULL,
  `orderno` int(11) default NULL,
  `pid` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_module
-- ----------------------------
INSERT INTO `t_module` VALUES ('1', '名称0', '标示0', 'url 0', '0', null);
INSERT INTO `t_module` VALUES ('2', '名称1', '标示1', 'url 1', '1', null);
INSERT INTO `t_module` VALUES ('3', '名称2', '标示2', 'url 2', '2', null);
INSERT INTO `t_module` VALUES ('4', '名称3', '标示3', 'url 3', '3', null);
INSERT INTO `t_module` VALUES ('5', '名称4', '标示4', 'url 4', '4', null);
INSERT INTO `t_module` VALUES ('6', '名称5', '标示5', 'url 5', '5', null);
INSERT INTO `t_module` VALUES ('7', '名称6', '标示6', 'url 6', '6', null);
INSERT INTO `t_module` VALUES ('8', '名称7', '标示7', 'url 7', '7', null);
INSERT INTO `t_module` VALUES ('9', '名称8', '标示8', 'url 8', '8', null);
INSERT INTO `t_module` VALUES ('10', '名称9', '标示9', 'url 9', '9', null);
INSERT INTO `t_module` VALUES ('11', '名称10', '标示10', 'url1 0', '0', null);
INSERT INTO `t_module` VALUES ('12', '名称11', '标示11', 'url1 1', '1', null);
INSERT INTO `t_module` VALUES ('13', '名称12', '标示12', 'url1 2', '2', null);
INSERT INTO `t_module` VALUES ('14', '名称13', '标示13', 'url1 3', '3', null);
INSERT INTO `t_module` VALUES ('15', '名称14', '标示14', 'url1 4', '4', '1');
INSERT INTO `t_module` VALUES ('16', '名称15', '标示15', 'url1 5', '5', '1');
INSERT INTO `t_module` VALUES ('17', '名称16', '标示16', 'url1 6', '6', '1');
INSERT INTO `t_module` VALUES ('18', '名称17', '标示17', 'url1 7', '7', '1');
INSERT INTO `t_module` VALUES ('19', '名称18', '标示18', 'url1 8', '8', '1');
INSERT INTO `t_module` VALUES ('20', '名称19', '标示19', 'url1 9', '9', '1');

-- ----------------------------
-- Table structure for t_org
-- ----------------------------
DROP TABLE IF EXISTS `t_org`;
CREATE TABLE `t_org` (
  `id` int(11) NOT NULL auto_increment,
  `description` varchar(200) default NULL,
  `name` varchar(10) NOT NULL,
  `sn` varchar(10) default NULL,
  `pid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK2163B31E7410E72` (`pid`),
  CONSTRAINT `FK2163B31E7410E72` FOREIGN KEY (`pid`) REFERENCES `t_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_org
-- ----------------------------

-- ----------------------------
-- Table structure for t_person
-- ----------------------------
DROP TABLE IF EXISTS `t_person`;
CREATE TABLE `t_person` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `sex` varchar(255) default NULL,
  `address` varchar(255) default NULL,
  `duty` varchar(255) default NULL,
  `phone` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  `orgid` int(11) default NULL,
  `userId` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_person
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `personId` varchar(255) default NULL,
  `createTime` datetime default NULL,
  `expireTime` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_userrole
-- ----------------------------
DROP TABLE IF EXISTS `t_userrole`;
CREATE TABLE `t_userrole` (
  `id` int(11) NOT NULL auto_increment,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `orderNo` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_userrole
-- ----------------------------
