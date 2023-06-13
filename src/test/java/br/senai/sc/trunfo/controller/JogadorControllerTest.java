package br.senai.sc.trunfo.controller;

import br.senai.sc.trunfo.model.dto.CartaDTO;
import br.senai.sc.trunfo.model.dto.JogadorDTO;
import br.senai.sc.trunfo.model.entity.Carta;
import br.senai.sc.trunfo.model.entity.Jogador;
import br.senai.sc.trunfo.model.enumerador.RankingCarta;
import br.senai.sc.trunfo.service.CartaService;
import br.senai.sc.trunfo.service.JogadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Data
@WebMvcTest(JogadorController.class)
class JogadorControllerTest {

    @MockBean
    private JogadorService jogadorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/jogador/delete/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testCreate() throws Exception {
        JogadorDTO jogadorDTO = new JogadorDTO("Kauê","123",0,0);
        Jogador jogador = new Jogador();
        copyProperties(jogadorDTO, jogador);
        jogador.setId(1);

        when(jogadorService.create(any())).thenReturn(jogador);
        String content = mapper.writeValueAsString(jogadorDTO);

        mockMvc.perform(post("/jogador").content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(jogador));
    }

    @Test
    void testEdit() throws Exception {
        JogadorDTO jogadorDTO = new JogadorDTO("Kauê","123",0,0);
        Jogador jogador = new Jogador();
        copyProperties(jogadorDTO, jogador);

        jogador.setId(1);

        when(jogadorService.update(any())).thenReturn(jogador);
        when(jogadorService.readOne(any())).thenReturn(jogador);
        String content = mapper.writeValueAsString(jogadorDTO);

        mockMvc.perform(put("/jogador/edit/{id}",1).content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(jogador));
    }

    @Test
    void testFindOneByName() throws Exception {

        JogadorDTO jogadorDTO = new JogadorDTO("Kauê","123",0,0);
        Jogador jogador = new Jogador();
        copyProperties(jogadorDTO, jogador);
        jogador.setId(1);

        when(jogadorService.readOneByName(any())).thenReturn(jogador);
        String content = mapper.writeValueAsString(jogadorDTO);

        mockMvc.perform(get("/jogador/{id}",1).content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(jogador));
    }

    @Test
    void testFindAll() throws Exception {
        JogadorDTO jogadorDTO = new JogadorDTO("Kauê","123",0,0);
        Jogador jogador = new Jogador();
        Jogador jogador2 = new Jogador();
        copyProperties(jogadorDTO, jogador);
        jogadorDTO.setNome("Kauê2");
        copyProperties(jogadorDTO,jogador2);

        List<Jogador> listaDeJogadores = new ArrayList<>();
        listaDeJogadores.add(jogador);
        listaDeJogadores.add(jogador2);

        when(jogadorService.readAll()).thenReturn(listaDeJogadores);
        String content = mapper.writeValueAsString(jogadorDTO);

        mockMvc.perform(get("/jogador/all",1).content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(jogador))
                .andExpect(jsonPath("$[1]").value(jogador2));
    }

}