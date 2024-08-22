package com.shair13.data_service.dao;

import com.shair13.data_service.entity.Movie;
import com.shair13.data_service.entity.Movie_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieSearchDao {

    private final EntityManager entityManager;

    public List<Movie> searchByCriteria(
            SearchRequest request,
            Pageable pageable
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<Movie> root = criteriaQuery.from(Movie.class);

        if (request.title() != null) {
            Predicate titlePredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get(Movie_.title)), "%" + request.title().toLowerCase() + "%");
            predicates.add(titlePredicate);
        }

        if (request.director() != null) {
            Predicate directorPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get(Movie_.director)), "%" + request.director().toLowerCase() + "%");
            predicates.add(directorPredicate);
        }

        if (request.description() != null) {
            Predicate descriptionPredicate = criteriaBuilder
                    .like(criteriaBuilder.lower(root.get(Movie_.description)), "%" + request.description().toLowerCase() + "%");
            predicates.add(descriptionPredicate);
        }

        if (request.rate() != null) {
            Predicate ratePredicate = criteriaBuilder
                    .greaterThanOrEqualTo(root.get(Movie_.rate), request.rate());
            predicates.add(ratePredicate);
        }

        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))
        );

        List<Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            if (order.isAscending()) {
                orders.add(criteriaBuilder.asc(root.get(order.getProperty())));
            } else {
                orders.add(criteriaBuilder.desc(root.get(order.getProperty())));
            }
        }

        criteriaQuery.orderBy(orders);

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }
}
