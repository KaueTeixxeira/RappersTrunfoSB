package br.senai.sc.trunfo.repository;

import br.senai.sc.trunfo.model.entity.Carta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Integer> {
}
