package com.study.admin.entity.param;

import lombok.Data;

@Data
public class PageParam {

    private Integer currentPage;
    private Integer pageSize;
    private String queryString;
}
