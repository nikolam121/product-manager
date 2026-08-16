# Product Manager

An application for managing stores, employees, and product orders, with a web interface and a scraper that pulls products from an external site.

Built during the TIS Academy Spring workshop 2026 as a team project (team Mljet), with fixes and improvements made afterward.

## Tech stack

| Layer | Technology |
| --- | --- |
| Application | Java 25, Spring Boot 4.1 |
| Database | Spring Data JPA, Hibernate, H2 |
| UI | Thymeleaf |
| Mapping | MapStruct |
| Scraping | jsoup |
| Documentation | springdoc OpenAPI (Swagger UI) |

## Prerequisites

The project depends on the `hr.tis.academy:common` module. If you don't have it in your local Maven repository, install it first:

```bash
cd ../common && ./mvnw install
```

## Running

```bash
./mvnw spring-boot:run
```

The app starts at `http://localhost:9090/product_manager_mljet`.

The database is a file-based H2 instance with `ddl-auto=create`, so the schema is rebuilt on every startup and seed data is loaded from `src/main/resources/init.sql`.

## Pages

| Page | Path |
| --- | --- |
| Home | `/` |
| Browse products | `/products-view` |
| Store list | `/stores-view` |
| Store details | `/stores-view/{storeId}` |
| New store | `/stores-view/new` |

## API

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/stores` | all stores |
| `GET` | `/stores/{id}` | a single store |
| `POST` | `/stores` | create a store |
| `PUT` | `/stores/{id}` | replace store data |
| `PATCH` | `/stores/{id}` | partial update |
| `DELETE` | `/stores/{id}` | delete |
| `GET` | `/products` | products for a date |
| `GET` | `/products/sum` | sum of prices for a date |
| `GET` | `/products/fetch` | fetch products from the web |
| `POST` | `/products/save` | fetch and save products |
| `GET` | `/orders` | all orders |
| `POST` | `/orders` | create an order |
| `GET` | `/common/image` | generate a PNG image with text |

## Profiles

The product repository has two implementations selected by a Spring profile:

| Profile | Storage |
| --- | --- |
| `memory` | in-memory (default, set in `application.properties`) |
| `db` | directly via JDBC |

## Error handling

All exceptions go through a `@ControllerAdvice`. Missing records return 404 with a message, invalid input returns 400, and unexpected errors return 500 with a reference id that's logged.
