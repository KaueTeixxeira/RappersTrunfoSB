package br.senai.sc.trunfo.infra.security.util;

import br.senai.sc.trunfo.model.entity.Jogador;
import br.senai.sc.trunfo.repository.JogadorRepository;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import com.auth0.jwt.JWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JWTUtil {

    private static JogadorRepository jogadorRepository;

    @Autowired
    public JWTUtil(JogadorRepository jogadorRepository) {
        JWTUtil.jogadorRepository = jogadorRepository;
    }

    public static String gerarToken(Jogador jogador) {

        String secret = "c127a7b6adb013a5ff879ae71afa62afa4b4ceb72afaa54711dbcde67b6dc325";
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("WEG")
                .withSubject(jogador.getNome())
                .withIssuedAt(new Date())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
    }

    private static Instant genExpirationDate() {
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }

    public static Jogador getUsuario(String token) {
        String nome = JWT.decode(token).getSubject();
        return jogadorRepository.findByNome(nome).get();
    }


}