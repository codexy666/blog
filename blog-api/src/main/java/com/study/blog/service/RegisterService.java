package com.study.blog.service;

import com.study.blog.entity.params.RegisterParam;
import com.study.blog.entity.vo.Result;

public interface RegisterService {
    Result register(RegisterParam registerParam);
}
