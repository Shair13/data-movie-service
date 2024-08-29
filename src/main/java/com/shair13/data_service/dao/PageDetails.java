package com.shair13.data_service.dao;

public record PageDetails(
        Integer page,
        Integer size,
        String sort) {

    public static PageDetails create(Integer page, Integer size, String sort) {
        return new PageDetails(
                page != null ? page : 0,
                size != null ? size : 10,
                sort != null ? sort : "id"
        );
    }
}
