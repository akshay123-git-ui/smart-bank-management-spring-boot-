# SmartBank App PIN

This version adds a separate 4-digit App PIN after normal email/password login.

## Flow
1. User opens `/login`.
2. User enters email and password.
3. If no App PIN exists, SmartBank opens `createPin.jsp`.
4. User creates and confirms a 4-digit PIN.
5. On later logins, SmartBank opens `appPin.jsp`.
6. Only after the correct PIN is entered is `userId` placed in the authenticated session and the dashboard opened.

## Security
The App PIN is stored as a BCrypt hash in `users.app_pin_hash`; the raw PIN is not stored.
The App PIN is separate from the demo UPI payment PIN.

## Database
Hibernate is configured with `hibernate.hbm2ddl.auto=update`, so the `app_pin_hash` column is added to an existing `users` table automatically. The reference `database/schema.sql` also contains the column.

## MySQL
`application.properties` uses database `smart_bank_db`. Replace `spring.datasource.password` with your local MySQL password. The JDBC URL includes `allowPublicKeyRetrieval=true` for MySQL 8 authentication.

## Running in Eclipse
Run `SmartBankApplication.java` as a Java Application. Do not deploy the project to an external Eclipse Tomcat server. Because the application uses JSP, it remains WAR-packaged while using embedded Tomcat when launched from the main class.
