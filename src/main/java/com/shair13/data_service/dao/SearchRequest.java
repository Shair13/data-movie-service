package com.shair13.data_service.dao;

public record SearchRequest(
        String title,
        String director,
        String description,
        Double rate) {
}
