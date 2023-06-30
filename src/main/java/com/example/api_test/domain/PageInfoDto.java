package com.example.api_test.domain;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class PageInfoDto {
    private int currentPageNum;
    private int totalPageNum;
    private int nextPageNum;
    private int prevPageNum;
    private int listSize;
    private List<Object> list;

    public PageInfoDto(int currentPageNum, int totalPageNum, List<Object> list) {
        this.currentPageNum = currentPageNum;
        this.totalPageNum = totalPageNum;
        this.list = list;
        this.listSize = list.size();
        this.nextPageNum = (currentPageNum == totalPageNum ? 0 : currentPageNum +1);
        this.prevPageNum = (currentPageNum - 1 == 0 ? 0 : currentPageNum - 1);
    }

}
