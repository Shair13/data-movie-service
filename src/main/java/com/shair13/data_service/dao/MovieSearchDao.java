package com.shair13.data_service.dao;

import com.shair13.data_service.entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieSearchDao {

    private final EntityManager entityManager;

    public List<Movie> searchByCriteria(
            SearchRequest request,
            PageDetails pageDetails
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<Movie> root = criteriaQuery.from(Movie.class);

        if (request.getTitle() != null) {
            Predicate titlePredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("title")), "%" + request.getTitle().toLowerCase() + "%");
            predicates.add(titlePredicate);
        }

        if (request.getDirector() != null) {
            Predicate directorPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("director")), "%" + request.getDirector().toLowerCase() + "%");
            predicates.add(directorPredicate);
        }

        if (request.getDescription() != null) {
            Predicate descriptionPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("description")), "%" + request.getDescription().toLowerCase() + "%");
            predicates.add(descriptionPredicate);
        }

        if (request.getRate() != null) {
            Predicate ratePredicate = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("rate"), request.getRate());
            predicates.add(ratePredicate);
        }


        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))
        );
        criteriaQuery.orderBy(
                criteriaBuilder.asc(root.get(pageDetails.getSortBy()))
        );

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult(pageDetails.getPage() * pageDetails.getSize());
        query.setMaxResults(pageDetails.getSize());

        return query.getResultList();
    }
}
