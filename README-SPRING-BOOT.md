# SmartBank - Spring Boot Conversion

Converted from the supplied Spring MVC project.

## Main changes
- Added `SmartBankApplication.java` with `@SpringBootApplication`.
- Replaced the old Maven dependencies with Spring Boot starters.
- Removed `web.xml`, `spring-servlet.xml`, and `applicationContext.xml`.
- Moved database configuration to `src/main/resources/application.properties`.
- Added `HibernateConfig.java` for the existing native Hibernate `SessionFactory` and transaction manager.
- Kept the existing controllers, services, DAOs, entities, JSP views, CSS and JavaScript.
- Java 17 is retained.
- JSP views remain under `src/main/webapp/WEB-INF/views`.
- The root controller redirects `/` to `/login`.

## Before running
Edit `src/main/resources/application.properties` and set:
`spring.datasource.password=YOUR_MYSQL_PASSWORD`

Make sure MySQL is running and the `smart_bank_db` database can be created by the configured user.

## Eclipse
Import as Existing Maven Project -> Maven Update Project -> run `SmartBankApplication.java` as Java Application.

Open: `http://localhost:8080/`

## Maven
`mvn spring-boot:run`
or
`mvn clean package`

The build creates `target/smart-bank.war`.

## Why Spring Boot 2.7?
The original project uses `javax.*` servlet/JSP APIs and Hibernate 5.6. Boot 2.7 lets us convert the project without a large `javax.*` -> `jakarta.*` rewrite. Moving to Spring Boot 3 would require that namespace migration.
