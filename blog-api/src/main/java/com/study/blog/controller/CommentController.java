package com.study.blog.controller;

import com.study.blog.entity.params.CommentParam;
import com.study.blog.entity.vo.Result;
import com.study.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId) {
        return commentService.commentsByArticleId(articleId);
    }


    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam) {
        return commentService.comment(commentParam);
    }
}
