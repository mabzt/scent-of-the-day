# SCENT OF THE DAY 

A fragrance recommendation platform built for fragrance enthusiasts, by enthusiasts.
SOTD recommends what to wear today based on your collection and the weather, suggests
layering combinations, and tells you when something on your wishlist goes on promotion.

## Highlights

- **Event-driven microservices**: three services, each owning its own PostgreSQL database,
  communicating through Kafka rather than shared data or synchronous calls.
- **Transactional outbox with Debezium CDC**: state changes and their events are committed
  atomically, then streamed to Kafka, so no event is lost and none is published for a
  rolled-back change.
- **Hexagonal architecture**: domain logic is framework-free; Postgres, JPA, Kafka and web
  concerns sit behind adapters at the hexagon's edge.
- **Read scaling**: Fragrance Service routes read-only transactions to a PostgreSQL replica
  and writes to the primary.
- **AI-assisted recommendations**: Anthropic API, combined with live weather from OpenWeather,
  cached per user per day in Redis.



## Services

### User Service
Source of truth for users. Handles onboarding and stores each user's fragrance collection
and wishlist, publishing changes as events for other services.

### Fragrance Service
- Recommends a scent of the day and layering combinations from the user's collection,
  using the local weather and the Anthropic API.
- Maintains the fragrance catalog from scraped promotion and release events.
- Matches promotions against wishlists and requests notifications for interested users.
- *(Planned)* Purchase assistant: suggests fragrances that fill gaps in a collection
  within the user's budget.

### Communication Service
The messenger. Sends email and push notifications, and deliberately knows nothing about
fragrances. Other services ask it to send a template to a user; it handles the rest.


## Tech stack

Java 25 · Spring Boot 4.x · PostgreSQL · Apache Kafka (Amazon MSK) · Debezium ·
Redis · AWS (Cognito, API Gateway, Lambda, EventBridge Scheduler, SES, SNS Mobile Push) ·
Testcontainers


## Architecture

See the detailed architecture documentation:

Diagrams, event flows and design decisions: [Architecture Documentation](docs/ARCHITECTURE.md)


## Project Board

Track the backlog and in-progress user stories on the
[Scent Of The Day Project Board](https://github.com/users/mabzt/projects/2/views/1)


[![Open Issues](https://img.shields.io/github/issues/mabzt/scent-of-the-day)](https://github.com/mabzt/scent-of-the-day/issues)
![Closed Issues](https://img.shields.io/github/issues-closed/mabzt/scent-of-the-day)


[![Coverage](.github/badges/jacoco.svg)](https://github.com/mabzt/scent-of-the-day/actions)