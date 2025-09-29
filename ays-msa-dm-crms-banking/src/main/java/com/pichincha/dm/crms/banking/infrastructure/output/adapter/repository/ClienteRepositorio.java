package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository;

import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.ClienteEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepositorio extends JpaRepository<ClienteEntidad, Long> {
    Page<ClienteEntidad> findAllByEstado(Boolean estado, org.springframework.data.domain.Pageable pageable);
}
