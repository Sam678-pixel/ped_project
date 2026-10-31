# Résumé du projet — FONEA / PED

> **Date de l'analyse :** 23 octobre 2026  
> **Méthode :** lecture statique de l'arborescence, du code source, du schéma SQL, de la configuration et des rapports de tests, puis tentative de compilation avec Maven.
>
> **Verdict rapide :** le projet possède une base métier riche et une architecture en couches identifiable, mais **il ne compile pas actuellement** et **aucun scénario de bout en bout n'est exécutable**. L'avancement est réel sur le domaine et les use cases, mais l'intégration (persistance, Spring, API/IHM, tests automatisés) reste largement à terminer.

---

## 1. Vue d'ensemble

| Élément | Valeur |
|---|---|
| Nom | `fonea:ped` |
| Type | Application Java / Spring Boot |
| Version | `0.0.1-SNAPSHOT` |
| Java | 21 |
| Build | Maven (`mvnw`, `mvnw.cmd`) |
| Spring Boot | 4.1.1 |
| Dépendances présentes | Spring Data JPA, Spring Security, H2, MySQL, HikariCP, DevTools |
| Base cible | MySQL `db_ped` (`localhost:3306`) |
| Point d'entrée | `src/main/java/fonea/ped/PedApplication.java` |
| Architecture | Inspirée de l'architecture hexagonale / clean architecture : `domain`, `useCase`, `interfaceAdapters`, `infrastructure` |
| Gestion de versions | Aucun dépôt Git détecté dans le dossier |

### Domaine fonctionnel

Le projet couvre le parcours d'un diplômé dans un dispositif de type FONEA :

1. création des référentiels (secteurs, domaines, métiers, postes, compétences, etc.) ;
2. création de conventions entreprise / programme ;
3. création et publication d'offres de stage ;
4. candidature d'un diplômé ;
5. présélection puis sélection ;
6. mise en stage, renouvellement, décrochage ;
7. insertion professionnelle et contrat ;
8. sécurité (utilisateurs, rôles, permissions, notifications) — modélisée mais non implémentée.

---

## 2. Chiffres clés

| Zone | Fichiers Java | Lignes (approx.) | Commentaire |
|---|---:|---:|---|
| `domain` | 104 | 2 509 | Modèles métier + 46 exceptions |
| `useCase` | 66 | 2 081 | 12 use cases, 30 ports, Request/Response |
| `interfaceAdapters` | 50 | 1 617 | 30 gateways, 6 controllers, 7 presenters, 7 viewmodels |
| `infrastructure` | 9 | 342 | Connexion, 3 repositories JDBC, 5 mappers |
| **Total `src/main/java`** | **230** | **6 562** | |
| `src/test/java` | 8 | 1 205 | 7 programmes interactifs + `InMemoryRepositories` |

Autres éléments :

- **30 ports** de repository dans la couche `useCase`.
- **30 gateways** dans `interfaceAdapters`, mais seulement **3 repositories JDBC** dans l'infrastructure.
- **12 use cases** implémentés.
- **7 presenters** et **7 viewmodels**.
- **6 controllers** seulement (il manque notamment un controller pour la mise en stage).
- **46 classes d'exception** métier.
- **1 schéma SQL** de 391 lignes couvrant une trentaine de tables.
- **148 tests** dans les anciens rapports Surefire, tous réussis, mais ces tests ne correspondent plus aux sources actuelles.

---

## 3. Organisation réelle du code

