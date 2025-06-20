# ⚽ Nice FC API – Spring Boot REST Application

Ce projet est une API REST développée avec **Spring Boot** (Java 21, JDK 21), qui permet de gérer les équipes de football et leurs joueurs pour le club de **Nice**. Il offre :
- **CRUD** simplifié pour les équipes et joueurs (ajout avec ou sans joueurs)
- **Liste paginée** et **tri dynamique** des équipes
- **Validation** des entrées (JSR‑303) avec gestion centralisée des erreurs
- **Services génériques** réutilisables pour mapping DTO ↔ Entity

---

## 🧰 Tech Stack

| Composant         | Choix                                    |
|------------------|------------------------------------------|
| Langage          | Java 21 / JDK 21                         |
| Framework        | Spring Boot (Web, Data JPA, Validation)  |
| Base de données  | PostgreSQL                               |
| ORM              | Hibernate                                |
| Build tool       | Maven                                    |
| Tests            | JUnit 5, Mockito, MockMvc                |

---

## 📦 Installation

### 1. Cloner le repository
```bash
git clone https://github.com/MoetazHajji/nice-fc-api.git
cd nice-fc-api
```

### 2. Configurer PostgreSQL
```sql
CREATE DATABASE nicefcdb;
```

Configurer `src/main/resources/application.properties` :
```properties
spring.application.name=nice-fc-api
spring.datasource.url=jdbc:postgresql://localhost:5432/nicefcdb
spring.datasource.username=postgres
spring.datasource.password=admin
server.port=9090

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
spring.jpa.properties.javax.persistence.validation.mode=none

```

### 3. Lancer l’application
Via Maven :
```bash
./mvnw spring-boot:run
```
Ou :
```bash
java -jar target/nice-fc-api.jar
```

---

## 🔍 Endpoints

| Méthode | URL           | Description                                      |
|--------|---------------|--------------------------------------------------|
| `GET`  | `/api/teams`  | Liste paginée triée des équipes + joueurs        |
| `POST` | `/api/teams`  | Crée une équipe (option : liste vide de joueurs) |

**Exemple POST** :
```json
{
  "name": "OGC Nice",
  "acronym": "OGCN",
  "budget": 85000000,
  "players": [
    { "name": "Jean Dupont", "position": "Attaquant" },
    { "name": "Hugo Martin", "position": "Défenseur" }
  ]
}
```

---

## 🏗️ Architecture

```
controller  → service (business) → repository (JPA)
  ↓              ↓                     ↓
 DTOs ↔ mappers ⇆ Entities              DB
```

- **`dto/`**  : objets de requête/réponse
- **`mappers/`** : MapStruct (DTO ↔ Entity)
- **`service/`**: service générique + spécifique (logique métier)
- **`controller/`**: couches REST
- **`repository/`**: interfaces Spring Data JPA
- **`entity/`**: modèles JPA
- **`error/`**: gestion centralisée des exceptions et validation

---

## ✅ Fonctionnalités clés

### 1. Mappers DTO ↔ Entity
- **TeamMapper**, **PlayerMapper** avec `@Mapper(componentModel="spring")`
- Listes imbriquées gérées via méthodes explicites `toPlayers()`, `toPlayerDtos()`

### 2. Service générique
- **`AbstractService<Req, Res, T, ID>`**
- Méthodes réutilisables `save()`, `getAll(Pageable, sortBy)`
- Implémentation spécifique `TeamService` pour mapping + cascade joueurs

### 3. Validation
- JSR‑303 sur **DTOs** (`@NotBlank`, `@NotNull`, `@Size`, `@Valid`)
- **GlobalExceptionHandler** pour renvoyer un JSON `ErrorResponse` structuré

### 4. Tests
- **Unit tests** (`TeamServiceTest`) avec Mockito pour :
  - `save()` (cas normal + liste vide)
  - `getAll()` paginé et trié
- **Slice Integration** (`TeamControllerTest`) avec MockMvc pour :
  - `POST /api/teams`
  - `GET /api/teams`
  - cas liste vide et absence de champ `players`

---

## ℹ️ Choix techniques

- **Spring Boot** : convention over configuration, starters intégrés
- **MapStruct** : mappage rapide et typé, cycle de build
- **JSR‑303** : validation au plus près du contrôleur
- **JUnit 5 + Mockito** : tests unitaires et slice

---

## ⏱️ Temps passé

> Environ 5 heures (conception, dev, validation, tests, documentation)

