package org.execut.pontoapi.repository;
import org.execut.pontoapi.model.BatidaPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BatidaRepository extends JpaRepository<BatidaPonto, Long> {
    List<BatidaPonto> findByColaboradorIdOrderByHoraBatidaDesc(Long colaboradorId);
}