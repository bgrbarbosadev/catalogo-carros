package br.com.bgrbarbosa.catalogo_carros_api.security;

import br.com.bgrbarbosa.catalogo_carros_api.repository.UserRepository;
import com.auth0.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter{

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);
            UserDetails user = userRepository.findByLogin(login);

            // Extrai as roles do token JWT
            var jwt = JWT.decode(token);
            List<String> roles = jwt.getClaim("roles").asList(String.class);

            // Converte as roles para GrantedAuthority
            var authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role)) // Adiciona o prefixo ROLE_
                    .collect(Collectors.toList());

            // Cria a autenticação com as roles
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
