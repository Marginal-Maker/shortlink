package com.majing.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author majing
 * @date 2024-04-24 14:54
 * @Description
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("t_link_goto")
public class LinkGotoDo {
    @TableId(type = IdType.AUTO)
    Long Id;
    String gid;
    String fullShortUrl;
}
