package fonea.ped.interfaceAdapters.controllers;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import fonea.ped.domain.exceptions.DomaineException;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos.Erreur;

/**
 * Traduction uniforme des exceptions en réponses JSON.
 *
 * <p>Sans ce gestionnaire, une exception métier remontait en 500 avec une page
 * d'erreur HTML : le client ne pouvait ni distinguer un 404 d'un conflit, ni
 * exploiter le message.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger JOURNAL = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** Exceptions métier : le statut est porté par l'exception elle-même. */
    @ExceptionHandler(DomaineException.class)
    public ResponseEntity<Erreur> gererDomaine(DomaineException exception) {
        int statut = exception.statutHttp();
        return ResponseEntity.status(statut)
                .body(new Erreur(statut, exception.getMessage()));
    }

    /** Entrée HTTP invalide (JSON mal formé, champ obligatoire absent...). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Erreur> gererValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(erreur -> erreur.getField() + " : " + erreur.getDefaultMessage())
                .collect(Collectors.joining(" ; "));
        if (message.isBlank()) {
            message = "Requête invalide.";
        }
        return ResponseEntity.badRequest().body(new Erreur(400, message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Erreur> gererJsonIllisible(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest()
                .body(new Erreur(400, "Corps de requête JSON illisible ou invalide."));
    }

    /** Méthode HTTP non prévue par l'endpoint (par exemple GET sur une route POST). */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Erreur> gererMethodeNonSupporte(
            HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new Erreur(405, "Méthode HTTP non supportée pour cette ressource."));
    }

    /** Route ou ressource statique inexistante. */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Erreur> gererRessourceIntrouvable(NoResourceFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Erreur(404, "Ressource introuvable."));
    }

    /** Mauvais identifiants ou jeton invalide. */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Erreur> gererAuthentification(AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new Erreur(401, "Email ou mot de passe incorrect."));
    }

    /** Permission manquante ou ressource appartenant à un autre utilisateur. */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Erreur> gererAccesRefuse(AccessDeniedException exception) {
        String message = exception.getMessage();
        if (message == null || message.isBlank() || "Access Denied".equals(message)) {
            message = "Permission insuffisante pour cette opération.";
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Erreur(403, message));
    }

    /** Argument métier invalide. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Erreur> gererArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
                .body(new Erreur(400, exception.getMessage()));
    }

    /** Conflit d'état (offre déjà publiée, chaîne métier incomplète...). */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Erreur> gererEtat(IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Erreur(409, exception.getMessage()));
    }

    /** Filet de sécurité : on ne divulgue jamais la pile d'appels au client. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Erreur> gererInattendu(Exception exception) {
        JOURNAL.error("Erreur non gérée", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Erreur(500, "Erreur interne du serveur."));
    }
}