```text
src/main/java/fonea/ped/
├── PedApplication.java
├── domain/
│   ├── Offres/             (23 classes : offres, critères, compétences, publication...)
│   ├── convention/model/   (8 classes : Convention, Programme, Résiliation...)
│   ├── recrutement/        (7 classes : Candidature, Document, Preselection, Selection...)
│   ├── MiseEnStage/        (6 classes : MiseEnStage, Renouvellement, Decrochage...)
│   ├── insertion/          (4 classes : Insertion, Contrat, TypeContrat...)
│   ├── diplome/            (2 classes)
│   ├── entreprise/         (2 classes)
│   ├── securite/           (6 classes)
│   └── exceptions/         (46 exceptions)
├── useCase/
│   ├── useCaseOffres/          → créer une offre
│   ├── UseCaseConvention/      → créer / résilier une convention
│   ├── useCaseRecrutement/     → présélectionner / sélectionner
│   ├── useCaseDiplome/         → créer un diplômé / postuler
│   ├── useCaseEntreprise/      → créer une entreprise
│   ├── useCaseMiseEnStage/     → mettre en stage / renouveler / décrochage
│   └── useCaseInsertion/       → insérer / créer le contrat
├── interfaceAdapters/
│   ├── controllers/        (6)
│   ├── gateways/           (30)
│   ├── presenters/         (7)
│   └── viewModels/         (7)
└── infrastructure/
    └── persistence/
        ├── config/         (DatabaseConnection + schema.sql)
        ├── mappers/        (5)
        └── repositories/   (3 repositories JDBC)
```

### Points d'architecture

- La séparation domaine / use cases / adapters est globalement respectée.
- Les use cases dépendent de **ports** (`*Repository`) et non des implémentations, ce qui est un bon point.
- Les gateways sont prévus comme adaptateurs entre use cases et infrastructure.
- **Cependant**, les gateways dépendent d'une classe de test (`InMemoryRepositories`) au lieu des repositories d'infrastructure, ce qui casse la compilation.
- Le nommage des packages est irrégulier : `useCase` vs `UseCase`, `ports` vs `port`, `UseCaseOffres`, `MiseEnStage` avec majuscule, etc.

---

## 4. Avancement fonctionnel par module

### 4.1 Offres de stage

**Use case présent :** `CreerOffreUseCase`

Règles déjà codées :

- intitulé obligatoire ;
- nombre de places strictement positif ;
- convention existante et **active** ;
- vérification de l'existence de l'entreprise, du domaine, du poste, du métier et du secteur ;
- au moins une compétence, un critère, un prérequis et une qualification ;
- vérification de l'existence de chaque élément des listes ;
- création de l'offre au statut `BROUILLON` ;
- création des liaisons offre/compétence, offre/critère, offre/prérequis, offre/qualification.

**Limites / anomalies :**

- L'offre est créée avec un mauvais ordre d'arguments : `idDomaine`, `idPoste` et `idEntreprise` sont passés à la place de `idPoste`, `idDomaine` et `idConvention` dans `OffresStages`.
- Utilisation de `DateInvalideException` pour un intitulé vide.
- Utilisation de `AucunCritereAssocieException` aussi pour les prérequis.
- Aucun use case de **publication**, **clôture**, **annulation**, **modification** ou **consultation** d'offre, alors que les méthodes existent dans le domaine (`publier`, `cloturer`, `changerStatut`).

**État :** partiel mais structuré.

---

### 4.2 Conventions

**Use cases présents :** `CreerConventionUseCase`, `ResilierConventionUseCase`

Règles déjà codées :

- date d'effet obligatoire et non passée ;
- taux FONEA et taux entreprise positifs ;
- programme et entreprise existants ;
- création au statut `BROUILLON` ;
- résiliation : convention existante, non déjà résiliée, active, motif et initiateur obligatoires, annulation des offres actives liées.

**Anomalies importantes :**

- `Convention.resilier(...)` contient une condition **inversée** :
  ```java
  if (statutConvention == StatutConvention.ACTIVE) {
      throw new IllegalStateException("Seule une convention active peut être résiliée...");
  }
  ```
  Une convention active ne peut donc jamais être résiliée, alors que c'est justement le cas nominal.
- `ResilierConventionUseCase` crée une première `Resiliation`, puis `Convention.resilier(...)` en crée une seconde avec un `id` nul.
- Le motif n'est pas persisté correctement : la réponse utilise `resiliation.getMotifResiliation()`, qui vaut `null` avec le constructeur utilisé.
- Le schéma SQL ne contient pas les tables `Programme` et `TypeConvention`, pourtant présentes dans le domaine.

**État :** use cases présents, mais le scénario de résiliation est fonctionnellement cassé.

---

### 4.3 Recrutement : postuler, présélectionner, sélectionner

