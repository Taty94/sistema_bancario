package com.pichincha.dm.crms.banking.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginationInfo {
    private String page;
    private String size;
    private String totalElements;
    private String totalPages;
    private String first;
    private String last;
}