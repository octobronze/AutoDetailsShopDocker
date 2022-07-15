package com.example.AutoDetailsShop.filters;

import com.example.AutoDetailsShop.domain.Offer;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public final class OfferFilter {

    private OfferFilter(){}

    public static Specification<Offer> filterByAllFields(String detailName, String carBrandName, String carModelName, BigDecimal price) {

        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Offer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate predicateForDetailName = criteriaBuilder.equal(root.get("detail").get("detailName"), detailName);
                Predicate predicateForCarBrandName = criteriaBuilder.equal(root.get("carBrand").get("carBrandName"), carBrandName);
                Predicate predicateForCarModelName = criteriaBuilder.equal(root.get("carModel").get("carModelName"), carModelName);
                Predicate predicateForPrice = criteriaBuilder.equal(root.get("price"), price);

                Predicate finalPredicate = conjunctQueries(null, predicateForDetailName, criteriaBuilder, detailName);
                finalPredicate = conjunctQueries(finalPredicate, predicateForCarBrandName, criteriaBuilder, carBrandName);
                finalPredicate = conjunctQueries(finalPredicate, predicateForCarModelName, criteriaBuilder, carModelName);
                finalPredicate = conjunctQueries(finalPredicate, predicateForPrice, criteriaBuilder, price);

                return finalPredicate;
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
        };
    }
}
