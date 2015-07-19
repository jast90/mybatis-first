package org.jast.mybatis.blog.service;

import org.jast.mybatis.blog.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by zhiwen on 15-7-18.
 */
@Service
public class UserService {


    /**
     * 根据用户名取的用户的角色
     */
    public Set<String> findRoles(String userName) {
        return null;
    }

    /**
     * 根据用户名取的用户的权限
     */
    public Set<String> findPermissions(String userName) {
        return null;
    }

    /**
     * 根据用户名取的用户
     */
    public UserEntity findByUserName(String userName) {
        return null;
    }
}
