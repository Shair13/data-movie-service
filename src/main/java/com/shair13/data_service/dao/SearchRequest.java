package com.shair13.data_service.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchRequest {
   private String title;
    private String director;
    private String description;
    private Double rate;
}
