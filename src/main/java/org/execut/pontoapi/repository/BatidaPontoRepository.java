package org.execut.pontoapi.repository;

import org.execut.pontoapi.model.BatidaPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatidaPontoRepository extends JpaRepository<BatidaPonto, Long> {
    // Este é o método novo, usando as variáveis corretas da nova arquitetura
    List<BatidaPonto> findByTenantIdOrderByDataHoraDesc(String tenantId);
}