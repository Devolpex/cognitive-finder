package org.cognitivefinder.patient.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnPageRES<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int currentPage;
    private String sortBy;
    private String orderBy;
}