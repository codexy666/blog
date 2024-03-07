package com.study.blog.controller;

import com.study.blog.common.aop.LogAnnotation;
import com.study.blog.common.cache.Cache;
import com.study.blog.entity.params.ArticleBodyParam;
import com.study.blog.entity.params.ArticleParam;
import com.study.blog.entity.params.PageParams;
import com.study.blog.entity.vo.ArticleBodyVo;
import com.study.blog.entity.vo.ArticleVo;
import com.study.blog.entity.vo.Result;
import com.study.blog.service.ArticleService;
import com.study.blog.service.ThreadService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping()
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    @Cache(expire = 5 * 60 * 1000, name = "articles")
    public Result articles(@RequestBody PageParams pageParams) {
//        int i = 1/0;
        return articleService.listArticlesPage(pageParams);
    }

    /**
     * 最热文章
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000, name = "hotArticle")
    public Result hotArticle() {
        int limit = 5;
        List<ArticleVo> articleVoList = articleService.hotArticle(limit);
        return Result.success(articleVoList);
    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 1000, name = "newArticles")
    public Result newArticles() {
        int limit = 5;
        List<ArticleVo> articleVoList = articleService.newArticles(limit);
        return Result.success(articleVoList);
    }

    @PostMapping("listArchives")
    public Result listArchives() {
        return Result.success(articleService.listArchives());
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        ArticleVo articleVo = articleService.findArticleById(id);
        return Result.success(articleVo);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }
}
