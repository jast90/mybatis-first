package org.jast.mybatis.blog.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jast.mybatis.blog.entity.RoleEntity;
import org.jast.mybatis.blog.entity.RoleEntityExample;

public interface RoleEntityMapper {
    int countByExample(RoleEntityExample example);

    int deleteByExample(RoleEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleEntity record);

    int insertSelective(RoleEntity record);

    List<RoleEntity> selectByExample(RoleEntityExample example);

    RoleEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleEntity record, @Param("example") RoleEntityExample example);

    int updateByExample(@Param("record") RoleEntity record, @Param("example") RoleEntityExample example);

    int updateByPrimaryKeySelective(RoleEntity record);

    int updateByPrimaryKey(RoleEntity record);
}