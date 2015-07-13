package org.jast.mybatis.cms.dao;

import org.apache.ibatis.annotations.Insert;
import org.jast.mybatis.cms.entity.Category;

/**
 * Created by zhiwen on 15-6-4.
 */
public interface CategoryDao {

    @Insert("INSERT INTO t_category(cname,cdescribe) VALUES(#{cname},#{cdescribe})")
    void add(Category category);

}
