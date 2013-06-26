/*
Navicat MySQL Data Transfer

Source Server         : zhangh
Source Server Version : 50015
Source Host           : localhost:3306
Source Database       : slider

Target Server Type    : MYSQL
Target Server Version : 50015
File Encoding         : 65001

Date: 2012-06-16 20:06:35
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_slider`
-- ----------------------------
DROP TABLE IF EXISTS `t_slider`;
CREATE TABLE `t_slider` (
  `SliderID` int(11) NOT NULL auto_increment,
  `SliderPath` varchar(300) default NULL,
  PRIMARY KEY  (`SliderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_slider
-- ----------------------------
INSERT INTO `t_slider` VALUES ('6', 'upload/e4f4559f-4a06-4828-9082-f6ac13ee81e0.jpg');
INSERT INTO `t_slider` VALUES ('7', 'upload/833632a2-8027-47e7-b4f7-32f6bdab04e8.gif');
