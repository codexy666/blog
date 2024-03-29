package com.study.blog.entity.params;

import com.study.blog.entity.vo.CategoryVo;
import com.study.blog.entity.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;
    private ArticleBodyParam body;
    private CategoryVo category;
    private String summary;
    private List<TagVo> tags;
    private String title;
}
