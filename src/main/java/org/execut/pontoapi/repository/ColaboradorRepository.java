package org.execut.pontoapi.repository;

import org.execut.pontoapi.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findByTenantId(String tenantId);

    boolean existsByMatriculaAndTenantId(String matricula, String tenantId);
}