package com.pruu.pombo.auth;

import com.pruu.pombo.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public  String getGeneratedToken(Authentication authentication) {
        Instant now = Instant.now();

        // será usado para definir tempo do token
        long dezHorasEmSegundos = 36000L;

        String rles = authentication
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors
                        .joining(" "));

        User jogadorAutenticado = (User) authentication.getPrincipal();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("vem-no-x1")			                // emissor do token
                .issuedAt(now) 						            // data/hora em que o token foi emitido
                .expiresAt(now.plusSeconds(dezHorasEmSegundos)) // expiração do token, em segundos.
                .subject(authentication.getName())              // nome do usuário
                .claim("roles", rles)                          // perfis ou permissões (roles)
                .claim("userId", jogadorAutenticado.getId()) // mais propriedades adicionais no token
                .build();

        return jwtEncoder.encode(
                        JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}