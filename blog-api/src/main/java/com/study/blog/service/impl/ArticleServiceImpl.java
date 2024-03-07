package com.study.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.blog.entity.dos.Archives;
import com.study.blog.entity.params.ArticleParam;
import com.study.blog.entity.params.PageParams;
import com.study.blog.entity.pojo.Article;
import com.study.blog.entity.pojo.ArticleBody;
import com.study.blog.entity.pojo.ArticleTag;
import com.study.blog.entity.pojo.SysUser;
import com.study.blog.entity.vo.*;
import com.study.blog.mapper.ArticleBodyMapper;
import com.study.blog.mapper.ArticleMapper;
import com.study.blog.mapper.ArticleTagMapper;
import com.study.blog.service.*;
import com.study.blog.utils.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;
    @Autowired
    private ThreadService threadService;

    // 自定义 SQL 实现文章列表
    @Override
    public Result listArticlesPage(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page, pageParams.getCategoryId(), pageParams.getTagId(), pageParams.getYear(), pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(), true, true));
    }

    // 使用 LambdaQueryWrapper
//    @Override
//    public List<ArticleVo> listArticlesPage(PageParams pageParams) {
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        // 具体分类下的文章列表
//        // and category_id=#{categoryId}
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//        // 具体标签下的文章列表
//        // and tag_id=#{tagId}
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null) {
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTagList = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTagList) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0) {
//                queryWrapper.in(Article::getId, articleIdList);
//            }
//        }
//
//        // 是否置顶、创建时间进行排序
//        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<ArticleVo> articleVoList = copyList(articlePage.getRecords(), true, true);
//        return Result.success(articleVoList);
//    }

    @Override
    public List<ArticleVo> hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articleList =  articleMapper.selectList(queryWrapper);
        return copyList(articleList, false, false, false, false);
    }

    @Override
    public List<ArticleVo> newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articleList =  articleMapper.selectList(queryWrapper);
        return copyList(articleList, false, false, false);
    }

    @Override
    public List<Archives> listArchives() {
        return articleMapper.listArchives();
    }

    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        threadService.updateViewCount(article);
        return copy(article, true, true, true, true);
    }

    @Override
    @Transactional // 需要修改表，添加事务注解
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setViewCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L); // 系统自动生成
        articleMapper.insert(article);  // 文章id自动生成

        // 标签和文章的关联表修改
        List<TagVo> tagVoList = articleParam.getTags();
        if (tagVoList != null) {
            for (TagVo tagVo : tagVoList) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.parseLong(tagVo.getId()));
                articleTagMapper.insert(articleTag);
            }
        }
        // 插入 article_body 表
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        // 将刚得到的 bodyId 与 article 关联起来
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        // 返回数据
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        return Result.success(articleVo);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor, isTag,false,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag,boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor, isTag, isBody,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTags, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            articleVoList.add(copy(article, isAuthor, isTags, isBody, isCategory));
        }
        return articleVoList;
    }


    private ArticleVo copy(Article article, boolean isAuthor, boolean isTags, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isAuthor) {
            SysUser sysUser = sysUserService.findSysUserById(article.getId());
            articleVo.setAuthor(sysUser.getNickname());
        }
        if (isBody) {
            ArticleBodyVo articleBodyVo = findArticleBody(article.getId());
            articleVo.setBody(articleBodyVo);
        }
        if (isTags) {
            List<TagVo> tags = tagService.findTagsByArticleId(article.getId());
            articleVo.setTags(tags);
        }
        if (isCategory) {
            CategoryVo categoryVo = findCategory(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }

        return articleVo;
    }

    private ArticleBodyVo findArticleBody(Long articleId) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId, articleId);
        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    private CategoryVo findCategory(Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }
}
