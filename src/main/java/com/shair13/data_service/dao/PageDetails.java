package com.shair13.data_service.dao;

public record PageDetails(
        Integer page,
        Integer size,
        String sortBy) {

    public static PageDetails create(Integer page, Integer size, String sortBy) {
        return new PageDetails(
                page != null ? page : 0,
                size != null ? size : 10,
                sortBy != null ? sortBy : "id"
        );
    }
}
