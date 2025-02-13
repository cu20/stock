package com.lpy.service;

import com.lpy.pojo.entity.SysUser;
import com.lpy.vo.resp.LoginRespVo;
import com.lpy.vo.resp.R;
import com.lpy.vo.resq.LoginReqVo;

import java.util.Map;

/**
 * @author lpy
 * @date 2025/2/11
 * @Description 用户服务接口
 */
public interface UserService {
    /**
     * 用户登录功能实现
     * @param vo
     * @return
     */
    R<LoginRespVo> login(LoginReqVo vo);

    R<Map> getCaptchaCode();

    SysUser findByUserName(String userName);
}
