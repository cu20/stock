package com.lpy.controller;

import com.lpy.pojo.entity.SysUser;
import com.lpy.service.UserService;
import com.lpy.vo.resp.LoginRespVo;
import com.lpy.vo.resp.R;
import com.lpy.vo.resq.LoginReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lpy
 * @date 2025/2/11
 * @Description
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo){
        R<LoginRespVo> r= this.userService.login(vo);
        return r;
    }

}
