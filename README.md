# SCENT OF THE DAY 
SOTD is a fragrance recommendation service built on Java 25, Springboot4, structured using Hexagonal(Ports and Adapters) architecture.
Persistence runs PostgresSQL with a primary/replica setup, routing writes to the primary 
and reads to the replica for horizontal scalling.

The codebase keeps domain logic framework-free, isolating Postgres, JPA and web concerns 
behind adapters at the hexagons edge.


## Project Board

Track the backlog and in-progress user stories on the
[Scent Of The Day Project Board](https://github.com/users/mabzt/projects/2/views/1)


[![Open Issues](https://img.shields.io/github/issues/mabzt/scent-of-the-day)](https://github.com/mabzt/scent-of-the-day/issues)
![Closed Issues](https://img.shields.io/github/issues-closed/mabzt/scent-of-the-day)


[![Coverage](.github/badges/jacoco.svg)](https://github.com/mabzt/scent-of-the-day/actions)