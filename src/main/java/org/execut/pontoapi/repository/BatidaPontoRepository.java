package org.execut.pontoapi.repository;

import org.execut.pontoapi.model.BatidaPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatidaPontoRepository extends JpaRepository<BatidaPonto, Long> {

}