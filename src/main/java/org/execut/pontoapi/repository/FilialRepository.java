package org.execut.pontoapi.repository;

import org.execut.pontoapi.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// AQUI ESTÁ O SEGREDO: Tem que ser <Filial, String> e não <Filial, Long>
@Repository
public interface FilialRepository extends JpaRepository<Filial, String> {
}