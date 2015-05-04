package org.jast.mybatis.cms.controller;

//import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.jast.mybatis.cms.entity.Article;
import org.jast.mybatis.cms.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhiwen on 15-4-9.
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    //log4j2
    //private static final Logger logger = LogManager.getLogger(ArticleController.class);

    //log4j1
   //final static Logger logger = Logger.getLogger(ArticleController.class);

    //slf4j
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "")
    public String index(){
        logger.info("logger4j2 !!");
        logger.error("Did it again!");
        return "index";
    }

    @RequestMapping(value = "/detailed/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable("id") Long id){
        Article article = articleService.getArticle(id);
        logger.info("article = " + article);
        return article;
    }

    @RequestMapping(value="/add",method = RequestMethod.GET)
    public String add(){
        logger.info("添加文章");
        return "cms/addArticle";
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(Article arricle){
        logger.info("article = "+arricle);
        articleService.add(arricle);
        return "success";
    }

    @RequestMapping(value="/list")
    @ResponseBody
    public Object getList(Article article){
        return articleService.getList(article);
    }
}

