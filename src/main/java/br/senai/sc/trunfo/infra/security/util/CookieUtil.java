package br.senai.sc.trunfo.infra.security.util;


import br.senai.sc.trunfo.model.entity.Jogador;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

public class CookieUtil {

    public static Cookie gerarCookie(Jogador jogador) {
        String token = JWTUtil.gerarToken(jogador);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setMaxAge(1800);
        return cookie;
    }

    public static String getToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "JWT");

        if (cookie != null) return cookie.getValue();
        throw new RuntimeException("Token não encontrado");

    }
}
