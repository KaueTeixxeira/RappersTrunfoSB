package br.senai.sc.trunfo.repository;

import br.senai.sc.trunfo.model.entity.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {
    Optional<Jogador> findByNome(String nome);
}
