--liquibase formatted sql

--changeset uyaki:1
CREATE TABLE IF NOT EXISTS `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态。0：禁用；1：正常；',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已删除。0：未删除；1：已删除；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT='用户表';