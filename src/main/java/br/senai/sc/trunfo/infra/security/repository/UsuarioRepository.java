package br.senai.sc.trunfo.infra.security.repository;

import br.senai.sc.trunfo.infra.security.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByJogador_Nome(String nome);
}
