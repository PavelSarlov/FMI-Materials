package com.fmi.materials.dto.pagedresult;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagedResultDto<T> {
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private boolean first;
    private boolean last;
}