**Use cases présents :** `PostulerUseCase`, `PreselectionnerUseCase`, `SelectionnerUseCase`

Règles déjà codées :

- vérification du diplômé, de l'offre, du statut `PUBLIEE`, de la publication active et de la date d'expiration ;
- prévention des candidatures en double ;
- vérification de la complétude des documents ;
- création de la candidature au statut `EN_COURS` ;
- présélection : candidature existante, documents complets, statut compatible, absence de doublon ;
- sélection : présélection existante, candidature bien présélectionnée, absence de doublon, mise à jour des statuts.

**Anomalies importantes :**

- `PostulerUseCase` cherche une candidature existante à partir de `request.getIdCandidature()` **avant** de créer la nouvelle candidature. Cette étape n'a pas de sens dans le flux actuel.
- `PostulerRequest` mélange un objet `Candidature` et une liste de `Document`, alors que le controller et les tests utilisent des signatures différentes.
- `PreselectionnerUseCase` contient une condition dupliquée :
  ```java
  if (statut != StatutCandidature.EN_COURS && statut != StatutCandidature.EN_COURS)
  ```
- Le statut de sélection réutilise `StatutCandidature` au lieu d'un enum dédié `StatutSelection` (présent dans les anciens tests et dans `src/test/resources/mirror-java`, mais absent du code principal).
- Aucun use case de rejet, retrait, gestion des documents ou consultation des candidatures.
- Le module sécurité n'intervient pas dans le processus.

**État :** les trois étapes existent, mais les contrats d'entrée/sortie ne sont pas cohérents.

---

### 4.4 Mise en stage

**Use cases présents :** `MettreEnStageUseCase`, `RenouvelerStageUseCase`, `DeclarerDecrochageUseCase`

Règles déjà codées :

- sélection existante et validée ;
- absence de mise en stage déjà existante ;
- dates obligatoires et cohérentes ;
- renouvellement possible uniquement après un stage terminé, sans doublon, avec dates valides ;
- décrochage possible uniquement pour un stage en cours ou renouvelé, avec raison et entreprise valides ;
- passage du stage au statut `DECROCHE`.

**Limites / anomalies :**

- `MettreEnStageRequest` exige un `idEntreprise`, mais les tests et certains appels utilisent une signature à 3 arguments.
- Le use case utilise `StatutCandidature` pour vérifier le statut d'une `Selection`, ce qui mélange les concepts.
- Le presenter de mise en stage existe, mais **aucun `MiseEnStageController`** n'est présent.
- Les statuts du stage sont dans le domaine, mais le workflow complet (`EN_ATTENTE → EN_COURS → TERMINE/RENOUVELE/DECROCHE`) n'est pas exposé par des use cases dédiés (démarrage/terminaison effectués directement dans les tests).

**État :** bonne couverture métier, exposition applicative incomplète.

---

### 4.5 Insertion et contrat

**Use case présent :** `InsererUseCase`

Règles déjà codées :

- mise en stage existante et terminée ;
- absence d'insertion déjà existante ;
- poste et type de contrat existants ;
- date d'embauche obligatoire ;
- salaire strictement positif ;
- dates de contrat cohérentes ;
- création de l'insertion puis du contrat associé.

**Limites :**

- Le statut du contrat n'est pas explicitement affecté lors de la création (le constructeur à 6 arguments laisse `statutContrat` à `null`).
- Aucun use case de mise à jour, rupture ou consultation des contrats.
- Pas de controller dédié à l'insertion ? Il existe `InsertionController`, mais pas de controller pour la mise en stage.

**État :** scénario nominal présent, cycle de vie du contrat incomplet.

---

### 4.6 Diplôme et entreprise

**Use cases présents :** `CreerDiplomeUseCase`, `CreerEntrepriseUseCase`

- Création d'un diplômé avec validation du nom, du prénom et de la date de naissance.
- Création d'une entreprise avec validation de la raison sociale.
- Les controllers et presenters associés existent.
- Aucun use case de mise à jour, recherche, suppression ou consultation.

**État :** création uniquement.

---

### 4.7 Sécurité et notifications

