package com.majing.shortlink.admin.common.serialize;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author majing
 * @date 2024-04-09 14:59
 * @Description 手机脱敏处理
 */
public class PhoneDesensitizationSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String phone, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // 使用 DesensitizedUtil工具类对添加注解的字段进行脱敏处理
        String phoneDesensitization = DesensitizedUtil.mobilePhone(phone);
        // 将脱敏后的手机号写入 JSON 数据流
        jsonGenerator.writeString(phoneDesensitization);
    }
}
