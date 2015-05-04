package org.jast.mybatis.cms.dao;

import org.apache.ibatis.annotations.*;
import org.jast.mybatis.cms.dao.sqlprovider.ArticleSQLProvider;
import org.jast.mybatis.cms.entity.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhiwen on 15-4-8.
 */
@Transactional
public interface ArticleDao {

    @Insert("INSERT INTO t_article(title,content) VALUES(#{title},#{content})")
    void add(Article article);

    @InsertProvider(type= ArticleSQLProvider.class,method = "insertArticle")
    @Options(useGeneratedKeys = true,keyProperty = "articleId")
    void addBySQLProvider();

    @Select("SELECT * FROM t_article WHERE articleId = #{articleId}")
    Article getArticle(@Param("articleId") Long articleId);

    @SelectProvider(type=ArticleSQLProvider.class,method = "findByArticle")
    @Results({
            @Result(id = true,column = "article_id",property = "articleId"),
            @Result(column = "title",property = "title"),
            @Result(column = "content",property = "content"),
    })
    public List<Article> getList(Article article);
}
