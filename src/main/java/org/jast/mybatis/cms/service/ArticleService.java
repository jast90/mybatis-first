package org.jast.mybatis.cms.service;

import org.jast.mybatis.cms.dao.ArticleDao;
import org.jast.mybatis.cms.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhiwen on 15-4-8.
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;


    public void add(Article article){
        articleDao.add(article);
    }

    public Article getArticle(Long articleId){
        return articleDao.getArticle(articleId);
    }

    public List<Article> getList(Article article) {
        return articleDao.getList(article);
    }
}