**Classes présentes :** `Utilisateur`, `Role`, `Permission`, `RolePermission`, `AgentFonea`, `Notification`.

- Modélisation métier correcte sur le papier.
- **Aucune utilisation** de ces classes en dehors de leur package.
- Aucun use case, repository, controller, filtre ou configuration Spring Security.
- La dépendance `spring-boot-starter-security` est présente mais inexploitée.

**État :** modélisation seulement, implémentation inexistante.

---

## 5. État de compilation et d'exécution

### Résultat de la commande

```powershell
.\mvnw.cmd -o -DskipTests compile
```

**Résultat : échec de compilation.**

### Blocages principaux

1. **30 gateways importent une classe de test depuis le code principal**

   Exemple : `src/main/java/fonea/ped/interfaceAdapters/gateways/OffreGateway.java`

   ```java
   public OffreGateway() {
       this(new InMemoryRepositories.InMemoryOffreRepository());
   }
   ```

   Or `InMemoryRepositories` se trouve dans `src/test/java/infrastructure/`, donc il n'est pas disponible à la compilation du `main`.

2. **Conflit de signature dans `CandidatureGateway`**

   - `CandidatureRepository.trouverParId(Object object)` dans le port ;
   - `CandidatureGateway.trouverParId(String id)` dans le gateway.

   Le gateway n'implémente donc pas correctement l'interface.

3. **Import invalide dans `ConventionController`**

   ```java
   import fonea.ped.useCase.useCaseDiplome.*;
   ```

   Le package `fonea.ped.useCase.useCaseDiplome` n'existe pas à la racine : les sous-packages sont `port` et `useCase`.

4. **`InMemoryRepositories` ne compile pas non plus**

   Le fichier importe :

   ```java
   import java.util.concurrent.atomic.AtomicString;
   ```

   Cette classe n'existe pas dans le JDK. Les tests ne peuvent donc pas être compilés tels quels.

5. **Tests obsolètes par rapport au domaine**

   Les tests actuels utilisent des constructeurs qui ne correspondent plus aux classes principales, par exemple :

   - `Poste(id, libelle)` au lieu de `Poste(id, libelle, idMetier)` ;
   - `Metier(id, libelle)` au lieu de `Metier(id, libelle, idSecteur)` ;
   - `Competence(id, libelle, ...)` alors que le constructeur courant prend 2 arguments ;
   - `OffresStages` avec un ordre de paramètres différent ;
   - `Document` avec 6 arguments alors que le constructeur en prend 4 ;
   - `Candidature` avec un ordre de paramètres différent ;
   - `CreerConventionRequest` avec 5 arguments alors que le constructeur en prend 7 ;
   - `MettreEnStageRequest` avec 3 arguments alors que le constructeur en prend 4 ;
   - `PostulerRequest` avec un identifiant de document alors que le constructeur attend une liste de `Document` et une `Candidature` ;
   - utilisation de `StatutSelection`, absent du code principal actuel.

6. **Fichier Java parasite à la racine**

   `DeclarerDecrochageUseCase.java` se trouve à la racine du projet, avec un package et des imports incorrects (`fonea.ped.useCsaeMiseEnStage`, `fonea.ped.MiseEnStage.ports`, etc.). Il n'est pas compilé par Maven mais pollue le projet.

### Conclusion sur l'exécution

- `mvn compile` échoue.
- `mvn test` ne peut pas aboutir.
- Le lancement Spring Boot n'est pas viable en l'état.
- Le dossier `target/` contient des classes compilées anciennes, ce qui peut donner une fausse impression de projet fonctionnel.

---

## 6. État des tests

### Ce qui existe dans les sources actuelles

`src/test/java` contient :

- `infrastructure/InMemoryRepositories.java` ;
- 7 programmes de test interactifs :
  - `TestCreerOffre`
  - `TestCreerConvention`
  - `TestResilierConvention`
  - `TestPostuler`
  - `TestPreselectionner`
  - `TestSelectionner`
  - `TestInserer`

Ces fichiers ne sont **pas des tests JUnit** : ce sont des programmes avec `public static void main`, menus `Scanner` et saisie clavier. Surefire ne les exécute pas comme des tests automatisés.

