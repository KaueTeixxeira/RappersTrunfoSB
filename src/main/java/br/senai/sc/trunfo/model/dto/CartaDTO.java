package br.senai.sc.trunfo.model.dto;

import br.senai.sc.trunfo.model.enumerador.RankingCarta;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaDTO {

    @Positive
    private Integer freestyle,originalidade, impacto, maisOuvidas;
    private String nome;
    private RankingCarta ranking;
    private String url;
}
