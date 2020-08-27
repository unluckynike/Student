/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : db_student

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-08-27 20:17:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `c3p0testtable`
-- ----------------------------
DROP TABLE IF EXISTS `c3p0testtable`;
CREATE TABLE `c3p0testtable` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c3p0testtable
-- ----------------------------

-- ----------------------------
-- Table structure for `clazz`
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gradeId` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gradeId` (`gradeId`),
  CONSTRAINT `clazz_ibfk_1` FOREIGN KEY (`gradeId`) REFERENCES `grade` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES ('18', '4', '计算机科学与技术18002班', '算法');
INSERT INTO `clazz` VALUES ('19', '1', '软件工程18001班', '18级学生');
INSERT INTO `clazz` VALUES ('20', '1', '软件工程18002班', '18级计算机与软件学院');
INSERT INTO `clazz` VALUES ('21', '14', '网络工程20001班', '今年新生');

-- ----------------------------
-- Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1', '软件工程18级', '好好学习');
INSERT INTO `grade` VALUES ('2', '软件工程19级', '学习C语言');
INSERT INTO `grade` VALUES ('3', '软件工程20级', '后生可畏');
INSERT INTO `grade` VALUES ('4', '计算机18级', '死磕算法');
INSERT INTO `grade` VALUES ('5', '计算机16级', '已经毕业的学长学姐');
INSERT INTO `grade` VALUES ('6', '计算机17级', '毕业设计');
INSERT INTO `grade` VALUES ('7', '计算机19级', '正在进行小学期实践');
INSERT INTO `grade` VALUES ('8', '计算机20级', '快来学习呀');
INSERT INTO `grade` VALUES ('9', '物联网19级', '无');
INSERT INTO `grade` VALUES ('11', '大数据18级', '大数据科学与技术');
INSERT INTO `grade` VALUES ('12', '软件工程17级', '');
INSERT INTO `grade` VALUES ('13', '网络工程20级', '大一新生');
INSERT INTO `grade` VALUES ('14', '网络工程19级', '小学期');
INSERT INTO `grade` VALUES ('15', '大数据20级', '开学');
INSERT INTO `grade` VALUES ('16', '人工智能18级', '百度AI');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clazzId` int(11) NOT NULL,
  `sn` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `sex` varchar(8) NOT NULL,
  `photo` varchar(128) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gradeId` (`clazzId`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`clazzId`) REFERENCES `clazz` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('22', '18', '180011598176689298', '舒傲寒', 'ghgs231', '男', '/Student/photo/default.png', '真正的大师，永远都怀着一颗学习的心');
INSERT INTO `student` VALUES ('24', '18', '180011598178562268', '雨鄢', 'asdaf', '女', '/Student/photo/1598442418415.jpg', '红袖添香伴读书');
INSERT INTO `student` VALUES ('25', '20', '180011598442443469', 'Lucy', '123', '女', '/Student/photo/110803134850343.jpg', '');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'hailin', '123');
INSERT INTO `user` VALUES ('2', 'admin', 'admin');
INSERT INTO `user` VALUES ('3', '曹孟德', '123456');
INSERT INTO `user` VALUES ('6', '孙文台', 'sunjian');
INSERT INTO `user` VALUES ('7', '曹丕', 'caopi');
INSERT INTO `user` VALUES ('8', '曹仁', 'SD2sd54');
INSERT INTO `user` VALUES ('11', 'Park', 'S2sd54');
INSERT INTO `user` VALUES ('12', 'Halen', 'S2sd54');
INSERT INTO `user` VALUES ('13', 'Alan', '1');
INSERT INTO `user` VALUES ('14', '入山又恐倾别城', '1');
INSERT INTO `user` VALUES ('15', '世间安得双全法', '1');
INSERT INTO `user` VALUES ('16', '不负如来不负卿', '1');
INSERT INTO `user` VALUES ('17', '曾虑多情梵自行', '1');
INSERT INTO `user` VALUES ('18', 'Steven', '654321');
