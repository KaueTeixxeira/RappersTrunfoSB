package br.senai.sc.trunfo.model.entity;

import br.senai.sc.trunfo.model.enumerador.RankingCarta;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer freestyle,originalidade, impacto, maisOuvidas;
    private String nome;
    @Enumerated(EnumType.STRING)
    private RankingCarta ranking;
    private String url;

}
