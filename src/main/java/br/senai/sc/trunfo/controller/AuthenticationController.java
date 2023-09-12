package br.senai.sc.trunfo.controller;

import br.senai.sc.trunfo.infra.security.TokenService;
import br.senai.sc.trunfo.infra.security.model.Usuario;
import br.senai.sc.trunfo.infra.security.repository.UsuarioRepository;
import br.senai.sc.trunfo.infra.security.util.CookieUtil;
import br.senai.sc.trunfo.model.dto.AuthenticationDTO;
import br.senai.sc.trunfo.model.dto.JogadorDTO;
import br.senai.sc.trunfo.model.dto.RegisterDTO;
import br.senai.sc.trunfo.model.entity.Jogador;
import br.senai.sc.trunfo.repository.JogadorRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO authenticationDTO,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        System.out.println("Im here");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken
                (authenticationDTO.login(),
                        authenticationDTO.password());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        System.out.println(auth.isAuthenticated());
        if (auth.isAuthenticated()) {
            Usuario usuario = (Usuario)auth.getPrincipal();
            Cookie cookie = CookieUtil.gerarCookie(usuario.getJogador());
            response.addCookie(cookie);
            System.out.println(cookie);
            return ResponseEntity.ok(auth.getPrincipal());
        }
        System.out.println("Falha");

        return ResponseEntity.status(401).build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody JogadorDTO registerDTO) {
        if (this.jogadorRepository.findByNome(registerDTO.getNome()).isPresent()) return ResponseEntity.badRequest().build();
        String encrypPasswd = new BCryptPasswordEncoder().encode(registerDTO.getSenha());
        JogadorDTO jogadorDTO = new JogadorDTO(registerDTO.getNome(),encrypPasswd,0,0);
        System.out.println(jogadorDTO);
        Jogador jogador = new Jogador();
        BeanUtils.copyProperties(jogadorDTO,jogador);
        jogadorRepository.save(jogador);
        Usuario usuario = new Usuario(jogador, encrypPasswd);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }


}
