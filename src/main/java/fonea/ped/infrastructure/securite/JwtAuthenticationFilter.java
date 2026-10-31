package fonea.ped.infrastructure.securite;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtre d'authentification sans état : lit l'en-tête
 * {@code Authorization: Bearer <jwt>}, vérifie la signature puis place
 * l'utilisateur dans le {@code SecurityContext}.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIXE = "Bearer ";

    private final JwtService jwtService;
    private final UtilisateurDetailsService utilisateurDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UtilisateurDetailsService utilisateurDetailsService) {
        this.jwtService = jwtService;
        this.utilisateurDetailsService = utilisateurDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String entete = request.getHeader("Authorization");
        if (entete != null && entete.startsWith(PREFIXE)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            String jeton = entete.substring(PREFIXE.length()).trim();
            jwtService.verifier(jeton).ifPresent(verifie -> {
                UserDetails details = utilisateurDetailsService
                        .loadUserByUsername(verifie.idUtilisateur());
                UsernamePasswordAuthenticationToken authentification =
                        new UsernamePasswordAuthenticationToken(
                                details, null, details.getAuthorities());
                authentification.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentification);
            });
        }
        chain.doFilter(request, response);
    }
}
