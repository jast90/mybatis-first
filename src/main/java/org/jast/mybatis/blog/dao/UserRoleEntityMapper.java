package org.jast.mybatis.blog.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jast.mybatis.blog.entity.UserRoleEntity;
import org.jast.mybatis.blog.entity.UserRoleEntityExample;

public interface UserRoleEntityMapper {
    int countByExample(UserRoleEntityExample example);

    int deleteByExample(UserRoleEntityExample example);

    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int insert(UserRoleEntity record);

    int insertSelective(UserRoleEntity record);

    List<UserRoleEntity> selectByExample(UserRoleEntityExample example);

    int updateByExampleSelective(@Param("record") UserRoleEntity record, @Param("example") UserRoleEntityExample example);

    int updateByExample(@Param("record") UserRoleEntity record, @Param("example") UserRoleEntityExample example);
}