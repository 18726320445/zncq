/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : zncq_db

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 20/03/2023 13:02:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for container
-- ----------------------------
DROP TABLE IF EXISTS `container`;
CREATE TABLE `container`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '库柜名称',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型',
  `max_capacity` bigint(0) NULL DEFAULT NULL COMMENT '最大容量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of container
-- ----------------------------
INSERT INTO `container` VALUES (1, '水果', '原料', 1000);
INSERT INTO `container` VALUES (2, '设备容器', '产品', 5000);
INSERT INTO `container` VALUES (3, '修改测试', NULL, 50000);
INSERT INTO `container` VALUES (4, '测试3', '原料', 1000);
INSERT INTO `container` VALUES (8, '测试5', '设备', 10);
INSERT INTO `container` VALUES (9, '测试6', '设备', 1000);
INSERT INTO `container` VALUES (10, '测试7', '设备', 1000);
INSERT INTO `container` VALUES (11, '测试8', '设备', 1000);
INSERT INTO `container` VALUES (12, '测试9', '原料', 1000);
INSERT INTO `container` VALUES (13, '库柜3', '原料', 1000000);
INSERT INTO `container` VALUES (14, '测试11', '原料', 1000);
INSERT INTO `container` VALUES (15, '测试12', '产品', 1000);
INSERT INTO `container` VALUES (16, '测试13', '产品', 1000);
INSERT INTO `container` VALUES (17, '测试14', '产品', 1000);
INSERT INTO `container` VALUES (21, '设备容器', '设备', 2000);
INSERT INTO `container` VALUES (22, '设备容器', '设备', 2000);
INSERT INTO `container` VALUES (23, '产品库柜', '产品', 10000);

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '流水',
  `contract_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '合同编号',
  `contract_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '合同名称',
  `createDate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `signDate` datetime(0) NULL DEFAULT NULL COMMENT '签署日期',
  `state` int(0) NULL DEFAULT 0 COMMENT '合同状态',
  `create_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者名称',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '简介',
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编号',
  `contract_type` int(0) NULL DEFAULT NULL COMMENT '0 设备购入合同\n1 产品出售合同',
  `customer_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `contract_contract_no_uindex`(`contract_no`) USING BTREE,
  INDEX `contract_order_order_no_fk`(`order_no`) USING BTREE,
  INDEX `contract_order_customer_id_fk`(`customer_id`) USING BTREE,
  CONSTRAINT `contract_order_customer_id_fk` FOREIGN KEY (`customer_id`) REFERENCES `order` (`customer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `contract_order_order_no_fk` FOREIGN KEY (`order_no`) REFERENCES `order` (`order_no`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contract
-- ----------------------------
INSERT INTO `contract` VALUES (1, 'D0144F75', '测试', '2023-02-25 14:29:55', '2023-02-28 10:52:30', 2, '王俊伟', '测试啊', 'e1d915bf-f910-4224-b76c-4b6f333ef09b', 1, 3);
INSERT INTO `contract` VALUES (2, '819A21B0', '方昆', '2023-02-26 21:45:30', '2023-03-10 11:01:00', 2, '方昆', '设备购入', 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 0, 2);
INSERT INTO `contract` VALUES (3, '66E26591', '黄帅的合同', '2023-02-26 21:49:05', '2023-02-28 10:57:43', 2, '黄帅', '设备购入', '44b336a2-a660-414a-94aa-3134a4455bc8', 0, 3);
INSERT INTO `contract` VALUES (4, 'D54CC23F', '方昆2', '2023-02-27 09:35:54', '2023-03-09 22:45:32', -1, '方昆', '哈哈哈', 'c1e73e10-58ed-4f6e-8aa2-637a43619829', 0, 2);
INSERT INTO `contract` VALUES (5, 'C96B5059', '好烦啊', '2023-02-27 15:28:49', '2023-02-28 10:53:58', 2, '王俊伟', '好烦好烦好烦好烦好烦好烦', '99ccfba3-4258-4393-b9aa-7c4d1e59ea7e', 1, 3);
INSERT INTO `contract` VALUES (6, '57EA8228', '产品是售出', '2023-03-04 14:30:32', '2023-03-04 14:31:49', 2, '王俊伟ddd', '测试啊', '9804fba4-30e2-43b9-ad7d-881006be9496', 1, 3);
INSERT INTO `contract` VALUES (7, 'A1938B72', '王俊伟的合同', '2023-03-09 22:26:33', '2023-03-10 11:19:36', 2, '王俊伟', 'aabbccdd', '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 1, 3);
INSERT INTO `contract` VALUES (8, 'B0BFF9B6', '测试合同', '2023-03-12 13:34:47', '2023-03-13 14:05:08', 2, '测试者', '测试人', 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 1, 3);
INSERT INTO `contract` VALUES (9, '2A333A78', '测试合同2', '2023-03-12 13:35:13', NULL, 0, '测试者2', '测试哈哈', '532c482d-f707-4d89-a4eb-96b6586545b5', 0, 3);
INSERT INTO `contract` VALUES (10, '670185B9', '王俊伟的合同', '2023-03-13 14:01:03', '2023-03-13 14:08:30', 2, '王俊伟', '哈哈哈', 'b938c099-038a-45d8-98c3-c8ec52a497b3', 1, 3);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户企业名称',
  `address` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业地址',
  `linkman` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `is_delete` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '本厂', '江苏无锡', '黄帅', '13888888888', 0);
INSERT INTO `customer` VALUES (2, '方昆公司', '江苏无锡', '王俊伟', '13988888888', 0);
INSERT INTO `customer` VALUES (3, '王俊伟集团', '江苏苏州', '王俊伟', '13988888888', 0);
INSERT INTO `customer` VALUES (4, '我是方昆', '北', '方昆', '18388888888', 1);
INSERT INTO `customer` VALUES (5, '黄帅集团', '江苏,昆山', '黄帅', '18399999999', 0);

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good`  (
  `good_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '货物编号',
  `good_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '货物名称',
  `good_type_id` int(0) NULL DEFAULT NULL COMMENT '类型编号',
  `supplier_id` int(0) NULL DEFAULT NULL COMMENT '供应商编号',
  `price` double NULL DEFAULT NULL COMMENT '单价',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '货物描述',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`good_id`) USING BTREE,
  INDEX `good_supplier_id_fk`(`supplier_id`) USING BTREE,
  INDEX `good_good_type_id_fk`(`good_type_id`) USING BTREE,
  CONSTRAINT `good_good_type_id_fk` FOREIGN KEY (`good_type_id`) REFERENCES `good_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `good_supplier_id_fk` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES (2, '水晶红富士', 1, 14, 60.2, '又红又大又甜', '/pic/5c2d8221-11c7-48a1-8c9e-84a7cc37a7ac.jpg,/pic/79451ea5-cdcb-49b3-a395-4957aa3bf10c.jpg,/pic/9f7aac6d-1892-4c47-8210-aa601a5fb20a.jpg');
INSERT INTO `good` VALUES (3, '大大西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', '/pic/218ddc73-c3f9-4ebd-bf1e-12fb141bef69.jpg,/pic/1458b233-e505-447a-89bd-b91f370b5649.jpg,/pic/470402af-431c-44cf-a9d4-704b4c2bf815.jpg');
INSERT INTO `good` VALUES (5, '柠檬', 1, 2, 10.2, '这是一个测试描述', '/pic/7669eeb6-ae67-407b-8be8-a7aa823b749b.jpg');
INSERT INTO `good` VALUES (7, '香蕉', 1, 7, 20.1, '清理肠道的好水果', '/pic/2f99b7a2-ece4-4cab-919e-0ace3e24b971.jpg');
INSERT INTO `good` VALUES (8, '牛油果', 1, 2, 405.1, '健康，减脂', NULL);
INSERT INTO `good` VALUES (9, '橘子', 1, 20, 7.5, '又甜又好吃', '/pic/a01a720d-8c8e-4380-93fc-b027d394ab16.jpg,/pic/f1ea619d-5431-4038-9b38-25952a0b04a1.jpg');
INSERT INTO `good` VALUES (10, '火龙果', 1, 21, 30.91, '红色的果子', NULL);
INSERT INTO `good` VALUES (11, '沙糖桔', 1, 2, 4.5, '小橘子甜又甜', NULL);
INSERT INTO `good` VALUES (12, '榴莲', 1, 20, 50.9, '丑但很香', NULL);
INSERT INTO `good` VALUES (22, '水仙花', 1, 3, 10.2, '水仙花啊真香啊', '/pic/2f310faa-7e4b-49a2-a4e8-2905073a083f.jpg');
INSERT INTO `good` VALUES (23, '茉莉花', 1, 3, 10.3, '茉莉花真的好香啊', '/pic/c9c44b0a-b800-4cc6-b495-451a17f29298.jpg');
INSERT INTO `good` VALUES (24, '西西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (25, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (26, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (27, '西', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (28, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (29, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (30, '大西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (31, '小西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (32, '大西瓜', 1, 3, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (33, '西瓜西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (34, '惺惺惜惺惺', 1, 3, 10.9, '测试', '/pic/80c9177e-bf32-451e-bb61-a1f0da212ec6.jpg');
INSERT INTO `good` VALUES (35, '大大大西瓜', 1, 3, 99.9, '测试', '/pic/60360a02-a2df-4790-8611-eee2bd3a04ec.jpg');
INSERT INTO `good` VALUES (36, '山竹', 1, 3, 50.5, '超甜的也好吗', NULL);
INSERT INTO `good` VALUES (48, '测试2', 1, 2, 10.4, '市场上出售', '/pic/a730877f-a6f7-48e3-8a9d-397d5873eb23.jpg');
INSERT INTO `good` VALUES (49, '测试', 1, 2, 1, '123', NULL);
INSERT INTO `good` VALUES (50, '文件测试', 1, 7, 12, '哈哈哈', NULL);
INSERT INTO `good` VALUES (51, '测试', 1, 7, 12, '谢谢谢谢', 'null');
INSERT INTO `good` VALUES (52, '120', 1, 12, 12, '12', 'null');
INSERT INTO `good` VALUES (53, '15', 1, 12, 16, '17', '/pic/a5805a8b-3761-4334-8a28-0ab1c64076d0.jpg,/pic/f0cc3364-dee8-49ea-8734-dd97dc7dd33f.jpg');
INSERT INTO `good` VALUES (56, '测试', 1, 3, 1, '测试', '/pic/406e4909-eb95-4fe3-92a2-320bad8e1742.jpg,/pic/56863d86-6170-41c6-8c84-942b39706f8f.jpg,/pic/b39445ee-bc7c-4da6-a187-ee0b9b8e9b91.jpg,/pic/b0f9de17-ef2d-41b0-9107-1e7536ef7eb6.jpg');
INSERT INTO `good` VALUES (57, '方昆', 3, 3, 4, '我服了', '/pic/0dc1c3a2-3bb5-4ad7-9cea-28c92a101ec2.jpg,/pic/cab30a67-cc8d-424b-9431-3de744637ea2.jpg,/pic/c76a1f16-afa8-4599-a8b9-9745e5dda358.jpg');
INSERT INTO `good` VALUES (60, '方昆的手', 3, 2, 1, '哈哈', '/pic/e3c988e3-5bb8-4cda-9f14-df8cc85457f0.jpg,/pic/6982fb0a-ef40-4730-909c-47b56e12d5d8.jpg,/pic/68c3d905-6192-4bb2-bf2c-098e6dc3d50e.jpg');
INSERT INTO `good` VALUES (61, '方昆', 3, 2, 16, '我服了', NULL);
INSERT INTO `good` VALUES (62, '方昆', 3, 2, 16, '我服了', NULL);
INSERT INTO `good` VALUES (63, '方昆', 3, 3, 16, '我服了', NULL);
INSERT INTO `good` VALUES (64, '方昆', 3, 3, 16, '我服了', NULL);
INSERT INTO `good` VALUES (65, '方昆', 3, 12, 16, '我服了', NULL);
INSERT INTO `good` VALUES (66, '方昆', 3, 12, 16, '我服了', NULL);
INSERT INTO `good` VALUES (70, '方昆', 3, 20, 16, '我服了', NULL);
INSERT INTO `good` VALUES (71, '方昆', 3, 21, 16, '我服了', NULL);
INSERT INTO `good` VALUES (72, '方昆', 3, 21, 16, '我服了', NULL);
INSERT INTO `good` VALUES (73, '方昆', 3, 20, 16, '我服了', NULL);
INSERT INTO `good` VALUES (74, '方昆', 3, 7, 16, '我服了', NULL);
INSERT INTO `good` VALUES (75, '方昆', 3, 12, 16, '我服了', NULL);
INSERT INTO `good` VALUES (78, '王俊伟', 2, 3, 90, '。。。', '/pic/b71826de-434e-498a-984b-dc61f38ffa44.jpg,/pic/b3a3738d-f1f3-40e2-bd7f-fe0b1319a671.jpg');
INSERT INTO `good` VALUES (80, '测试', 2, 3, 10.9, '给哈哈哈', '/pic/5da39ebd-a71c-4d99-b167-3e809d7830a3.jpg,/pic/76c03951-a1dd-4f42-8ddd-dc46c9dad355.jpg,/pic/6eef9027-a410-4b80-a2c8-df302462ff02.jpg');
INSERT INTO `good` VALUES (81, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (82, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (83, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (84, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (85, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (86, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (87, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (88, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (89, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (90, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (91, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (92, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (93, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (94, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (95, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (96, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (97, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (98, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (99, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (100, '西瓜', 1, 2, 12.8, '无籽西瓜吃到爽', NULL);
INSERT INTO `good` VALUES (101, '王俊伟的测试', 1, 3, 999, '哈啥给i', '/pic/e66c07a8-9584-41ec-ad59-4d7ea5bb007b.jpg,/pic/af680554-cf73-4a9d-9271-99119df66c42.jpg,/pic/0342c5a7-d960-4518-aec1-f01e93cd5ced.jpg');
INSERT INTO `good` VALUES (102, '产品1', 2, 20, 11.2, '产品一号', NULL);
INSERT INTO `good` VALUES (103, '产品2号', 2, 3, 12, '测试', NULL);
INSERT INTO `good` VALUES (104, '产品3', 2, 25, 13, '测试', NULL);
INSERT INTO `good` VALUES (105, '产品4', 2, 23, 14, '测试', NULL);
INSERT INTO `good` VALUES (106, '产品5', 2, 9, 15, '测试', NULL);
INSERT INTO `good` VALUES (107, '产品6', 2, 3, 16, '测试', NULL);
INSERT INTO `good` VALUES (108, '测试日志', 1, 2, 11, '测试日志', NULL);
INSERT INTO `good` VALUES (109, '测试认证', 1, 2, 13, '测试测试', NULL);
INSERT INTO `good` VALUES (110, '认证测试', 1, 2, 11, '菜市场', NULL);
INSERT INTO `good` VALUES (111, '测试日志', 1, 3, 12, '哈哈哈', NULL);
INSERT INTO `good` VALUES (112, '测试', 1, 2, 12, '从撒旦', '/pic/fdcef1cb-1839-41da-acb8-e4fb70598da1.jpg,/pic/ade3ad6b-f377-4006-99e4-09dbbdf3f26f.jpg');
INSERT INTO `good` VALUES (113, 'cs1', 1, 3, 12, 'asdsadas', NULL);
INSERT INTO `good` VALUES (114, 'cs', 1, 3, 12, 'sdasdsad', NULL);
INSERT INTO `good` VALUES (115, '好烦啊', 1, 3, 12, '213131', NULL);
INSERT INTO `good` VALUES (116, '测试', 1, 3, 12, '哈哈哈哈', NULL);
INSERT INTO `good` VALUES (117, '日志测试', 1, 2, 13, '哈哈哈', '/pic/c91c0221-6048-4cd7-9f55-a49c0236ac74.jpg,/pic/e3b00b0b-2631-464d-8b53-ad46285dc268.jpg');
INSERT INTO `good` VALUES (118, '再次测试', 1, 3, 13, '哈哈哈', NULL);

-- ----------------------------
-- Table structure for good_type
-- ----------------------------
DROP TABLE IF EXISTS `good_type`;
CREATE TABLE `good_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good_type
-- ----------------------------
INSERT INTO `good_type` VALUES (1, '原料');
INSERT INTO `good_type` VALUES (2, '产品');
INSERT INTO `good_type` VALUES (3, '设备');

-- ----------------------------
-- Table structure for in_storage
-- ----------------------------
DROP TABLE IF EXISTS `in_storage`;
CREATE TABLE `in_storage`  (
  `in_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '入库流水',
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编号',
  `good_id` int(0) NULL DEFAULT NULL COMMENT '入库货物编号',
  `num` bigint(0) NULL DEFAULT NULL COMMENT '数量',
  `warehouse_id` int(0) NULL DEFAULT NULL COMMENT '仓库编号',
  `container_id` int(0) NULL DEFAULT NULL COMMENT '库柜编号',
  `inDate` datetime(0) NULL DEFAULT NULL COMMENT '入库时间',
  PRIMARY KEY (`in_id`) USING BTREE,
  INDEX `in_storage_order_order_no_fk`(`order_no`) USING BTREE,
  INDEX `in_storage_warehouse_container_container_id_fk`(`container_id`) USING BTREE,
  INDEX `in_storage_warehouse_container_warehouse_id_container_id_fk`(`warehouse_id`, `container_id`) USING BTREE,
  CONSTRAINT `in_storage_order_order_no_fk` FOREIGN KEY (`order_no`) REFERENCES `order` (`order_no`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of in_storage
-- ----------------------------
INSERT INTO `in_storage` VALUES (2, '3e8ecc48-e4af-4bf9-bc14-a334c0c0c057', 3, 25, 1, 1, '2023-02-28 08:00:00');
INSERT INTO `in_storage` VALUES (3, '3e8ecc48-e4af-4bf9-bc14-a334c0c0c057', 7, 15, 1, 1, '2023-02-24 08:00:00');
INSERT INTO `in_storage` VALUES (22, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 60, 5, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (23, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 57, 12, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (24, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 62, 10, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (25, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 65, 6, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (26, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 60, 5, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (27, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 57, 12, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (28, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 62, 10, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (29, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 65, 6, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (30, '44b336a2-a660-414a-94aa-3134a4455bc8', 60, 5, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (31, '44b336a2-a660-414a-94aa-3134a4455bc8', 61, 5, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (32, '44b336a2-a660-414a-94aa-3134a4455bc8', 63, 5, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (33, '44b336a2-a660-414a-94aa-3134a4455bc8', 74, 6, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (34, '3e69303a-57ab-47cb-bbd8-424062a10a90', 3, 5, 3, 1, NULL);
INSERT INTO `in_storage` VALUES (35, '0b7b2bfa-cc76-4a9b-ba51-d143a528453a', 3, 5, 1, 1, NULL);
INSERT INTO `in_storage` VALUES (36, '2025dae6-1f5e-412a-bba4-a185177d86d5', 2, 3, 1, 1, NULL);
INSERT INTO `in_storage` VALUES (37, 'cab81981-f1b7-49f8-a875-b34454db6d71', 3, 10, 3, 1, NULL);
INSERT INTO `in_storage` VALUES (38, '710e5188-8e5f-4b76-a27c-d3c0de8ca342', 3, 10, 3, 1, NULL);
INSERT INTO `in_storage` VALUES (39, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 2, 5, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (40, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 3, 6, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (41, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 10, 11, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (42, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 22, 16, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (43, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 5, 11, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (44, '9df04989-ebef-4c76-a377-fee743703778', 7, 40, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (45, '9df04989-ebef-4c76-a377-fee743703778', 2, 50, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (46, '9df04989-ebef-4c76-a377-fee743703778', 3, 50, 1, 1, '2023-03-22 08:00:00');
INSERT INTO `in_storage` VALUES (47, '9df04989-ebef-4c76-a377-fee743703778', 8, 70, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (48, '9df04989-ebef-4c76-a377-fee743703778', 10, 50, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (49, '9df04989-ebef-4c76-a377-fee743703778', 11, 50, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (50, '9df04989-ebef-4c76-a377-fee743703778', 5, 50, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (51, '9df04989-ebef-4c76-a377-fee743703778', 9, 50, 1, NULL, NULL);
INSERT INTO `in_storage` VALUES (52, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 60, 5, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (53, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 57, 12, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (54, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 62, 10, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (55, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 65, 6, NULL, NULL, NULL);
INSERT INTO `in_storage` VALUES (56, '6a8e14d8-003d-4819-9094-9181f334eca0', 78, 10, 3, 2, NULL);
INSERT INTO `in_storage` VALUES (57, '5ebdbcae-7dd7-46fd-9526-e093c7c32f5a', 2, 10, 10, NULL, NULL);
INSERT INTO `in_storage` VALUES (58, '5ebdbcae-7dd7-46fd-9526-e093c7c32f5a', 5, 11, 10, NULL, NULL);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `do_time` datetime(0) NULL DEFAULT NULL,
  `process_time` int(0) NULL DEFAULT NULL,
  `args` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `result_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `detail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, 1, '192.168.21.38', '2023-03-03 21:25:32', 20, '[{\"goodName\":\"王俊伟的测试\",\"goodTypeId\":1,\"supplierId\":3,\"price\":999,\"pic\":\"/pic/e66c07a8-9584-41ec-ad59-4d7ea5bb007b.jpg,/pic/af680554-cf73-4a9d-9271-99119df66c42.jpg,/pic/0342c5a7-d960-4518-aec1-f01e93cd5ced.jpg\",\"detail\":\"哈啥给i\"},[{},{},{}]]', '添加成功', '添加原料', 'insertGood');
INSERT INTO `log` VALUES (2, 1, '192.168.21.38', '2023-03-06 22:34:38', 11, '[{\"goodName\":\"好烦啊\",\"goodTypeId\":1,\"supplierId\":3,\"price\":12,\"detail\":\"213131\"}]', '添加成功', '添加原料', 'insertGood');
INSERT INTO `log` VALUES (3, 29, '192.168.21.38', '2023-03-07 20:23:28', 13, '[{\"goodName\":\"测试\",\"goodTypeId\":1,\"supplierId\":3,\"price\":12,\"detail\":\"哈哈哈哈\"}]', '添加成功', '添加原料', 'insertGood');
INSERT INTO `log` VALUES (4, 29, '192.168.21.38', '2023-03-07 22:04:42', 19, '[{\"goodName\":\"日志测试\",\"goodTypeId\":1,\"supplierId\":2,\"price\":13,\"pic\":\"/pic/c91c0221-6048-4cd7-9f55-a49c0236ac74.jpg,/pic/e3b00b0b-2631-464d-8b53-ad46285dc268.jpg\",\"detail\":\"哈哈哈\"},[{},{}]]', '添加成功', '添加原料', 'insertGood');
INSERT INTO `log` VALUES (5, 29, '192.168.21.38', '2023-03-08 19:34:17', 18, '[{\"goodName\":\"再次测试\",\"goodTypeId\":1,\"supplierId\":3,\"price\":13,\"detail\":\"哈哈哈\"}]', '添加成功', '添加原料', 'insertGood');
INSERT INTO `log` VALUES (6, 29, '192.168.21.38', '2023-03-13 14:02:37', 9, '[\"670185B9\"]', '提交成功,等待签署中', '合同提交', 'commitContact');
INSERT INTO `log` VALUES (7, 29, '192.168.21.38', '2023-03-13 14:05:08', 15, '[{\"contractNo\":\"B0BFF9B6\",\"processName\":\"admin4\",\"vehicleId\":13}]', '合约签署成功,已发出出库通知，等待产品出库', '合同签署', 'goodAgree');
INSERT INTO `log` VALUES (8, 29, '192.168.21.38', '2023-03-13 14:08:30', 13, '[{\"contractNo\":\"670185B9\",\"processName\":\"admin4\",\"vehicleId\":17}]', '合约签署成功,已发出出库通知，等待产品出库', '合同签署', 'goodAgree');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号',
  `Originator_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '申请者',
  `customer_id` int(0) NULL DEFAULT NULL COMMENT '客户编号',
  `num` bigint(0) NULL DEFAULT 0 COMMENT '数量',
  `total` double(10, 2) NULL DEFAULT 0.00 COMMENT '总价',
  `createDate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `process_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核人',
  `state` int(0) NULL DEFAULT 0 COMMENT '订单状态\n-1 审核未通过\n0 未确定\n1 确定未审核\n2 通过计划中\n3 订单完成',
  `order_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单类型',
  `is_delete` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_order_no_uindex`(`order_no`) USING BTREE,
  INDEX `order_customer_id_fk`(`customer_id`) USING BTREE,
  CONSTRAINT `order_customer_id_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, '123123auw1a1231', NULL, 1, 0, NULL, '2022-01-02 00:00:00', NULL, 0, '采购订单', 0);
INSERT INTO `order` VALUES (2, '4a6aa4da-4887-457f-abe0-aed2d5d74a54', NULL, 1, 0, 0.00, '2023-02-20 20:32:49', NULL, 1, '采购订单', 1);
INSERT INTO `order` VALUES (3, 'fa29673c-35f0-417b-9132-a69e99bb7ac4', NULL, 1, 21, 2996.70, '2023-02-20 20:37:23', NULL, 2, '采购订单', 0);
INSERT INTO `order` VALUES (4, '3e8ecc48-e4af-4bf9-bc14-a334c0c0c057', NULL, 1, 16, 248.60, '2023-02-20 20:46:20', NULL, 2, '采购订单', 0);
INSERT INTO `order` VALUES (5, '325575c1-1dc3-43a5-b73e-31fb06b32e01', NULL, 1, 145, 2695.20, '2023-02-20 20:47:06', NULL, 3, '采购订单', 0);
INSERT INTO `order` VALUES (6, '58a570a0-95d6-4be9-ac6a-5c39d59b5cbe', NULL, 1, 29, 745.30, '2023-02-20 20:54:04', NULL, 2, '采购订单', 0);
INSERT INTO `order` VALUES (7, 'f4c20a17-b3bb-4186-9820-3a119f98a2d5', NULL, 1, 10, 200.44, '2023-02-21 18:59:04', NULL, 2, '采购订单', 0);
INSERT INTO `order` VALUES (8, 'd0fad099-31cf-4f7e-90d0-f8859fa52c02', NULL, 1, 10, 102.00, '2023-02-21 19:11:02', NULL, -1, '采购订单', 1);
INSERT INTO `order` VALUES (9, '998c3e76-2c75-4c2d-ba52-bff4ec8a0560', NULL, 1, 11, 662.20, '2023-02-21 19:20:30', NULL, 1, '采购订单', 0);
INSERT INTO `order` VALUES (10, '00a6468f-efac-48aa-b8ba-97bcb229652a', NULL, 1, 0, 0.00, '2023-02-21 19:51:38', NULL, -1, '采购订单', 1);
INSERT INTO `order` VALUES (11, '4d12152c-9ef9-4618-8425-cb6625773a8d', NULL, 1, 11, 262.20, '2023-02-22 11:01:17', NULL, 0, '采购订单', 0);
INSERT INTO `order` VALUES (12, '952a29f8-7e5c-4482-b239-7c6d31e609b0', NULL, 1, 0, 0.00, '2023-02-22 13:21:04', NULL, 0, '采购订单', 0);
INSERT INTO `order` VALUES (13, '585d976a-1817-41ce-9db4-e192ab262560', NULL, 1, 30, 6268.50, '2023-02-22 18:18:07', NULL, -1, '采购订单', 1);
INSERT INTO `order` VALUES (14, '72c25afc-3daa-45f3-95a0-bd94c41fa1b3', NULL, 1, 35, 2783.55, '2023-02-22 18:40:23', NULL, -1, '采购订单', 1);
INSERT INTO `order` VALUES (15, '5ebdbcae-7dd7-46fd-9526-e093c7c32f5a', '黄帅', 1, 21, 714.20, '2023-02-22 18:43:38', 'admin4', 2, '采购订单', 0);
INSERT INTO `order` VALUES (16, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', '张邦昌', 1, 49, 993.21, '2023-02-23 15:09:12', 'admin', 2, '采购订单', 0);
INSERT INTO `order` VALUES (17, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', '测试', 1, 16, 278.51, '2023-02-23 19:11:09', NULL, 2, '采购订单', 0);
INSERT INTO `order` VALUES (19, 'e1d915bf-f910-4224-b76c-4b6f333ef09b', '王俊伟', 3, 9, 414.50, '2023-02-25 14:29:55', '方昆', 3, '合约订单', 0);
INSERT INTO `order` VALUES (20, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', '方昆', 2, 33, 309.00, '2023-02-26 21:45:30', 'admin4', 2, '合约订单', 0);
INSERT INTO `order` VALUES (21, '44b336a2-a660-414a-94aa-3134a4455bc8', '黄帅', 3, 21, 261.00, '2023-02-26 21:49:05', '方昆', 2, '合约订单', 0);
INSERT INTO `order` VALUES (22, 'c1e73e10-58ed-4f6e-8aa2-637a43619829', '方昆', 2, 23, 368.00, '2023-02-27 09:35:54', 'admin4', -1, '合约订单', 1);
INSERT INTO `order` VALUES (23, '99ccfba3-4258-4393-b9aa-7c4d1e59ea7e', '王俊伟', 3, 11, 515.40, '2023-02-27 15:28:49', '方昆', 2, '合约订单', 0);
INSERT INTO `order` VALUES (24, '3e69303a-57ab-47cb-bbd8-424062a10a90', '方昆', 1, 0, 0.00, '2023-03-29 08:00:00', NULL, 2, '移库订单', 0);
INSERT INTO `order` VALUES (25, '0b7b2bfa-cc76-4a9b-ba51-d143a528453a', '方昆', 1, 0, 0.00, '2023-03-02 08:00:00', NULL, 2, '移库订单', 0);
INSERT INTO `order` VALUES (26, '2025dae6-1f5e-412a-bba4-a185177d86d5', '方昆', 1, 0, 0.00, '2023-03-14 08:00:00', NULL, 2, '移库订单', 0);
INSERT INTO `order` VALUES (27, 'cab81981-f1b7-49f8-a875-b34454db6d71', '方昆', 1, 0, 0.00, NULL, NULL, 2, '移库订单', 0);
INSERT INTO `order` VALUES (28, '710e5188-8e5f-4b76-a27c-d3c0de8ca342', '方昆', 1, 0, 0.00, '2023-03-02 08:00:00', 'FK', 2, '移库订单', 0);
INSERT INTO `order` VALUES (29, '1775286c-db56-448c-8329-dc4563fa98ca', NULL, 1, 0, 0.00, '2023-03-03 16:32:23', NULL, 0, '采购订单', 0);
INSERT INTO `order` VALUES (30, '452cc82b-5aa4-4d3a-aab8-efdc49fefe7c', NULL, 1, 0, 0.00, '2023-03-03 20:21:28', NULL, 0, '采购订单', 0);
INSERT INTO `order` VALUES (31, '9804fba4-30e2-43b9-ad7d-881006be9496', '王俊伟ddd', 3, 67, 915.20, '2023-03-04 14:30:32', 'admin', 2, '合约订单', 0);
INSERT INTO `order` VALUES (32, '9df04989-ebef-4c76-a377-fee743703778', '王俊伟', 1, 410, 35466.50, '2023-03-09 21:32:47', 'admin4', 3, '采购订单', 0);
INSERT INTO `order` VALUES (33, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', '王俊伟', 3, 200, 2510.00, '2023-03-09 22:26:33', 'admin4', 2, '合约订单', 0);
INSERT INTO `order` VALUES (34, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', '测试者', 3, 40, 1241.00, '2023-03-12 13:34:47', 'admin4', 2, '合约订单', 0);
INSERT INTO `order` VALUES (35, '532c482d-f707-4d89-a4eb-96b6586545b5', '测试者2', 3, 0, 0.00, '2023-03-12 13:35:13', NULL, 0, '合约订单', 0);
INSERT INTO `order` VALUES (38, '6a8e14d8-003d-4819-9094-9181f334eca0', 'admin4', 1, 0, 0.00, '2023-03-12 08:00:00', 'FK', 2, '移库订单', 0);
INSERT INTO `order` VALUES (39, 'b938c099-038a-45d8-98c3-c8ec52a497b3', '王俊伟', 3, 1120, 15532.00, '2023-03-13 14:01:03', 'admin4', 2, '合约订单', 0);

-- ----------------------------
-- Table structure for order_good
-- ----------------------------
DROP TABLE IF EXISTS `order_good`;
CREATE TABLE `order_good`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编号',
  `good_id` int(0) NULL DEFAULT NULL COMMENT '货物编号',
  `vehicle_id` int(0) NULL DEFAULT NULL COMMENT '运输车辆编号',
  `num` int(0) NULL DEFAULT NULL COMMENT '数量',
  `state` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_good_order_order_no_fk`(`order_no`) USING BTREE,
  INDEX `order_good_good_good_id_fk`(`good_id`) USING BTREE,
  INDEX `order_good_vehicle_id_fk`(`vehicle_id`) USING BTREE,
  CONSTRAINT `order_good_good_good_id_fk` FOREIGN KEY (`good_id`) REFERENCES `good` (`good_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_good_order_order_no_fk` FOREIGN KEY (`order_no`) REFERENCES `order` (`order_no`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_good
-- ----------------------------
INSERT INTO `order_good` VALUES (1, '325575c1-1dc3-43a5-b73e-31fb06b32e01', 2, 1, 15, 0);
INSERT INTO `order_good` VALUES (2, '325575c1-1dc3-43a5-b73e-31fb06b32e01', 3, 1, 20, 0);
INSERT INTO `order_good` VALUES (3, '325575c1-1dc3-43a5-b73e-31fb06b32e01', 10, 1, 20, 0);
INSERT INTO `order_good` VALUES (5, '325575c1-1dc3-43a5-b73e-31fb06b32e01', 5, 1, 90, 0);
INSERT INTO `order_good` VALUES (7, '58a570a0-95d6-4be9-ac6a-5c39d59b5cbe', 2, 1, 9, 0);
INSERT INTO `order_good` VALUES (8, '58a570a0-95d6-4be9-ac6a-5c39d59b5cbe', 3, 1, 5, 0);
INSERT INTO `order_good` VALUES (9, '58a570a0-95d6-4be9-ac6a-5c39d59b5cbe', 5, 1, 10, 0);
INSERT INTO `order_good` VALUES (10, 'd0fad099-31cf-4f7e-90d0-f8859fa52c02', 5, 1, 10, 0);
INSERT INTO `order_good` VALUES (11, 'f4c20a17-b3bb-4186-9820-3a119f98a2d5', 3, 1, 6, 0);
INSERT INTO `order_good` VALUES (13, '00a6468f-efac-48aa-b8ba-97bcb229652a', 5, 1, 5, 0);
INSERT INTO `order_good` VALUES (14, 'fa29673c-35f0-417b-9132-a69e99bb7ac4', 5, 1, 7, 0);
INSERT INTO `order_good` VALUES (15, 'fa29673c-35f0-417b-9132-a69e99bb7ac4', 8, 1, 7, 0);
INSERT INTO `order_good` VALUES (16, 'fa29673c-35f0-417b-9132-a69e99bb7ac4', 3, 1, 7, 0);
INSERT INTO `order_good` VALUES (17, '3e8ecc48-e4af-4bf9-bc14-a334c0c0c057', 3, 1, 10, 0);
INSERT INTO `order_good` VALUES (19, '3e8ecc48-e4af-4bf9-bc14-a334c0c0c057', 7, 1, 6, 0);
INSERT INTO `order_good` VALUES (20, '58a570a0-95d6-4be9-ac6a-5c39d59b5cbe', 9, 1, 5, 0);
INSERT INTO `order_good` VALUES (21, 'f4c20a17-b3bb-4186-9820-3a119f98a2d5', 10, 1, 4, 0);
INSERT INTO `order_good` VALUES (22, '998c3e76-2c75-4c2d-ba52-bff4ec8a0560', 2, 1, 11, 0);
INSERT INTO `order_good` VALUES (23, '585d976a-1817-41ce-9db4-e192ab262560', 3, 1, 5, 0);
INSERT INTO `order_good` VALUES (24, '585d976a-1817-41ce-9db4-e192ab262560', 8, 1, 15, 0);
INSERT INTO `order_good` VALUES (25, '585d976a-1817-41ce-9db4-e192ab262560', 32, 1, 10, 0);
INSERT INTO `order_good` VALUES (27, '5ebdbcae-7dd7-46fd-9526-e093c7c32f5a', 2, 1, 10, 0);
INSERT INTO `order_good` VALUES (29, '72c25afc-3daa-45f3-95a0-bd94c41fa1b3', 7, 1, 10, 0);
INSERT INTO `order_good` VALUES (30, '72c25afc-3daa-45f3-95a0-bd94c41fa1b3', 2, 1, 5, 0);
INSERT INTO `order_good` VALUES (31, '72c25afc-3daa-45f3-95a0-bd94c41fa1b3', 3, 1, 5, 0);
INSERT INTO `order_good` VALUES (33, '72c25afc-3daa-45f3-95a0-bd94c41fa1b3', 8, 1, 5, 0);
INSERT INTO `order_good` VALUES (34, '72c25afc-3daa-45f3-95a0-bd94c41fa1b3', 9, 1, 5, 0);
INSERT INTO `order_good` VALUES (35, '72c25afc-3daa-45f3-95a0-bd94c41fa1b3', 10, 1, 5, 0);
INSERT INTO `order_good` VALUES (36, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 2, 1, 5, 0);
INSERT INTO `order_good` VALUES (37, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 3, 1, 4, 0);
INSERT INTO `order_good` VALUES (38, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 5, 1, 1, 0);
INSERT INTO `order_good` VALUES (39, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 9, 1, 4, 0);
INSERT INTO `order_good` VALUES (40, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 2, 1, 1, 0);
INSERT INTO `order_good` VALUES (41, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 7, 1, 1, 0);
INSERT INTO `order_good` VALUES (42, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 10, 1, 1, 0);
INSERT INTO `order_good` VALUES (43, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 22, 1, 1, 0);
INSERT INTO `order_good` VALUES (44, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 12, 1, 1, 0);
INSERT INTO `order_good` VALUES (45, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 11, 1, 1, 0);
INSERT INTO `order_good` VALUES (46, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff', 23, 1, 1, 0);
INSERT INTO `order_good` VALUES (48, '4d12152c-9ef9-4618-8425-cb6625773a8d', 2, 1, 3, 0);
INSERT INTO `order_good` VALUES (49, '4d12152c-9ef9-4618-8425-cb6625773a8d', 5, 1, 8, 0);
INSERT INTO `order_good` VALUES (61, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 60, 1, 5, 0);
INSERT INTO `order_good` VALUES (63, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 57, 1, 12, 0);
INSERT INTO `order_good` VALUES (64, '44b336a2-a660-414a-94aa-3134a4455bc8', 60, 1, 5, 0);
INSERT INTO `order_good` VALUES (65, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 62, 1, 10, 0);
INSERT INTO `order_good` VALUES (67, '44b336a2-a660-414a-94aa-3134a4455bc8', 61, 1, 5, 0);
INSERT INTO `order_good` VALUES (68, '44b336a2-a660-414a-94aa-3134a4455bc8', 63, 1, 5, 0);
INSERT INTO `order_good` VALUES (69, '44b336a2-a660-414a-94aa-3134a4455bc8', 74, 1, 6, 0);
INSERT INTO `order_good` VALUES (70, 'c1e73e10-58ed-4f6e-8aa2-637a43619829', 61, 1, 5, 0);
INSERT INTO `order_good` VALUES (73, 'e7738e21-c442-4a62-aba0-de1ca0bdd5ff', 65, 1, 6, 0);
INSERT INTO `order_good` VALUES (74, 'e1d915bf-f910-4224-b76c-4b6f333ef09b', 80, 5, 5, 0);
INSERT INTO `order_good` VALUES (75, 'c1e73e10-58ed-4f6e-8aa2-637a43619829', 62, 1, 7, 0);
INSERT INTO `order_good` VALUES (76, 'e1d915bf-f910-4224-b76c-4b6f333ef09b', 78, 5, 4, 1);
INSERT INTO `order_good` VALUES (77, 'c1e73e10-58ed-4f6e-8aa2-637a43619829', 64, 1, 11, 0);
INSERT INTO `order_good` VALUES (78, '99ccfba3-4258-4393-b9aa-7c4d1e59ea7e', 78, 3, 5, 0);
INSERT INTO `order_good` VALUES (79, '99ccfba3-4258-4393-b9aa-7c4d1e59ea7e', 80, 3, 6, 0);
INSERT INTO `order_good` VALUES (80, '3e69303a-57ab-47cb-bbd8-424062a10a90', 3, 8, 5, 0);
INSERT INTO `order_good` VALUES (81, '0b7b2bfa-cc76-4a9b-ba51-d143a528453a', 3, 7, 5, 0);
INSERT INTO `order_good` VALUES (82, '2025dae6-1f5e-412a-bba4-a185177d86d5', 2, 9, 3, 0);
INSERT INTO `order_good` VALUES (83, 'cab81981-f1b7-49f8-a875-b34454db6d71', 3, 24, 10, 0);
INSERT INTO `order_good` VALUES (84, '710e5188-8e5f-4b76-a27c-d3c0de8ca342', 3, 10, 10, 1);
INSERT INTO `order_good` VALUES (85, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 3, 1, 6, 0);
INSERT INTO `order_good` VALUES (86, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 10, 1, 11, 0);
INSERT INTO `order_good` VALUES (87, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 22, 1, 16, 0);
INSERT INTO `order_good` VALUES (88, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4', 5, 1, 11, 0);
INSERT INTO `order_good` VALUES (89, '9804fba4-30e2-43b9-ad7d-881006be9496', 102, 20, 6, 0);
INSERT INTO `order_good` VALUES (90, '9804fba4-30e2-43b9-ad7d-881006be9496', 103, 20, 10, 0);
INSERT INTO `order_good` VALUES (91, '9804fba4-30e2-43b9-ad7d-881006be9496', 104, 20, 13, 0);
INSERT INTO `order_good` VALUES (92, '9804fba4-30e2-43b9-ad7d-881006be9496', 105, 20, 11, 0);
INSERT INTO `order_good` VALUES (93, '9804fba4-30e2-43b9-ad7d-881006be9496', 106, 20, 27, 0);
INSERT INTO `order_good` VALUES (94, '9df04989-ebef-4c76-a377-fee743703778', 7, 1, 40, 0);
INSERT INTO `order_good` VALUES (95, '9df04989-ebef-4c76-a377-fee743703778', 2, 1, 50, 0);
INSERT INTO `order_good` VALUES (96, '9df04989-ebef-4c76-a377-fee743703778', 3, 1, 50, 0);
INSERT INTO `order_good` VALUES (97, '9df04989-ebef-4c76-a377-fee743703778', 8, 1, 70, 0);
INSERT INTO `order_good` VALUES (98, '9df04989-ebef-4c76-a377-fee743703778', 10, 1, 50, 0);
INSERT INTO `order_good` VALUES (99, '9df04989-ebef-4c76-a377-fee743703778', 11, 1, 50, 0);
INSERT INTO `order_good` VALUES (100, '9df04989-ebef-4c76-a377-fee743703778', 5, 1, 50, 0);
INSERT INTO `order_good` VALUES (101, '9df04989-ebef-4c76-a377-fee743703778', 9, 1, 50, 0);
INSERT INTO `order_good` VALUES (102, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 102, 21, 50, 0);
INSERT INTO `order_good` VALUES (103, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 103, 21, 50, 0);
INSERT INTO `order_good` VALUES (104, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 104, 21, 50, 0);
INSERT INTO `order_good` VALUES (105, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 105, 21, 50, 0);
INSERT INTO `order_good` VALUES (106, '6a8e14d8-003d-4819-9094-9181f334eca0', 78, 12, 10, 1);
INSERT INTO `order_good` VALUES (107, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 78, 13, 10, 0);
INSERT INTO `order_good` VALUES (108, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 103, 13, 10, 0);
INSERT INTO `order_good` VALUES (109, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 80, 13, 10, 0);
INSERT INTO `order_good` VALUES (110, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 102, 13, 10, 0);
INSERT INTO `order_good` VALUES (111, 'b938c099-038a-45d8-98c3-c8ec52a497b3', 102, 17, 10, 0);
INSERT INTO `order_good` VALUES (112, 'b938c099-038a-45d8-98c3-c8ec52a497b3', 103, 17, 10, 0);
INSERT INTO `order_good` VALUES (113, 'b938c099-038a-45d8-98c3-c8ec52a497b3', 104, 17, 100, 0);
INSERT INTO `order_good` VALUES (114, 'b938c099-038a-45d8-98c3-c8ec52a497b3', 105, 17, 1000, 0);
INSERT INTO `order_good` VALUES (115, '5ebdbcae-7dd7-46fd-9526-e093c7c32f5a', 5, 1, 11, 0);

-- ----------------------------
-- Table structure for out_storage
-- ----------------------------
DROP TABLE IF EXISTS `out_storage`;
CREATE TABLE `out_storage`  (
  `out_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '出库流水',
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编号',
  `good_id` int(0) NULL DEFAULT NULL COMMENT '出库货物编号',
  `num` bigint(0) NULL DEFAULT NULL COMMENT '数量',
  `warehouse_id` int(0) NULL DEFAULT NULL COMMENT '仓库编号',
  `container_id` int(0) NULL DEFAULT NULL COMMENT '库柜编号',
  `outDate` datetime(0) NULL DEFAULT NULL COMMENT '出库时间',
  PRIMARY KEY (`out_id`) USING BTREE,
  INDEX `out_storage_order_order_no_fk`(`order_no`) USING BTREE,
  CONSTRAINT `out_storage_order_order_no_fk` FOREIGN KEY (`order_no`) REFERENCES `order` (`order_no`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of out_storage
-- ----------------------------
INSERT INTO `out_storage` VALUES (1, 'e1d915bf-f910-4224-b76c-4b6f333ef09b', 80, 5, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (2, 'e1d915bf-f910-4224-b76c-4b6f333ef09b', 78, 4, 3, 2, '2023-02-28 08:00:00');
INSERT INTO `out_storage` VALUES (5, '99ccfba3-4258-4393-b9aa-7c4d1e59ea7e', 78, 5, 3, 2, '2023-03-08 12:55:44');
INSERT INTO `out_storage` VALUES (6, '99ccfba3-4258-4393-b9aa-7c4d1e59ea7e', 80, 6, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (7, '3e69303a-57ab-47cb-bbd8-424062a10a90', 3, 5, 1, 1, NULL);
INSERT INTO `out_storage` VALUES (8, '0b7b2bfa-cc76-4a9b-ba51-d143a528453a', 3, 5, 3, 1, NULL);
INSERT INTO `out_storage` VALUES (9, '2025dae6-1f5e-412a-bba4-a185177d86d5', 2, 3, 3, 1, NULL);
INSERT INTO `out_storage` VALUES (10, 'cab81981-f1b7-49f8-a875-b34454db6d71', 3, 10, 1, 1, '2023-03-10 08:00:00');
INSERT INTO `out_storage` VALUES (11, '710e5188-8e5f-4b76-a27c-d3c0de8ca342', 3, 10, 1, 1, NULL);
INSERT INTO `out_storage` VALUES (12, '9804fba4-30e2-43b9-ad7d-881006be9496', 102, 6, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (13, '9804fba4-30e2-43b9-ad7d-881006be9496', 103, 10, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (14, '9804fba4-30e2-43b9-ad7d-881006be9496', 104, 13, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (15, '9804fba4-30e2-43b9-ad7d-881006be9496', 105, 11, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (16, '9804fba4-30e2-43b9-ad7d-881006be9496', 106, 27, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (17, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 102, 50, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (18, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 103, 50, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (19, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 104, 50, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (20, '9c9a7af1-5242-4b9a-aa5d-7e60c925fb3d', 105, 50, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (21, '6a8e14d8-003d-4819-9094-9181f334eca0', 78, 10, 1, 2, NULL);
INSERT INTO `out_storage` VALUES (22, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 78, 10, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (23, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 103, 10, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (24, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 80, 10, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (25, 'c37fb9b7-eb50-4e5a-bb55-a63ead3d8c72', 102, 10, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (26, 'b938c099-038a-45d8-98c3-c8ec52a497b3', 102, 10, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (27, 'b938c099-038a-45d8-98c3-c8ec52a497b3', 103, 10, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (28, 'b938c099-038a-45d8-98c3-c8ec52a497b3', 104, 100, NULL, NULL, NULL);
INSERT INTO `out_storage` VALUES (29, 'b938c099-038a-45d8-98c3-c8ec52a497b3', 105, 1000, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for produce_consume
-- ----------------------------
DROP TABLE IF EXISTS `produce_consume`;
CREATE TABLE `produce_consume`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `warehouse_id` int(0) NULL DEFAULT NULL,
  `raw_num` bigint(0) NULL DEFAULT NULL,
  `raw_id` int(0) NULL DEFAULT NULL,
  `raw_container_id` int(0) NULL DEFAULT NULL,
  `good_id` int(0) NULL DEFAULT NULL,
  `good_num` bigint(0) NULL DEFAULT NULL,
  `good_container_id` int(0) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of produce_consume
-- ----------------------------
INSERT INTO `produce_consume` VALUES (1, 3, 4, 2, 1, 78, 5, 2, '2023-03-28 08:00:00');
INSERT INTO `produce_consume` VALUES (2, 1, 10, 3, 1, 3, 5, 2, '2023-03-09 08:00:00');
INSERT INTO `produce_consume` VALUES (3, 1, 10, 3, 1, 3, 5, 2, '2023-03-09 08:00:00');
INSERT INTO `produce_consume` VALUES (4, 1, 10, 3, 1, 3, 5, 2, '2023-03-09 08:00:00');
INSERT INTO `produce_consume` VALUES (5, 1, 10, 3, 1, 78, 5, 2, '2023-03-09 08:00:00');
INSERT INTO `produce_consume` VALUES (6, 1, 5, 3, 1, 78, 1, 2, '2023-03-09 08:00:00');
INSERT INTO `produce_consume` VALUES (7, 1, 10, 3, 1, 78, 5, 2, '2023-03-09 08:00:00');
INSERT INTO `produce_consume` VALUES (8, 1, 100, 3, 1, 78, 20, 2, '2023-03-09 08:00:00');

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '流水',
  `num` bigint(0) NULL DEFAULT 0 COMMENT '数量',
  `purchase_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '采购编号',
  `create_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发起人',
  `process_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理人编号',
  `createDate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `state` int(0) NULL DEFAULT NULL COMMENT '状态',
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `purchase_purchase_no_uindex`(`purchase_no`) USING BTREE,
  INDEX `purchase_order_order_no_fk`(`order_no`) USING BTREE,
  CONSTRAINT `purchase_order_order_no_fk` FOREIGN KEY (`order_no`) REFERENCES `order` (`order_no`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase
-- ----------------------------
INSERT INTO `purchase` VALUES (1, 0, NULL, NULL, '王俊伟', NULL, NULL, NULL);
INSERT INTO `purchase` VALUES (2, 0, 'FAFEA5AA', NULL, NULL, '2023-02-20 20:32:49', 1, '4a6aa4da-4887-457f-abe0-aed2d5d74a54');
INSERT INTO `purchase` VALUES (3, 21, 'A39D7939', '黄帅', '方昆', '2023-02-20 20:37:23', 2, 'fa29673c-35f0-417b-9132-a69e99bb7ac4');
INSERT INTO `purchase` VALUES (4, 16, '5A3A21CF', '方昆', '方昆', '2023-02-20 20:46:20', 2, '3e8ecc48-e4af-4bf9-bc14-a334c0c0c057');
INSERT INTO `purchase` VALUES (5, 145, 'B41FE806', '王俊伟', '方昆', '2023-02-20 20:47:06', 2, '325575c1-1dc3-43a5-b73e-31fb06b32e01');
INSERT INTO `purchase` VALUES (6, 29, '15787CA8', '王俊伟2号', '方昆', '2023-02-20 20:54:04', 2, '58a570a0-95d6-4be9-ac6a-5c39d59b5cbe');
INSERT INTO `purchase` VALUES (7, 10, '93958DCA', '方昆2号', '方昆', '2023-02-21 18:59:04', 2, 'f4c20a17-b3bb-4186-9820-3a119f98a2d5');
INSERT INTO `purchase` VALUES (8, 10, '1F95C738', '黄帅2号', NULL, '2023-02-21 19:11:02', -1, 'd0fad099-31cf-4f7e-90d0-f8859fa52c02');
INSERT INTO `purchase` VALUES (9, 11, '28CA45B6', '方昆3号', NULL, '2023-02-21 19:20:30', 1, '998c3e76-2c75-4c2d-ba52-bff4ec8a0560');
INSERT INTO `purchase` VALUES (10, 5, '3CB46286', NULL, NULL, '2023-02-21 19:51:38', -1, '00a6468f-efac-48aa-b8ba-97bcb229652a');
INSERT INTO `purchase` VALUES (11, 11, '4F2EEB43', '王俊伟3号', NULL, '2023-02-22 11:01:17', 0, '4d12152c-9ef9-4618-8425-cb6625773a8d');
INSERT INTO `purchase` VALUES (12, 0, '8F3D5679', '黄帅3号', NULL, '2023-02-22 13:21:04', 0, '952a29f8-7e5c-4482-b239-7c6d31e609b0');
INSERT INTO `purchase` VALUES (13, 30, 'DF9B3B79', '黄帅', NULL, '2023-02-22 18:18:07', -1, '585d976a-1817-41ce-9db4-e192ab262560');
INSERT INTO `purchase` VALUES (14, 35, '6BAA9EF8', '方昆', NULL, '2023-02-22 18:40:23', -1, '72c25afc-3daa-45f3-95a0-bd94c41fa1b3');
INSERT INTO `purchase` VALUES (15, 21, '32E3E119', '黄帅', 'admin4', '2023-02-22 18:43:38', 2, '5ebdbcae-7dd7-46fd-9526-e093c7c32f5a');
INSERT INTO `purchase` VALUES (16, 49, 'D84A72F8', '张邦昌', 'admin', '2023-02-23 15:09:12', 2, '0d059b9a-9b78-40e4-ad43-81efed5bd9e4');
INSERT INTO `purchase` VALUES (17, 16, 'C5637970', '测试', '方昆', '2023-02-23 19:11:09', 2, '9f006f8d-ce7e-4382-907e-5b673a1cf8ff');
INSERT INTO `purchase` VALUES (18, 0, 'CA1D6E78', NULL, NULL, '2023-03-03 16:32:23', 0, '1775286c-db56-448c-8329-dc4563fa98ca');
INSERT INTO `purchase` VALUES (19, 0, '984808A4', NULL, NULL, '2023-03-03 20:21:28', 0, '452cc82b-5aa4-4d3a-aab8-efdc49fefe7c');
INSERT INTO `purchase` VALUES (20, 410, '85A841E2', '王俊伟', 'admin4', '2023-03-09 21:32:47', 2, '9df04989-ebef-4c76-a377-fee743703778');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名称',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '2023-03-05 23:34:45', 'boss', 'ROLE_ROOT');
INSERT INTO `role` VALUES (2, '普通管理员', '2023-03-05 23:35:33', '普通管理员', 'ROLE_ADMIN');
INSERT INTO `role` VALUES (3, '员工', '2023-03-05 23:36:10', '打工人', 'ROLE_EMP');
INSERT INTO `role` VALUES (4, '运输员工', NULL, '运输管理', 'ROLE_VEHICLE');

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `stock_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '库存编号',
  `warehouse_id` int(0) NULL DEFAULT NULL COMMENT '仓库编号',
  `container_id` int(0) NULL DEFAULT NULL COMMENT '库柜编号',
  `good_id` int(0) NULL DEFAULT NULL COMMENT '货物编号',
  `num` bigint(0) NULL DEFAULT 0 COMMENT '货物数量',
  PRIMARY KEY (`stock_id`) USING BTREE,
  INDEX `stock_good_good_id_fk`(`good_id`) USING BTREE,
  INDEX `stock_warehouse_container_warehouse_id_container_id_fk`(`warehouse_id`, `container_id`) USING BTREE,
  CONSTRAINT `stock_warehouse_container_warehouse_id_container_id_fk` FOREIGN KEY (`warehouse_id`, `container_id`) REFERENCES `warehouse_container` (`warehouse_id`, `container_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES (1, 3, 1, 2, 6);
INSERT INTO `stock` VALUES (4, 1, 2, 78, 46);
INSERT INTO `stock` VALUES (5, 1, 1, 3, 94);
INSERT INTO `stock` VALUES (6, 3, 2, 78, 997);
INSERT INTO `stock` VALUES (8, 3, 11, 73, 100);
INSERT INTO `stock` VALUES (9, 3, 9, NULL, 0);
INSERT INTO `stock` VALUES (10, 23, 23, NULL, 0);

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商地址',
  `linkMan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES (2, '王俊伟集团', '18318318311', '江苏无锡国家软件园', '王俊伟', '123456@qq.com', '做大做强再创辉煌');
INSERT INTO `supplier` VALUES (3, '张邦昌股份有线公司', '12345678910', '张邦昌之家', '张邦昌', '123321@qq.com', '我才是最强的那一个');
INSERT INTO `supplier` VALUES (7, '成功了开心', '18318318311', '不知道', '不知道', '1831@qq.com', '1831@qq.com');
INSERT INTO `supplier` VALUES (9, '我是王俊伟', '18318318311', '王俊伟', '王俊伟', '1831@qq.com', '王俊伟');
INSERT INTO `supplier` VALUES (10, '王俊伟', '18318318311', '不知道啊', '王俊伟', '1831@qq.com', '这是一个测试');
INSERT INTO `supplier` VALUES (11, '王俊伟', '18318318311', '王俊伟', '王俊伟', '1831@qq.com', '王俊伟');
INSERT INTO `supplier` VALUES (12, '王俊伟', '18318318311', '王俊伟', '王俊伟', '1831@qq.com', '王俊伟');
INSERT INTO `supplier` VALUES (13, '王俊伟', '18318318311', '王俊伟', '王俊伟', '1831@qq.com', '王俊伟');
INSERT INTO `supplier` VALUES (14, '我是王俊伟', '18318318311', '王俊伟', '我是王俊伟', '1831@qq.com', '1831@qq.com');
INSERT INTO `supplier` VALUES (20, '强盛集团', '18318812232', '山东', '高启强', '1831@qq.com', '一个卖鱼的');
INSERT INTO `supplier` VALUES (21, '俊伟集团', '1888188818', '江苏无锡国家软件园', '王俊伟', '123456@qq.com', '做大做强再创辉煌');
INSERT INTO `supplier` VALUES (23, '俊伟集团', '13988888888', '江苏无锡', '王俊伟', NULL, NULL);
INSERT INTO `supplier` VALUES (24, '俊伟集团', '1888188818', '江苏无锡国家软件园', '王俊伟', '123456@qq.com', '做大做强再创辉煌');
INSERT INTO `supplier` VALUES (25, '俊伟集团', '1888188818', '江苏无锡国家软件园', '王俊伟', '123456@qq.com', '做大做强再创辉煌');

-- ----------------------------
-- Table structure for transport
-- ----------------------------
DROP TABLE IF EXISTS `transport`;
CREATE TABLE `transport`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `vehicle_id` int(0) NULL DEFAULT NULL COMMENT '运输车辆编号',
  `start_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出发点',
  `mid_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '途径地点',
  `end_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目的地',
  `startDate` datetime(0) NULL DEFAULT NULL COMMENT '出发时间',
  `expected_endDate` datetime(0) NULL DEFAULT NULL COMMENT '预计送达时间',
  `state` int(0) NULL DEFAULT NULL COMMENT '车辆状态\n0：未开始运输\n1：运输中\n3：运输完成 ',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `transport_vehicle_id_fk`(`vehicle_id`) USING BTREE,
  CONSTRAINT `transport_vehicle_id_fk` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transport
-- ----------------------------
INSERT INTO `transport` VALUES (1, 1, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `transport` VALUES (2, 5, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `transport` VALUES (3, 3, '上海,浦东机场', '北京,天安门', '武汉,江岸步行街', '2023-02-28 08:00:00', '2023-03-07 00:00:00', 1);
INSERT INTO `transport` VALUES (4, 8, '测试2', NULL, '上海', NULL, NULL, 0);
INSERT INTO `transport` VALUES (5, 7, '上海', '测试2', '测试2', NULL, NULL, 2);
INSERT INTO `transport` VALUES (6, 9, '上海', NULL, '测试2', NULL, NULL, 0);
INSERT INTO `transport` VALUES (7, 24, '江苏,无锡', '江苏无锡', '江苏,无锡', '2023-03-10 08:00:00', '2023-03-17 00:00:00', 1);
INSERT INTO `transport` VALUES (8, 10, '测试2', NULL, '上海', NULL, NULL, 0);
INSERT INTO `transport` VALUES (9, 20, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `transport` VALUES (10, 21, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `transport` VALUES (11, 12, '测试2', '上海', '上海', NULL, NULL, 1);
INSERT INTO `transport` VALUES (12, 13, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `transport` VALUES (13, 17, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登入账号',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `state` int(0) NULL DEFAULT 1,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_user_name_uindex`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '黄帅', 1, '$2a$10$RymTvDd6biaawMQfR/UBrOUhb8kDylhmkYHLn.bKsCMD9PeU/N2eu', '1388888888@qq.com', '138888', '2023-03-13 14:45:25');
INSERT INTO `user` VALUES (6, 'admin2', '黄帅', 0, '$2a$10$RymTvDd6biaawMQfR/UBrOUhb8kDylhmkYHLn.bKsCMD9PeU/N2eu', '1388888888@qq.com', '138888', '2023-03-13 14:45:27');
INSERT INTO `user` VALUES (29, 'admin4', 'admin4', 1, '$2a$10$RymTvDd6biaawMQfR/UBrOUhb8kDylhmkYHLn.bKsCMD9PeU/N2eu', '1445211359@qq.com', '1233566', '2023-03-13 14:45:28');
INSERT INTO `user` VALUES (31, 'junwell', '王俊伟', 1, '$2a$10$.1uoEQlDnm6vLuhwfSpXyu65CWV7cUATL5dap23h/of9H7kjqYOWO', '2081088544@qq.com', '18388888888', '2023-03-06 11:44:26');
INSERT INTO `user` VALUES (32, 'FK', '方昆', 1, '$2a$10$0TGlrV2.xn4t4oCYWR/TdOEV6ZFkIlVy/r4NYeCt.XFa4F00gHtve', '1445211359@qq.com', '18388888888', '2023-03-08 21:08:57');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(0) NOT NULL,
  `role_id` int(0) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `user_role_role_id_fk_2`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (6, 1);
INSERT INTO `user_role` VALUES (29, 1);
INSERT INTO `user_role` VALUES (31, 1);
INSERT INTO `user_role` VALUES (1, 2);
INSERT INTO `user_role` VALUES (29, 2);
INSERT INTO `user_role` VALUES (31, 2);
INSERT INTO `user_role` VALUES (1, 3);
INSERT INTO `user_role` VALUES (6, 3);
INSERT INTO `user_role` VALUES (29, 3);
INSERT INTO `user_role` VALUES (31, 3);
INSERT INTO `user_role` VALUES (32, 3);
INSERT INTO `user_role` VALUES (29, 4);
INSERT INTO `user_role` VALUES (32, 4);

-- ----------------------------
-- Table structure for vehicle
-- ----------------------------
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE `vehicle`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车辆名称',
  `admin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '司机',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `max_num` bigint(0) NULL DEFAULT NULL COMMENT '载重',
  `vehicle_no` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车牌号',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vehicle
-- ----------------------------
INSERT INTO `vehicle` VALUES (1, '保时捷911', '黄', '18888888888', 10000, '苏B666666', '/pic/12d2af4f-1199-4a6d-b96b-304d4b095cbf.jpg,/pic/9d7b33fd-f157-418b-84cc-61fc7b7d0357.jpg,/pic/74c4fa40-da65-4d62-acd9-bf048af32fa3.jpg,/pic/197cd718-3b1d-4891-8b52-b863cc91c623.jpg');
INSERT INTO `vehicle` VALUES (3, '奥迪R8', '方昆', '13999999999', 5000, '沪A888888', '/pic/83482080-f0ec-481d-a8aa-41c9f79db9cc.jpg,/pic/91497a53-6f70-44e5-a574-58e19845f629.jpg,/pic/0c4ad354-234d-4df9-922d-a887fbd5bf09.jpg');
INSERT INTO `vehicle` VALUES (5, '奥迪R8', '方昆1', '13999999999', 5000, '沪A888887', NULL);
INSERT INTO `vehicle` VALUES (6, '奥迪R8', '方昆2', '13999999999', 5000, '沪A888886', NULL);
INSERT INTO `vehicle` VALUES (7, '奥迪R8', '方昆3', '13999999999', 5000, '沪A888885', NULL);
INSERT INTO `vehicle` VALUES (8, '奥迪R8', '方昆4', '13999999999', 5000, '沪A888884', '/pic/77e3b27e-f3db-4bc8-8f39-03a2c02c37db.jpg,/pic/12512bde-68f5-47da-8ccf-5aed945477a6.jpg');
INSERT INTO `vehicle` VALUES (9, '奥迪R8', '方昆5', '13999999999', 5000, '沪A888883', NULL);
INSERT INTO `vehicle` VALUES (10, '奥迪R8', '方昆6', '13999999999', 5000, '沪A888882', NULL);
INSERT INTO `vehicle` VALUES (11, '奥迪R8', '方昆7', '13999999999', 5000, '沪A888881', NULL);
INSERT INTO `vehicle` VALUES (12, '奥迪Rs8', '方昆8', '13999999999', 5000, '沪A888880', NULL);
INSERT INTO `vehicle` VALUES (13, '奥迪R8', '方昆9', '13999999999', 5000, '沪A888879', NULL);
INSERT INTO `vehicle` VALUES (14, '奥迪R8', '方昆10', '13999999999', 5000, '沪A888878', NULL);
INSERT INTO `vehicle` VALUES (15, '奥迪R8', '方昆11', '13999999999', 5000, '沪A888877', NULL);
INSERT INTO `vehicle` VALUES (16, '奥迪R8', '方昆12', '13999999999', 5000, '沪A888876', NULL);
INSERT INTO `vehicle` VALUES (17, '奥迪R8', '方昆13', '13999999999', 5000, '沪A888875', NULL);
INSERT INTO `vehicle` VALUES (18, '奥迪R8', '方昆14', '13999999999', 5000, '沪A888874', NULL);
INSERT INTO `vehicle` VALUES (19, '奥迪Rs7', '方昆15', '13999999999', 5000, '沪A888873', NULL);
INSERT INTO `vehicle` VALUES (20, '法拉利SF90', '王俊伟', '13999999999', 99999, '浙A666666', NULL);
INSERT INTO `vehicle` VALUES (21, '帕加尼', '王俊伟', '18388888888', 10000, '666666', '/pic/b58b7e75-7d6d-434f-8acb-29bea6af2b67.jpg');
INSERT INTO `vehicle` VALUES (24, '测试', '王俊伟', '18388888888', 10000, 'AB123456', '/pic/6898ccfd-a76d-47c6-8320-5b7ac911af6e.jpg');
INSERT INTO `vehicle` VALUES (25, '法拉利SF90', '王俊伟', '13999999999', 99999, '浙A666666', NULL);
INSERT INTO `vehicle` VALUES (26, '法拉利SF90', '王俊伟', '13999999999', 99999, '浙A666666', NULL);

-- ----------------------------
-- Table structure for vehicle_state
-- ----------------------------
DROP TABLE IF EXISTS `vehicle_state`;
CREATE TABLE `vehicle_state`  (
  `vehicle_id` int(0) NOT NULL,
  `state` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`) USING BTREE,
  CONSTRAINT `vehicle_state_vehicle_id_fk` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vehicle_state
-- ----------------------------
INSERT INTO `vehicle_state` VALUES (1, 1);
INSERT INTO `vehicle_state` VALUES (3, 1);
INSERT INTO `vehicle_state` VALUES (5, 1);
INSERT INTO `vehicle_state` VALUES (6, 0);
INSERT INTO `vehicle_state` VALUES (7, 0);
INSERT INTO `vehicle_state` VALUES (8, 1);
INSERT INTO `vehicle_state` VALUES (9, 1);
INSERT INTO `vehicle_state` VALUES (10, 1);
INSERT INTO `vehicle_state` VALUES (11, 0);
INSERT INTO `vehicle_state` VALUES (12, 1);
INSERT INTO `vehicle_state` VALUES (13, 1);
INSERT INTO `vehicle_state` VALUES (14, 0);
INSERT INTO `vehicle_state` VALUES (15, 0);
INSERT INTO `vehicle_state` VALUES (16, 0);
INSERT INTO `vehicle_state` VALUES (17, 1);
INSERT INTO `vehicle_state` VALUES (18, 0);
INSERT INTO `vehicle_state` VALUES (19, 0);
INSERT INTO `vehicle_state` VALUES (20, 1);
INSERT INTO `vehicle_state` VALUES (21, 1);
INSERT INTO `vehicle_state` VALUES (24, 1);
INSERT INTO `vehicle_state` VALUES (25, 0);
INSERT INTO `vehicle_state` VALUES (26, 0);

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库名字',
  `admin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理人员',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse
-- ----------------------------
INSERT INTO `warehouse` VALUES (1, '测试1', '测试', '测试2', '18886666666');
INSERT INTO `warehouse` VALUES (3, '你急了啊仓库', '姿态', '上海', '18886666666');
INSERT INTO `warehouse` VALUES (9, '你急了啊仓库', '姿态', '上海', '18886666666');
INSERT INTO `warehouse` VALUES (10, '你急了啊仓库', '姿态', '上海', '18886666666');
INSERT INTO `warehouse` VALUES (11, '你急了啊仓库', '姿态', '上海', '18886666666');
INSERT INTO `warehouse` VALUES (12, '你急了啊仓库', '姿态', '上海', '18886666666');
INSERT INTO `warehouse` VALUES (13, '测试3', '测试3', '测试3', '18886666666');
INSERT INTO `warehouse` VALUES (14, '测试4', '测试4', '测试4', '18886666666');
INSERT INTO `warehouse` VALUES (15, '测试5', '测试5', '测试5', '18886666666');
INSERT INTO `warehouse` VALUES (16, '你急了啊仓库', '姿态', '上海', NULL);
INSERT INTO `warehouse` VALUES (17, '你急了啊仓库', '姿态', '上海', '18886666666');
INSERT INTO `warehouse` VALUES (18, '你急了啊仓库', '姿态', '上海', '18886666666');
INSERT INTO `warehouse` VALUES (19, '测试3', '王俊伟', '江苏无锡', '13888888888');
INSERT INTO `warehouse` VALUES (21, '大飞老师的仓库', 'Faker', '韩国', '18888888888');
INSERT INTO `warehouse` VALUES (22, '大飞老师的仓库', 'Faker', '韩国', '18888888888');
INSERT INTO `warehouse` VALUES (23, '产品仓库', '黄帅', '江苏无锡', '13888888888');

-- ----------------------------
-- Table structure for warehouse_container
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_container`;
CREATE TABLE `warehouse_container`  (
  `warehouse_id` int(0) NOT NULL COMMENT '仓库编号',
  `container_id` int(0) NOT NULL COMMENT '库柜编号',
  `num` bigint(0) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`warehouse_id`, `container_id`) USING BTREE,
  INDEX `warehouse_container1_container_id_fk`(`container_id`) USING BTREE,
  CONSTRAINT `warehouse_container1_container_id_fk` FOREIGN KEY (`container_id`) REFERENCES `container` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `warehouse_container1_warehouse_id_fk` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_container
-- ----------------------------
INSERT INTO `warehouse_container` VALUES (1, 1, 6);
INSERT INTO `warehouse_container` VALUES (1, 2, 10);
INSERT INTO `warehouse_container` VALUES (3, 1, 20);
INSERT INTO `warehouse_container` VALUES (3, 2, 20);
INSERT INTO `warehouse_container` VALUES (3, 9, 3);
INSERT INTO `warehouse_container` VALUES (3, 11, 100);
INSERT INTO `warehouse_container` VALUES (3, 13, 20);
INSERT INTO `warehouse_container` VALUES (9, 1, 6);
INSERT INTO `warehouse_container` VALUES (23, 23, 6);

-- ----------------------------
-- Table structure for warehouse_transfer
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_transfer`;
CREATE TABLE `warehouse_transfer`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `num` bigint(0) NULL DEFAULT NULL COMMENT '移库数量',
  `original_warehouse_id` int(0) NULL DEFAULT NULL COMMENT '原来仓库编号',
  `original_container_id` int(0) NULL DEFAULT NULL COMMENT '原来库柜编号',
  `transferDate` datetime(0) NULL DEFAULT NULL COMMENT '移库时间',
  `transfer_container_id` int(0) NULL DEFAULT NULL COMMENT '目标库柜编号',
  `transfer_warehouse_id` int(0) NULL DEFAULT NULL COMMENT '目标仓库编号',
  `good_id` int(0) NULL DEFAULT NULL COMMENT '物品名称',
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `warehouse_transfer_good_good_id_fk`(`good_id`) USING BTREE,
  INDEX `warehouse_transfer_warehouse_container_container_id_fk`(`original_container_id`) USING BTREE,
  INDEX `warehouse_transfer_warehouse_container_container_id_fk_2`(`transfer_container_id`) USING BTREE,
  INDEX `warehouse_transfer_warehouse_container_warehouse_id_fk_2`(`original_warehouse_id`) USING BTREE,
  INDEX `warehouse_transfer_warehouse_container_id_fk`(`transfer_warehouse_id`, `transfer_container_id`) USING BTREE,
  INDEX `warehouse_transfer_order_order_no_fk`(`order_no`) USING BTREE,
  CONSTRAINT `warehouse_transfer_good_good_id_fk` FOREIGN KEY (`good_id`) REFERENCES `good` (`good_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `warehouse_transfer_order_order_no_fk` FOREIGN KEY (`order_no`) REFERENCES `order` (`order_no`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `warehouse_transfer_warehouse_container_id_fk` FOREIGN KEY (`transfer_warehouse_id`, `transfer_container_id`) REFERENCES `warehouse_container` (`warehouse_id`, `container_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_transfer
-- ----------------------------
INSERT INTO `warehouse_transfer` VALUES (1, 10, 1, 1, '2023-03-02 08:00:00', 1, 3, 3, '710e5188-8e5f-4b76-a27c-d3c0de8ca342');
INSERT INTO `warehouse_transfer` VALUES (2, 10, 1, 2, '2023-03-12 08:00:00', 2, 3, 78, '6a8e14d8-003d-4819-9094-9181f334eca0');

SET FOREIGN_KEY_CHECKS = 1;
