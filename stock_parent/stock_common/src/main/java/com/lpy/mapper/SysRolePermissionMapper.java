package com.lpy.mapper;

import com.lpy.pojo.entity.SysRolePermission;

/**
* @author gugu
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2025-02-11 20:05:48
* @Entity com.lpy.pojo.entity.SysRolePermission
*/
public interface SysRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

}
