package commajing.shortlink.project.test;

/**
 * @author majing
 * @date 2024-04-17 17:08
 * @Description
 */
public class LinkTableShardingTest {
    public static final String sql = "CREATE TABLE `t_link_%d` (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `domain` varchar(128) DEFAULT NULL COMMENT '域名',\n" +
            "  `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',\n" +
            "  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',\n" +
            "  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',\n" +
            "\t`click_num` int(11) DEFAULT 0 COMMENT '点击量',\n" +
            "\t`gid` varchar(32) DEFAULT NULL COMMENT '分组标识',\n" +
            "\t`enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：未启用 1：已启用',\n" +
            "\t`created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：控制台 1：接口',\n" +
            "\t`valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：用户自定义',\n" +
            "\t`valid_date` datetime DEFAULT NULL COMMENT '有效期',\n" +
            "\t`describe` varchar(1024) DEFAULT NULL COMMENT '描述',\n" +
            "  `favicon` varchar(1024) DEFAULT NULL COMMENT '图片url',\n" +
            "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
            "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
            "  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `idx_unique_full_short_uri` (`full_short_url`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    public static void main(String[] args) {
        for(int i=0;i<16;i++){
            System.out.printf((sql) + "%n",i);
        }
    }
}
