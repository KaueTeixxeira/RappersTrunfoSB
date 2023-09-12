package br.senai.sc.trunfo.service;

import br.senai.sc.trunfo.infra.security.model.Usuario;
import br.senai.sc.trunfo.infra.security.repository.UsuarioRepository;
import br.senai.sc.trunfo.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return usuarioRepository.findByJogador_Nome(name);
    }
}
