package com.study.blog.service;

import com.study.blog.entity.vo.ArticleBodyVo;
import com.study.blog.entity.vo.CategoryVo;
import com.study.blog.entity.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long categoryId);
}
