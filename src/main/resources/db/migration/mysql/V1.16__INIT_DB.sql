-- ----------------------------
-- Table structure for img
-- ----------------------------
DROP TABLE IF EXISTS `img`;
CREATE TABLE `img`  (
                        `id` bigint(11) NOT NULL,
                        `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                        `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                        `size` int(11) NULL DEFAULT NULL,
                        `public` tinyint(1) NOT NULL DEFAULT 1,
                        `limit_date` datetime NULL DEFAULT NULL,
                        `limit_times` int(6) NULL DEFAULT 0,
                        `owner` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                        `deleted` tinyint(1) NOT NULL DEFAULT 0,
                        `storage` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE,
                        INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `data` json NULL,
                         `updateTime` datetime NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
