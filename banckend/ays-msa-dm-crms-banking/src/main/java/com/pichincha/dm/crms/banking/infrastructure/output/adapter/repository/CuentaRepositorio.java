package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository;

import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.CuentaEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepositorio extends JpaRepository<CuentaEntidad, Long> {
    Page<CuentaEntidad> findAllByCliente_IdPersona(Long idPersona, Pageable pageable);
    Optional<CuentaEntidad> findByNumeroCuenta(String numeroCuenta);
}
