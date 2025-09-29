package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository;

import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.PersonaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepositorio extends JpaRepository<PersonaEntidad, Long> {
}
