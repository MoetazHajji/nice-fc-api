# ⚽ Nice FC API – Spring Boot REST Application

Ce projet est une API REST développée avec **Spring Boot**, qui permet de gérer les équipes de football et leurs joueurs pour le club de **Nice**. Elle permet de :
- Ajouter une équipe avec ou sans joueurs
- Lister les équipes paginées avec tri
- Étendre facilement les fonctionnalités (Bonus)

---

## 🧰 Tech Stack

| Composant         | Choix                                   |
|------------------|-----------------------------------------|
| Langage          | Java 21                                 |
| Framework        | Spring Boot (Web, Data JPA, Validation) |
| Base de données  | PostgreSQL                              |
| ORM              | Hibernate                               |
| Build tool       | Maven                                   |
| Documentation    | Swagger (Springdoc OpenAPI)             |
| Tests            | JUnit, Mockito                          |

---

## 📦 Installation du projet

### 1. Cloner le repository
```bash
git clone https://github.com/votre-utilisateur/nice-fc-api.git
cd nice-fc-api
```

### 2. Configuration de la base de données PostgreSQL

Crée une base de données :
```sql
CREATE DATABASE nice_fc;
```

Met à jour `src/main/resources/application.properties` :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nice_fc
spring.datasource.username=postgres
spring.datasource.password=mot_de_passe

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

### 3. Lancer le projet
Via Maven :
```bash
./mvnw spring-boot:run
```
Ou directement :
```bash
java -jar target/nice-fc-api.jar
```

---

### 4. Tester l’API

Une fois l'application lancée :
- Swagger UI disponible à : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🔍 Endpoints de l’API

| Méthode | URL                    | Description                                              |
|---------|------------------------|----------------------------------------------------------|
| `GET`   | `/api/teams`           | Retourne la liste paginée des équipes avec joueurs       |
| `POST`  | `/api/teams`           | Ajoute une nouvelle équipe (avec ou sans joueurs)        |

**Exemple JSON POST** :
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

- `controller` : Gère les endpoints REST
- `service` : Contient la logique métier
- `repository` : Accès aux données avec JPA
- `entity` : Représentation des tables SQL
- `dto` : Objets de transfert pour requêtes/réponses
- `exception` : Gestion centralisée des erreurs

---

## ✅ Tests

Les tests unitaires et d’intégration sont disponibles sous `src/test/java/...`

- Frameworks utilisés : `JUnit 5`, `Mockito`
- Tests couvrent :
    - Services
    - Repositories
    - Endpoints REST (MockMvc)

---

## ℹ️ Choix techniques

- **Spring Boot** : Démarrage rapide et convention over configuration
- **PostgreSQL** : Base relationnelle open source fiable
- **Hibernate** : Intégration facile avec Spring Data JPA
- **Springdoc** : Pour générer automatiquement la doc de l’API

---

## ⏱️ Temps passé

> Environ X heures réparties entre conception, développement, test et documentation.
