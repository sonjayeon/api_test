package com.example.api_test.domain;

import lombok.Data;

@Data
public class Pageable {
    private int currentPageNum = 1;
    private int pageSize = 10;
}
