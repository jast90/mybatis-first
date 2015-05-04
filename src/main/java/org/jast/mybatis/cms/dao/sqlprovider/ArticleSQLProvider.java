package org.jast.mybatis.cms.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;
import org.jast.mybatis.cms.entity.Article;

/**
 *
 * Mybatis 动态SQL
 *
 * Created by zhiwen on 15-5-4.
 */
public class ArticleSQLProvider {

    private final static String TABLE_NAME = "t_article";

    public String insertArticle(final Article article){
        return new SQL(){
            {
                INSERT_INTO(TABLE_NAME);
                if(article.getTitle()!=null){
                    VALUES("title","#{title}");
                }
                if(article.getContent()!=null){
                    VALUES("content","#{content}");
                }
            }
        }.toString();
    }

    public String findByArticle(final Article article){
        return new SQL(){
            {
                SELECT("*");
                FROM(TABLE_NAME);
                if(article.getArticleId()!=null){
                    WHERE("articleId = #{articleId}");
                }
                if (article.getTitle()!=null){
                    WHERE("title like #{title}");
                }
                if(article.getContent()!=null){
                    WHERE("content like #{content}");
                }
            }
        }.toString();
    }
}
