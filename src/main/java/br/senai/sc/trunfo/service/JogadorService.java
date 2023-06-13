package br.senai.sc.trunfo.service;


import br.senai.sc.trunfo.model.entity.Jogador;
import br.senai.sc.trunfo.repository.JogadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    public Jogador create(Jogador jogador) {
        return jogadorRepository.save(jogador);
    }

    public List<Jogador> readAll(){
        return jogadorRepository.findAll();
    }

    public Jogador readOne(Integer id) {
        Optional<Jogador> jogadorOptional = jogadorRepository.findById(id);
        if (jogadorOptional.isPresent()) {
            return jogadorOptional.get();
        }
        throw new RuntimeException("Jogador não encontrado");
    }

    public Jogador update(Jogador jogador) {
        return create(jogador);
    }

    public void delete(Integer id) {
        jogadorRepository.deleteById(id);
    }

    public Jogador readOneByName(String nome) {
        Optional<Jogador> optional = jogadorRepository.findByNome(nome);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("Jogador não encontrado");
    }
}
