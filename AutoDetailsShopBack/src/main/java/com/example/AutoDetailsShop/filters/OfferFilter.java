package com.example.AutoDetailsShop.filters;

import com.example.AutoDetailsShop.domain.Offer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public final class OfferFilter {


    private OfferFilter(){}

    public static Specification<Offer> filter(String detailName, String carBrandName, String carModelName, BigDecimal price) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AutoDetailsShopUnit");
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Offer> criteriaQuery = criteriaBuilder.createQuery(Offer.class);
        Root<Offer> offerRoot = criteriaQuery.from(Offer.class);

        Predicate predicateForDetailName = criteriaBuilder.equal(offerRoot.get("detail").get("detailName"), detailName);
        Predicate predicateForCarBrandName = criteriaBuilder.equal(offerRoot.get("carBrand").get("carBrandName"), carBrandName);
        Predicate predicateForCarModelName = criteriaBuilder.equal(offerRoot.get("carModel").get("carModelName"), carModelName);
        Predicate predicateForPrice = criteriaBuilder.equal(offerRoot.get("price"), price);

        Predicate query = conjunctQueries(null, predicateForDetailName, criteriaBuilder, detailName);
        query = conjunctQueries(query, predicateForCarBrandName, criteriaBuilder, carBrandName);
        query = conjunctQueries(query, predicateForCarModelName, criteriaBuilder, carModelName);
        query = conjunctQueries(query, predicateForPrice, criteriaBuilder, price);

        final Predicate finalQuery = query;
        criteriaQuery.where(query);
        em.createQuery(criteriaQuery);

        return (r, q, c) -> finalQuery;
    }

    private static Predicate conjunctQueries(Predicate query1, Predicate query2, CriteriaBuilder criteriaBuilder, Object comparisonParameter){
        if(comparisonParameter == null)
            return query1;
        if(query1 == null){
            query1 = query2;
        }else{
            query1 = criteriaBuilder.and(query1, query2);
        }
        return query1;
    }
}
