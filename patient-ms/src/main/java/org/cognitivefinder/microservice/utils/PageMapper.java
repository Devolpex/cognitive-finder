package org.cognitivefinder.microservice.utils;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PageMapper {

    public static <T, R> OwnPageRES<R> toMyPageRES(Page<T> page, Pageable pageable, Function<T, R> mapper) {
        return OwnPageRES.<R>builder()
                .content(page.getContent().stream().map(mapper).toList()) 
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .sortBy(pageable.getSort().toString())
                .orderBy(pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name() : null)
                .size(page.getSize())
                .build();
    }
    
}