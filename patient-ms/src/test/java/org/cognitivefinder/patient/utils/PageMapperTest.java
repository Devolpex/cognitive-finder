package org.cognitivefinder.patient.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class PageMapperTest {

    @Test
    void testToMyPageRES() {
        // Arrange: Create a mock Page and Pageable
        List<String> content = List.of("item1", "item2", "item3");
        Page<String> page = new PageImpl<>(content, PageRequest.of(1, 3, Sort.by(Sort.Order.asc("field"))), 10);
        Pageable pageable = PageRequest.of(1, 3, Sort.by(Sort.Order.asc("field")));

        // Act: Map the Page to OwnPageRES
        OwnPageRES<String> result = PageMapper.toMyPageRES(page, pageable, item -> item.toUpperCase());

        // Assert: Verify the mapping
        assertNotNull(result);
        assertEquals(3, result.getContent().size());
        assertEquals(List.of("ITEM1", "ITEM2", "ITEM3"), result.getContent());
        assertEquals(10, result.getTotalElements());
        assertEquals(4, result.getTotalPages());
        assertEquals(2, result.getCurrentPage()); // Page numbers are 1-based
        assertEquals("field: ASC", result.getSortBy());
        assertEquals("ASC", result.getOrderBy());
        assertEquals(3, result.getSize());
    }

    @Test
    void testToMyPageRESWithEmptyPage() {
        // Arrange: Create an empty Page and Pageable
        Page<String> page = new PageImpl<>(List.of(), PageRequest.of(0, 5, Sort.unsorted()), 0);
        Pageable pageable = PageRequest.of(0, 5, Sort.unsorted());

        // Act: Map the Page to OwnPageRES
        OwnPageRES<String> result = PageMapper.toMyPageRES(page, pageable, item -> item.toUpperCase());

        // Assert: Verify the mapping
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        assertEquals(1, result.getCurrentPage()); // Page numbers are 1-based
        assertNotNull(result.getSortBy());
        assertNull(result.getOrderBy());
        assertEquals(5, result.getSize());
    }
}
