package com.majing.shortlink.admin.test;

/**
 * @author majing
 * @date 2024-04-10 12:25
 * @Description
 */
public class UserTableShardingTest {
    public static final String sql = "CREATE TABLE IF NOT EXISTS `shortlink`.`t_user_%d` (\n" +
            "  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `username` VARCHAR(256) NULL DEFAULT NULL COMMENT '用户名',\n" +
            "  `password` VARCHAR(512) NULL DEFAULT NULL COMMENT '密码',\n" +
            "  `real_name` VARCHAR(256) NULL DEFAULT NULL COMMENT '真实姓名',\n" +
            "  `phone` VARCHAR(128) NULL DEFAULT NULL COMMENT '手机号',\n" +
            "  `mail` VARCHAR(512) NULL DEFAULT NULL COMMENT '邮箱',\n" +
            "  `deletion_time` BIGINT NULL DEFAULT NULL COMMENT '注销时间戳',\n" +
            "  `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',\n" +
            "  `update_time` DATETIME NULL DEFAULT NULL COMMENT '修改时间',\n" +
            "  `del_flag` TINYINT(1) NULL DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8mb4;";

    public static void main(String[] args) {
        for(int i=0;i<16;i++){
            System.out.printf((sql) + "%n",i);
        }
    }
}