### Anciens rapports Surefire

Le dossier `target/surefire-reports` contient :

- 37 suites ;
- 148 tests ;
- 0 échec, 0 erreur.

Mais ces rapports datent du **23 septembre 2026** et concernent des classes de tests générées dans des packages qui ne sont plus présents dans `src/test/java` (par exemple `fonea.ped.Offres.model.OffresStagesTest`). Ils ne reflètent donc pas l'état actuel du code.

### Script de génération de tests

`scripts/mirror-test-structure.py` génère des tests miroirs avec un simple `assertTrue(true)` et un `TODO`. Ce script ne fournit pas une vraie couverture métier ; il servait visiblement à créer une structure de tests.

**État réel des tests :** très faible. Les scénarios nominaux et les cas d'erreur sont décrits dans les menus interactifs, mais ils ne sont pas automatisés, pas exécutables et pas alignés sur le code actuel.

---

## 7. Persistance, configuration et intégration

### Ce qui est présent

- Un schéma SQL complet dans `src/main/java/fonea/ped/infrastructure/persistence/config/schema.sql` :
  - référentiels ;
  - sécurité ;
  - entreprise ;
  - diplômé ;
  - convention ;
  - offres ;
  - recrutement ;
  - mise en stage ;
  - insertion ;
  - tables de liaison.
- `DatabaseConnection` avec HikariCP.
- 3 repositories JDBC seulement :
  - `JdbcDomaineRepository`
  - `JdbcSecteurRepository`
  - `JdbcTypeContratRepository`
- 5 mappers.
- Configuration MySQL dans `application.properties`.

### Ce qui ne fonctionne pas

- `DatabaseConnection.initialiser(...)` n'est **jamais appelé**.
- Aucun repository JDBC n'est branché aux gateways.
- Les gateways utilisent des implémentations en mémoire de test.
- 27 ports sur 30 n'ont **aucune implémentation JDBC**.
- `schema.sql` n'est pas chargé automatiquement : il n'est pas à la racine du classpath et aucune configuration `spring.sql.init` n'est présente.
- Le schéma ne correspond pas totalement au domaine :
  - faute `SatutConvention` dans le schéma ;
  - pas de table `Programme` ni `TypeConvention` ;
  - pas de colonne `tauxEntreprise` dans `Convention` ;
  - `Convention.TypeConvention` est un `VARCHAR` alors que le domaine utilise un objet `TypeConvention` ;
  - `OffresStage` utilise `idDomain` alors que le domaine parle de `idDomaine` ;
  - certaines tables de liaison ne sont pas représentées par des repositories.
- Les dépendances Spring Data JPA et Spring Security sont déclarées mais aucune entité JPA, aucun repository Spring et aucune configuration de sécurité n'existe.
- Aucun controller REST : les controllers sont des classes Java pures, sans annotation Spring (`@RestController`, `@Service`, `@Configuration`, etc.).
- Aucune injection de dépendances Spring ni aucun assemblage des use cases.
- `spring.jpa.ddl-auto=update` est actif alors qu'aucune entité JPA n'est définie.
- Les tests utilisent la même base MySQL que la production au lieu de H2, malgré la dépendance H2 déclarée.

---

## 8. Synthèse de l'avancement

| Axe | Estimation | Commentaire |
|---|---:|---|
| Modélisation métier | **85–90 %** | Domaine très fourni, beaucoup de règles et d'exceptions |
| Use cases | **65–70 %** | 12 use cases présents, mais contrats et logique à corriger |
| Adapters / présentation | **50–60 %** | Gateways, controllers, presenters existent mais non compilables |
| Persistance | **10–15 %** | 3 repositories JDBC sur 30 ports |
| Sécurité | **10 %** | Modèles seulement |
| Tests automatisés | **5–10 %** | Programmes interactifs, pas de vrais tests JUnit |
| Intégration Spring / API / IHM | **0 %** | Aucun câblage, aucune API, aucune interface |
| **Avancement global** | **≈ 45 %** | Base métier solide, application non exécutable |

