package com.example.AutoDetailsShop.filters;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public final class Pagination {

    private Pagination(){};

    public static <T> List<T> getPage(TypedQuery<T> query, int page, int size){
        size = size < 1 ? 1 : size;
        page = page < 0 ? 0 : page;
        return query.setFirstResult(size * page).setMaxResults(size).getResultList();
    }
}
