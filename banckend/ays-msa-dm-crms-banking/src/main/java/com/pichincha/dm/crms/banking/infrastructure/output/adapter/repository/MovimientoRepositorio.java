package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository;

import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.MovimientoEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface MovimientoRepositorio extends JpaRepository<MovimientoEntidad, Long> {
    Page<MovimientoEntidad> findAllByCuenta_numeroCuenta(String numeroCuenta, Pageable pageable);

    List<MovimientoEntidad> findByCuenta_Cliente_IdPersonaAndFechaBetween(
            Long idPersona,
            OffsetDateTime fechaInicio,
            OffsetDateTime fechaFin
    );
}
