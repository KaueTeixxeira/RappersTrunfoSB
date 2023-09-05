package br.senai.sc.trunfo.service;

import br.senai.sc.trunfo.model.entity.Carta;
import br.senai.sc.trunfo.repository.CartaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartaService {


    private CartaRepository cartaRepository;


    public Carta create(Carta carta) {
        return cartaRepository.save(carta);
    }

    public List<Carta> readAll(){
        return cartaRepository.findAll();
    }
    public Page<Carta> readSome(int page, int size){
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return cartaRepository.findAll(pageable);
    }

    public Carta readOne(Integer id) {
        Optional<Carta> cartaOptional = cartaRepository.findById(id);
        if (cartaOptional.isPresent()) {
            return cartaOptional.get();
        }
        throw new RuntimeException("Carta não encontrada");
    }

    public Carta update(Carta carta) {
        return create(carta);
    }

    public void delete(Integer id) {
       cartaRepository.deleteById(id);
    }

    public List<List<Carta>> starGame() {
        List<Carta> listaDeCartas = readAll();
        List<Carta> jogador1 = new ArrayList<>();
        List<Carta> jogador2 = new ArrayList<>();
        List<List<Carta>> cartaPJogo = new ArrayList<>();
        if (listaDeCartas.size() > 1) {
            for (int i = 0; i < listaDeCartas.size(); i++) {
                if (i%2 == 0){
                    jogador1.add(listaDeCartas.get(i));
                } else {
                    jogador2.add(listaDeCartas.get(i));
                }
            }
        } else {
            throw new RuntimeException("Não existem cartas suficientes para jogar!");
        }
        cartaPJogo.add(jogador1);
        cartaPJogo.add(jogador2);
        return cartaPJogo;
    }
}
