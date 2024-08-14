package com.shair13.data_service.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageDetails {
    private Integer page;
    private Integer size;
    private String sortBy;

    public static PageDetails create(Integer page, Integer size, String sortBy) {
        return new PageDetails(
                page != null ? page : 0,
                size != null ? size : 10,
                sortBy != null ? sortBy : "id"
        );
    }
}
