package com.study.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.blog.entity.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long id);

    List<Long> findHotTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> hotTagIds);
}
