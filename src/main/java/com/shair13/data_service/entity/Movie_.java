package com.shair13.data_service.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Movie.class)
public class Movie_ {
    public static volatile SingularAttribute<Movie, String> title;
    public static volatile SingularAttribute<Movie, String> director;
    public static volatile SingularAttribute<Movie, String> description;
    public static volatile SingularAttribute<Movie, Double> rate;
}
