package br.senai.sc.trunfo.controller;

import br.senai.sc.trunfo.model.dto.CartaDTO;
import br.senai.sc.trunfo.model.entity.Carta;
import br.senai.sc.trunfo.model.enumerador.RankingCarta;
import br.senai.sc.trunfo.service.CartaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
@Data
@WebMvcTest(CartaController.class)
public class CartaControllerTest {


    @MockBean
    private CartaService cartaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testCreate() throws Exception {
        CartaDTO cartaDTO = new CartaDTO(10,20,30,40,"Teste-carta"
                , RankingCarta.A,"a");
        Carta carta = new Carta();
        copyProperties(cartaDTO, carta);
        carta.setId(1);


        when(cartaService.create(any())).thenReturn(carta);
        String content = mapper.writeValueAsString(cartaDTO);

        mockMvc.perform(post("/carta").content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(carta));
    }


    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/carta/delete/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testEdit() throws Exception {
        CartaDTO cartaDTO = new CartaDTO(10,20,30,40,"Teste-carta", RankingCarta.A,"a");
        Carta carta = new Carta();
        copyProperties(cartaDTO, carta);
        carta.setId(1);

        when(cartaService.update(any())).thenReturn(carta);
        when(cartaService.readOne(any())).thenReturn(carta);
        String content = mapper.writeValueAsString(cartaDTO);

        mockMvc.perform(put("/carta/edit/{id}",1).content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(carta));
    }

    @Test
    void testFindOne() throws Exception {
        CartaDTO cartaDTO = new CartaDTO(10,20,30,40,"Teste-carta", RankingCarta.A,"a");
        Carta carta = new Carta();
        copyProperties(cartaDTO, carta);
        carta.setId(1);

        when(cartaService.readOne(1)).thenReturn(carta);
        String content = mapper.writeValueAsString(cartaDTO);

        mockMvc.perform(get("/carta/{id}",1).content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(carta));
    }

    @Test
    void testFindAll() throws Exception {
        CartaDTO cartaDTO = new CartaDTO(10,20,30,40,"Teste-carta", RankingCarta.A,"a");
        Carta carta = new Carta();
        copyProperties(cartaDTO, carta);
        cartaDTO.setNome("Teste-carta2");
        Carta carta2 = new Carta();
        copyProperties(cartaDTO,carta2);
        carta.setId(1);
        List<Carta> listaDeCartas = new ArrayList<>();
        listaDeCartas.add(carta);
        listaDeCartas.add(carta2);

        when(cartaService.listOfCards()).thenReturn(listaDeCartas);
        String content = mapper.writeValueAsString(cartaDTO);

        mockMvc.perform(get("/carta/all",1).content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(carta))
                .andExpect(jsonPath("$[1]").value(carta2));
    }


}
