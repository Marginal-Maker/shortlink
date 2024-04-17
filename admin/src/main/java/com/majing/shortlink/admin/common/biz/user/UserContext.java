package com.majing.shortlink.admin.common.biz.user;


import com.alibaba.ttl.TransmittableThreadLocal;
import java.util.Optional;
/**
 * @author majing
 * @date 2024-04-15 16:33
 * @Description 用户上下文
 */
public final class UserContext {

    //线程安全的ThreadLocal，阿里巴巴的TTL
    private static final ThreadLocal<UserInfoDto> USER_THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 设置用户至上下文
     *
     * @param user 用户详情信息
     */
    public static void setUser(UserInfoDto user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取上下文中用户 ID
     *
     * @return 用户 ID
     */
    public static String getUserId() {
        UserInfoDto userInfoDto = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDto).map(UserInfoDto::getUserId).orElse(null);
    }

    /**
     * 获取上下文中用户名称
     *
     * @return 用户名称
     */
    public static String getUsername() {
        UserInfoDto userInfoDto = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDto).map(UserInfoDto::getUsername).orElse(null);
    }

    /**
     * 获取上下文中用户真实姓名
     *
     * @return 用户真实姓名
     */
    public static String getRealName() {
        UserInfoDto userInfoDto = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDto).map(UserInfoDto::getRealName).orElse(null);
    }

    /**
     * 获取上下文中用户 Token
     *
     * @return 用户 Token
     */
    public static String getToken() {
        UserInfoDto userInfoDto = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDto).map(UserInfoDto::getToken).orElse(null);
    }

    /**
     * 清理用户上下文
     */
    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }
}
