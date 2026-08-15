# Product Manager

Aplikacija za upravljanje trgovinama, zaposlenicima i narudžbama proizvoda, s web sučeljem i scraperom koji dohvaća proizvode s vanjske stranice.

Nastalo na TIS Academy Spring radionici 2026 kao timski projekt (tim Mljet), uz naknadne ispravke i doradu.

## Tehnologije

| Sloj | Tehnologija |
| --- | --- |
| Aplikacija | Java 25, Spring Boot 4.1 |
| Baza | Spring Data JPA, Hibernate, H2 |
| Sučelje | Thymeleaf |
| Mapiranje | MapStruct |
| Scraping | jsoup |
| Dokumentacija | springdoc OpenAPI (Swagger UI) |

## Preduvjeti

Projekt ovisi o modulu `hr.tis.academy:common`. Ako ga nemaš u lokalnom Maven repozitoriju, prvo ga instaliraj:

```bash
cd ../common && ./mvnw install
```

## Pokretanje

```bash
./mvnw spring-boot:run
```

Aplikacija se diže na `http://localhost:9090/product_manager_mljet`.

Baza je H2 u datoteci s `ddl-auto=create`, pa se shema gradi iznova pri svakom pokretanju, a početni podaci učitavaju iz `src/main/resources/init.sql`.

## Sučelje

| Stranica | Putanja |
| --- | --- |
| Naslovnica | `/` |
| Pregled proizvoda | `/products-view` |
| Popis trgovina | `/stores-view` |
| Detalji trgovine | `/stores-view/{storeId}` |
| Nova trgovina | `/stores-view/new` |

## API

| Metoda | Putanja | Opis |
| --- | --- | --- |
| `GET` | `/stores` | sve trgovine |
| `GET` | `/stores/{id}` | jedna trgovina |
| `POST` | `/stores` | nova trgovina |
| `PUT` | `/stores/{id}` | zamjena podataka trgovine |
| `PATCH` | `/stores/{id}` | djelomična izmjena |
| `DELETE` | `/stores/{id}` | brisanje |
| `GET` | `/products` | proizvodi za datum |
| `GET` | `/products/sum` | zbroj cijena za datum |
| `GET` | `/products/fetch` | dohvat proizvoda s weba |
| `POST` | `/products/save` | dohvat i spremanje proizvoda |
| `GET` | `/orders` | sve narudžbe |
| `POST` | `/orders` | nova narudžba |
| `GET` | `/common/image` | generiranje PNG slike s tekstom |

## Profili

Repozitorij proizvoda ima dvije izvedbe koje se biraju Spring profilom:

| Profil | Pohrana |
| --- | --- |
| `memory` | u memoriji (zadano, postavljeno u `application.properties`) |
| `db` | izravno preko JDBC-a |

## Rukovanje greškama

Sve iznimke prolaze kroz `@ControllerAdvice`. Nepostojeći zapisi vraćaju 404 uz poruku, neispravan unos 400, a neočekivane greške 500 s referentnim identifikatorom koji se zapisuje u log.
