package fonea.ped.infrastructure.securite;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Émission et vérification de jetons JWT signés en HMAC-SHA256.
 *
 * <p>Implémentation volontairement sans dépendance externe (jjwt &amp; co) :
 * seuls le JDK et Jackson, déjà présents, sont utilisés.</p>
 */
@Service
public class JwtService {

    private static final String ALGORITHME = "HmacSHA256";
    private static final Base64.Encoder ENCODEUR =
            Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder DECODEUR = Base64.getUrlDecoder();

    private final ObjectMapper objectMapper;
    private final byte[] secret;
    private final long expirationSecondes;

    public JwtService(ObjectMapper objectMapper,
                      @Value("${ped.securite.secret}") String secret,
                      @Value("${ped.securite.expiration-minutes:120}") long expirationMinutes) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException(
                    "ped.securite.secret doit contenir au moins 32 caractères.");
        }
        if (expirationMinutes <= 0) {
            throw new IllegalStateException("ped.securite.expiration-minutes doit être positif.");
        }
        this.objectMapper = objectMapper;
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationSecondes = expirationMinutes * 60;
    }

    public long getExpirationSecondes() {
        return expirationSecondes;
    }

    /** Jeton décodé et vérifié. */
    public record Jeton(String idUtilisateur, String email, String role, long expiration) {
    }

    public String generer(String idUtilisateur, String email, String role) {
        long maintenant = Instant.now().getEpochSecond();
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("sub", idUtilisateur);
        claims.put("email", email);
        claims.put("role", role);
        claims.put("iat", maintenant);
        claims.put("exp", maintenant + expirationSecondes);

        try {
            String entete = encoder(objectMapper.writeValueAsBytes(
                    Map.of("alg", "HS256", "typ", "JWT")));
            String charge = encoder(objectMapper.writeValueAsBytes(claims));
            String signature = encoder(signer(entete + "." + charge));
            return entete + "." + charge + "." + signature;
        } catch (Exception e) {
            throw new IllegalStateException("Impossible de générer le jeton.", e);
        }
    }

    public Optional<Jeton> verifier(String jeton) {
        if (jeton == null || jeton.isBlank()) {
            return Optional.empty();
        }
        String[] parties = jeton.split("\\.");
        if (parties.length != 3) {
            return Optional.empty();
        }
        try {
            byte[] signatureAttendue = signer(parties[0] + "." + parties[1]);
            byte[] signatureFournie = DECODEUR.decode(parties[2]);
            if (!MessageDigest.isEqual(signatureAttendue, signatureFournie)) {
                return Optional.empty();
            }
            JsonNode claims = objectMapper.readTree(DECODEUR.decode(parties[1]));
            long expiration = claims.path("exp").asLong(0);
            if (expiration <= Instant.now().getEpochSecond()) {
                return Optional.empty();
            }
            String sub = claims.path("sub").asText(null);
            if (sub == null || sub.isBlank()) {
                return Optional.empty();
            }
            return Optional.of(new Jeton(
                    sub,
                    claims.path("email").asText(null),
                    claims.path("role").asText(null),
                    expiration));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private byte[] signer(String contenu) throws Exception {
        Mac mac = Mac.getInstance(ALGORITHME);
        mac.init(new SecretKeySpec(secret, ALGORITHME));
        return mac.doFinal(contenu.getBytes(StandardCharsets.UTF_8));
    }

    private String encoder(byte[] octets) {
        return ENCODEUR.encodeToString(octets);
    }
}
