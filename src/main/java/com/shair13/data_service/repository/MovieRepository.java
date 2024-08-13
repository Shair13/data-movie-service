package com.shair13.data_service.repository;

import com.shair13.data_service.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE " +
            "LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')) AND " +
            "LOWER(m.director) LIKE LOWER(CONCAT('%', :director, '%')) AND " +
            "m.rate >= :rateGreaterThan")
    Page<Movie> search(String title, String director, Double rateGreaterThan, Pageable pageable);
}
