package com.fmi.materials.dto.pagedresult;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagedResultDto<T> {
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private int itemsPerPage;
    private long totalItems;
    private boolean first;
    private boolean last;
}
