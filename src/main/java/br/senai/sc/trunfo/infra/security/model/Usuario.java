package br.senai.sc.trunfo.infra.security.model;


import br.senai.sc.trunfo.model.entity.Jogador;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class Usuario implements UserDetails {


    private Jogador jogador;

    public Usuario(Jogador jogador) {
        this.jogador = jogador;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return jogador.getSenha();
    }

    @Override
    public String getUsername() {
        return jogador.getNome();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
