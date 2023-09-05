package br.senai.sc.trunfo.infra.security;

import br.senai.sc.trunfo.infra.security.util.CookieUtil;
import br.senai.sc.trunfo.infra.security.util.JWTUtil;
import br.senai.sc.trunfo.model.entity.Jogador;
import br.senai.sc.trunfo.repository.JogadorRepository;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    JogadorRepository jogadorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getRequestURI());
        if (!rotaPublica(request.getRequestURI())) {
            try {
                String token = CookieUtil.getToken(request);
                System.out.println(token);
                Jogador jogador = JWTUtil.getUsuario(token);
                response.addCookie(CookieUtil.gerarCookie(jogador));

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(jogador.getUsername(), null, jogador.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JWTDecodeException exception) {
                response.setStatus(401);
                response.getWriter().write("Token inválido");
                return;
            } catch (Exception exception){
                System.out.println("Cookie não encontrado");

            }
            System.out.println("Deu bo");
        }

        filterChain.doFilter(request,response);
    }

    private boolean rotaPublica(String requestURI) {
        return requestURI.startsWith("/auth/login") || requestURI.startsWith("/auth/register");
    }


}
