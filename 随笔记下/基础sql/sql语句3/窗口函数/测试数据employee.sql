/*
 Navicat Premium Data Transfer

 Source Server         : 172.16.10.163
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 172.16.10.163:3306
 Source Schema         : db2

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 01/12/2022 09:54:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `eid` int(0) NOT NULL AUTO_INCREMENT,
  `eNAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `mgr_id` int(0) NULL DEFAULT NULL,
  `salary` double NULL DEFAULT NULL,
  PRIMARY KEY (`eid`) USING BTREE,
  INDEX `mgr_id`(`mgr_id`) USING BTREE,
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`mgr_id`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '孙悟空', 5, 9000);
INSERT INTO `employee` VALUES (2, '猪八戒', 5, 8000);
INSERT INTO `employee` VALUES (3, '沙和尚', 5, 8500);
INSERT INTO `employee` VALUES (4, '小白龙', 5, 7900);
INSERT INTO `employee` VALUES (5, '唐僧', NULL, 15000);
INSERT INTO `employee` VALUES (6, '武松', 9, 7600);
INSERT INTO `employee` VALUES (7, '李逵', 9, 7400);
INSERT INTO `employee` VALUES (8, '林冲', 9, 8100);
INSERT INTO `employee` VALUES (9, '宋江', NULL, 16000);
INSERT INTO `employee` VALUES (10, '猴子1', 1, 8000);
INSERT INTO `employee` VALUES (11, '猴子0', 1, 7000);
INSERT INTO `employee` VALUES (12, '猴子2', 1, 8000);
INSERT INTO `employee` VALUES (13, '猴子3', 1, 5000);

SET FOREIGN_KEY_CHECKS = 1;
