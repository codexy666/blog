package com.study.blog.service;


import com.study.blog.entity.dos.Archives;
import com.study.blog.entity.params.ArticleParam;
import com.study.blog.entity.params.PageParams;
import com.study.blog.entity.vo.ArticleVo;
import com.study.blog.entity.vo.Result;

import java.util.List;

public interface ArticleService {


    Result listArticlesPage(PageParams pageParams);

    List<ArticleVo> hotArticle(int limit);

    List<ArticleVo> newArticles(int limit);

    List<Archives> listArchives();

    ArticleVo findArticleById(Long id);

    Result publish(ArticleParam articleParam);
}
