package br.senai.sc.trunfo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogadorDTO {

    private String nome, senha;
    private Integer numVitoria, numDerrota;

}
