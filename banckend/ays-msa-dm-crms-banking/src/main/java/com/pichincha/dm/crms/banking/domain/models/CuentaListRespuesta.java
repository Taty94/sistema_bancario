package com.pichincha.dm.crms.banking.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CuentaListRespuesta {
    private List<Cuenta> data;
    private PaginationInfo pagination;
}
