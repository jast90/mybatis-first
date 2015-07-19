package org.jast.mybatis.blog.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jast.mybatis.blog.entity.RolePermissionEntity;
import org.jast.mybatis.blog.entity.RolePermissionEntityExample;

public interface RolePermissionEntityMapper {
    int countByExample(RolePermissionEntityExample example);

    int deleteByExample(RolePermissionEntityExample example);

    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    int insert(RolePermissionEntity record);

    int insertSelective(RolePermissionEntity record);

    List<RolePermissionEntity> selectByExample(RolePermissionEntityExample example);

    int updateByExampleSelective(@Param("record") RolePermissionEntity record, @Param("example") RolePermissionEntityExample example);

    int updateByExample(@Param("record") RolePermissionEntity record, @Param("example") RolePermissionEntityExample example);
}