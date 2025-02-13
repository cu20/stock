package com.lpy.vo.resq;

import lombok.Data;

/**
 * @author lpy
 * @date 2025/2/12
 * @Description 包装登录请求
 */
@Data
public class LoginReqVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
    /**
     * 保存redis随机码的key，也就是sessionId
     */
    private String sessionId;
}
