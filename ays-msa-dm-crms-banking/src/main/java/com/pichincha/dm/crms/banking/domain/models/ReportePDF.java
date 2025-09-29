package com.pichincha.dm.crms.banking.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportePDF{
    private String contentType;
    private String filename;
    private String data;
}
