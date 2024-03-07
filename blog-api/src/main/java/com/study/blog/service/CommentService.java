package com.study.blog.service;

import com.study.blog.entity.params.CommentParam;
import com.study.blog.entity.vo.Result;

public interface CommentService {
    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