> L'estimation globale est indicative : le projet est plus avancé sur la réflexion métier que sur la livraison d'une application fonctionnelle.

---

## 9. Principaux problèmes à traiter en priorité

### P0 — Rendre le projet compilable

1. Déplacer `InMemoryRepositories` dans `src/main/java` (ou supprimer les constructeurs sans argument des gateways qui l'utilisent).
2. Corriger `CandidatureRepository.trouverParId` : utiliser `String` au lieu de `Object`.
3. Corriger l'import de `ConventionController`.
4. Supprimer ou déplacer le fichier `DeclarerDecrochageUseCase.java` situé à la racine.
5. Remplacer `AtomicString` par `AtomicLong` ou une autre implémentation valide.
6. Aligner les constructeurs et DTOs entre domaine, use cases, controllers et tests.

### P1 — Corriger les bugs métier

1. Réparer la logique de résiliation dans `Convention.resilier`.
2. Éviter la double création de `Resiliation`.
3. Corriger l'ordre des paramètres dans `CreerOffreUseCase`.
4. Revoir complètement `PostulerUseCase` et `PostulerRequest`.
5. Corriger la condition dupliquée dans `PreselectionnerUseCase`.
6. Décider d'un statut unique pour la sélection (`StatutSelection` ou `StatutCandidature`), et l'utiliser partout.
7. Affecter un statut initial au contrat lors de l'insertion.

### P2 — Rendre l'application réellement exécutable

1. Initialiser `DatabaseConnection` au démarrage.
2. Brancher les gateways sur des repositories réels (JDBC ou JPA).
3. Implémenter les 27 repositories manquants ou assumer une persistance en mémoire.
4. Charger `schema.sql` via `spring.sql.init` ou une migration (Flyway/Liquibase).
5. Configurer Spring : beans des use cases, controllers REST ou interface console.
6. Remplacer la configuration de test MySQL par H2.

### P3 — Qualité et tests

1. Remplacer les programmes interactifs par de vrais tests JUnit 5.
2. Écrire un test par use case : cas nominal + cas d'erreur.
3. Ajouter des tests d'intégration H2 pour les repositories.
4. Ajouter une couche de tests des controllers/presenters.
5. Mettre en place une CI (`mvn verify`).
6. Nettoyer le nommage des packages et supprimer les doublons (`Motif`, `MotifResiliation`, `ResiliationMotif`, `DecrochageMotif`).

### P4 — Fonctionnalités manquantes

- publication / clôture / annulation d'une offre ;
- gestion des documents et des candidatures (rejet, retrait, consultation) ;
- controller de mise en stage ;
- gestion des utilisateurs, rôles, permissions et notifications ;
- gestion des programmes et types de convention ;
- consultation / mise à jour des diplômés, entreprises, contrats.

---

## 10. Conclusion

Le projet FONEA/PED est un **bon squelette d'application métier** : le domaine est riche, les use cases sont identifiables et l'architecture en couches est globalement respectée. Les scénarios principaux (offre, convention, candidature, sélection, stage, insertion) sont déjà pensés et partiellement codés.

En revanche, l'état actuel est celui d'un **prototype non intégré** :

- la compilation échoue ;
- les tests actuels ne sont pas de vrais tests automatisés ;
- la persistance est presque entièrement absente ;
- Spring n'est pas câblé ;
- plusieurs règles métier importantes sont incorrectes ;
- les contrats entre couches ne sont pas stabilisés.

La prochaine étape la plus rentable est de **stabiliser le build et les contrats DTO/domaine**, puis de brancher un premier parcours complet de bout en bout (créer une offre → postuler → présélectionner → sélectionner → mettre en stage → insérer) avant d'ajouter de nouvelles fonctionnalités.

---

## Annexe — Commandes utiles

```powershell
# Compiler
.\mvnw.cmd -o -DskipTests compile

# Lancer les tests
.\mvnw.cmd -o test

# Lancer l'application Spring Boot
.\mvnw.cmd -o spring-boot:run
```

> En l'état, seule la lecture du code et l'analyse statique sont possibles. Les commandes ci-dessus échouent ou ne permettent pas d'exécuter un scénario métier complet.
