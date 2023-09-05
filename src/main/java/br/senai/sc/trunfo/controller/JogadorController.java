package br.senai.sc.trunfo.controller;

import br.senai.sc.trunfo.model.dto.CartaDTO;
import br.senai.sc.trunfo.model.dto.JogadorDTO;
import br.senai.sc.trunfo.model.entity.Carta;
import br.senai.sc.trunfo.model.entity.Jogador;
import br.senai.sc.trunfo.service.JogadorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/jogador")
@CrossOrigin
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    @PostMapping
    public ResponseEntity<Jogador> create(@RequestBody JogadorDTO jogadorDTO) {
        Jogador jogador = new Jogador();
        BeanUtils.copyProperties(jogadorDTO, jogador);
        return ResponseEntity.ok(jogadorService.create(jogador));
    }

//    @GetMapping("{id}")
//    public ResponseEntity<Jogador> readOne(@PathVariable Integer id) {
//        return  ResponseEntity.ok(jogadorService.readOne(id));
//    }

    @GetMapping("{nome}")
    public ResponseEntity<Jogador> readOneByName(@PathVariable String nome) {
        return  ResponseEntity.ok(jogadorService.readOneByName(nome));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        jogadorService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Jogador> edit(@PathVariable Integer id,@RequestBody JogadorDTO jogadorDTO) {
        Jogador jogador = jogadorService.readOne(id);
        BeanUtils.copyProperties(jogadorDTO,jogador);
        return ResponseEntity.ok(jogadorService.update(jogador));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Jogador>> listOfCards() {
        return ResponseEntity.ok(jogadorService.readAll());
    }

}
