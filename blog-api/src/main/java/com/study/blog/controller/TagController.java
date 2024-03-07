package com.study.blog.controller;

import com.study.blog.entity.vo.Result;
import com.study.blog.entity.vo.TagVo;
import com.study.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        List<TagVo> tagVoList = tagService.hot(limit);
        return Result.success(tagVoList);
    }

    @GetMapping()
    public Result findAll() {
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result tagsDetail() {
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result tagsDetail(@PathVariable("id") Long tagId) {
        return tagService.findTagDetailById(tagId);
    }
}
