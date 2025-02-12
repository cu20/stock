package com.lpy.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.lpy.mapper.SysUserMapper;
import com.lpy.pojo.entity.SysUser;
import com.lpy.service.UserService;
import com.lpy.vo.resp.LoginRespVo;
import com.lpy.vo.resp.R;
import com.lpy.vo.resp.ResponseCode;
import com.lpy.vo.resq.LoginReqVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lpy
 * @date 2025/2/11
 * @Description 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        if(vo==null || StringUtils.isBlank(vo.getUsername())
        ||StringUtils.isBlank(vo.getPassword())){
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        SysUser user=this.sysUserMapper.findByUserName(vo.getUsername());
        //判断用户是否存在，存在则密码校验比对
        if (user==null || !passwordEncoder.matches(vo.getPassword(),user.getPassword())){
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
        }
        //组装登录成功数据
        LoginRespVo respVo = new LoginRespVo();
        //属性名称与类型必须相同，否则属性值无法copy
        BeanUtils.copyProperties(user,respVo);
        return  R.ok(respVo);
    }
}
