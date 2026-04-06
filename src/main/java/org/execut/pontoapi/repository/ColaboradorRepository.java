package org.execut.pontoapi.repository;

import org.execut.pontoapi.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    Optional<Colaborador> findByMatricula(String matricula);

    boolean existsByEmail(String email);

    // A peça final: ensina o Spring Boot a buscar o colaborador pelo e-mail
    Optional<Colaborador> findByEmail(String email);
}