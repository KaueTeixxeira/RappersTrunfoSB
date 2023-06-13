package br.senai.sc.trunfo.controller;

import br.senai.sc.trunfo.model.dto.CartaDTO;
import br.senai.sc.trunfo.model.entity.Carta;
import br.senai.sc.trunfo.service.CartaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Controller
@AllArgsConstructor
@RequestMapping("/carta")
@CrossOrigin(origins = "*")
public class CartaController {

    private CartaService cartaService;

    @PostMapping
    public ResponseEntity<Carta> create(@RequestBody CartaDTO cartaDTO) {
        Carta carta = new Carta();
        BeanUtils.copyProperties(cartaDTO, carta);
        return ok(cartaService.create(carta));
    }

    @GetMapping("/star-game")
    public ResponseEntity<?> starGame(){
        return status(200).body(cartaService.starGame());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Carta>> readAll(){
        return status(200).body(cartaService.readAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Carta> readOne(@PathVariable Integer id) {
        return  ok(cartaService.readOne(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        cartaService.delete(id);
        return ok().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Carta> edit(@PathVariable Integer id,@RequestBody CartaDTO cartaDTO) {
        Carta carta = cartaService.readOne(id);
        BeanUtils.copyProperties(cartaDTO,carta);
        return ok(cartaService.update(carta));
    }

    @GetMapping
    public ResponseEntity<?> readSome(@RequestParam int page, @RequestParam int size) {
        return ok(cartaService.readSome(page, size));
    }

}
