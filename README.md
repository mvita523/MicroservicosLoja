Microservices project (Lombok + ModelMapper + PostgreSQL) - Full generated scaffold
Properties: Java 19, Lombok 1.18.28

Modules:
- identity-service (8080)
- catalog-service (8081)
- order-service (8082)
- notification-service (8083)

Prerequisites:
- Java 19, Maven, Docker, Postgres running at host 'postgres' port 5432 (user postgres/postgres db microdb)
  Example: docker run --name pg-micro -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=microdb -p 5432:5432 -d postgres:15

Build:
mvn -T 1C -pl identity-service,catalog-service,order-service,notification-service -am clean package

Run jars (example):
java -jar identity-service/target/identity-service-1.0.0.jar

Order of startup recommended: identity -> catalog -> notification -> order

Test flow example is in README of the zip.
