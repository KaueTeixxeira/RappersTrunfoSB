package br.senai.sc.trunfo.service;

import br.senai.sc.trunfo.infra.security.model.Usuario;
import br.senai.sc.trunfo.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new Usuario(jogadorRepository.findByNome(username).get());
    }
}
