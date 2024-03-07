package com.study.blog.service;

import com.study.blog.entity.vo.Result;
import com.study.blog.entity.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long id);

    List<TagVo> hot(int limit);

    Result findAll();

    Result findAllDetail();

    Result findTagDetailById(Long tagId);
}
