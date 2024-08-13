package com.shair13.data_service.repository;

import com.shair13.data_service.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCaseAndRateGreaterThan(String title, String director, Double rateGreaterThan, Pageable pageable);
}
