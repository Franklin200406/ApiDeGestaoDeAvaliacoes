package br.edu.gestaoavaliacoes.security;

import br.edu.gestaoavaliacoes.model.User;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;

@Component
public class TokenProvider {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final long expirationSeconds;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.expiration-hours}") long expirationHours) {
        SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        this.encoder = new NimbusJwtEncoder(new ImmutableSecret<>(key));
        this.decoder = NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
        this.expirationSeconds = expirationHours * 3600L;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("gestao-avaliacoes")
                .subject(user.getEmail())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationSeconds))
                .claim("userId", user.getId())
                .claim("name", user.getName())
                .claim("type", user.getType().name())
                .build();
        return encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public Optional<String> extractEmail(String token) {
        try {
            Jwt jwt = decoder.decode(token);
            return Optional.ofNullable(jwt.getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean isValid(String token) {
        try {
            Jwt jwt = decoder.decode(token);
            return jwt.getExpiresAt() != null && jwt.getExpiresAt().isAfter(Instant.now());
        } catch (Exception e) {
            return false;
        }
    }
}
