package com.lpy.controller;

import com.lpy.pojo.entity.SysUser;
import com.lpy.service.UserService;
import com.lpy.vo.resp.LoginRespVo;
import com.lpy.vo.resp.R;
import com.lpy.vo.resq.LoginReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lpy
 * @date 2025/2/11
 * @Description
 */
@RestController
@RequestMapping("/api")
@Api(value = "用户认证相关接口定义",tags = "用户功能-用户登录功能")
public class UserController {
    /**
     * 注入用户服务bean
     */
    @Autowired
    private UserService userService;
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    @GetMapping("/{userName}")
    @ApiOperation(value = "根据用户名查询用户信息",notes = "用户信息查询",response = SysUser.class)
    @ApiImplicitParam(paramType = "path",name = "userName",value = "用户名",required = true)
    public SysUser getUserByUserName(@PathVariable("userName") String userName){
        return userService.findByUserName(userName);
    }
    /**
     * 用户登录功能接口
     * @param vo
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录功能",notes = "用户登录",response = R.class)
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo){
        return userService.login(vo);
    }
    /**
     * 生成登录校验码的访问接口
     * @return
     */
    @GetMapping("/captcha")
    @ApiOperation(value = "验证码生成功能",response = R.class)
    public R<Map> getCaptchaCode(){
        return userService.getCaptchaCode();
    }
}
