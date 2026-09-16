# Smart Bank Management System

A learning project built with the same stack and architecture style as your
Complaint Tracker: **Spring MVC + Spring ORM (Hibernate) + JSP/JSTL + MySQL**,
using **DAO → Service → Controller** layering.

## What's implemented

**Customer**
- Register & login (session-based; email-OTP is stubbed — see note below)
- Dashboard: balance, recent transactions, loan status
- Fund transfer by account number or UPI ID (double-entry transaction records)
- Mini statement (last 10 transactions)
- Loan application

**Admin**
- Approve / reject loan requests
- View & manage all customers
- Basic reports (loan totals, counts by status)

## Project structure

```
src/main/java/com/smartbank/
  entity/      → User, Account, Transaction, Loan  (JPA/Hibernate entities)
  dao/         → one interface + Hibernate-Session-based impl per entity
  service/     → business logic + @Transactional boundaries
  controller/  → @Controller classes, one per feature area
  util/        → PasswordUtil (BCrypt), AccountNumberGenerator, BankException

src/main/webapp/
  WEB-INF/
    web.xml               → DispatcherServlet + root context config
    spring-servlet.xml     → MVC config (controllers, view resolver)
    applicationContext.xml → DataSource, SessionFactory, TransactionManager
    views/*.jsp            → one JSP per screen
  resources/css/style.css

database/schema.sql  → reference only; Hibernate auto-creates tables
```

This mirrors your Complaint Tracker's **MVC + DAO** pattern, just swapping
Servlets for Spring MVC `@Controller`s and adding Spring's declarative
`@Transactional` instead of manual transaction handling.

## Setup in Eclipse

1. **Import** → File → Import → Maven → Existing Maven Projects → select this folder.
2. Eclipse will download dependencies via Maven (first build may take a minute).
3. **MySQL**: make sure MySQL is running locally. Update
   `src/main/resources/db.properties` with your username/password
   (the database `smart_bank_db` is created automatically if it doesn't exist).
4. **JRE**: in Eclipse, make sure the project's JRE is set to Java 17
   (right-click project → Properties → Java Build Path → Libraries, and
   also Properties → Java Compiler → set to 17).
5. **Server**: add a **Tomcat 9** server in Eclipse (Window → Preferences →
   Server → Runtime Environments → Add), and point it at a Java 17 JDK.
   Tomcat 9 still uses the `javax.servlet` namespace, which is what
   Spring 5.3.39 (used here) expects — Tomcat 10+ switched to
   `jakarta.servlet`, which only matters if you later upgrade to Spring 6.
   Tomcat 9 runs perfectly well on a Java 17 JVM, so this combination works
   fine.
6. Right-click the project → Run As → Run on Server → select your Tomcat 9.
7. Visit `http://localhost:8080/smart-bank/register` to create your first account.

## Creating an admin user

There's no separate admin signup screen (by design — in a real bank, admin
accounts wouldn't be self-service). Register normally, then promote yourself
in MySQL:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your_email@example.com';
```

Log out and back in — you'll land on `/admin/dashboard` instead of `/dashboard`.

## Known simplifications (and what to try next)

These were left simple on purpose so the core flow stays readable. Good next
steps, roughly in order of difficulty:

1. **Email OTP** — login currently trusts email+password alone. Wire up
   `JavaMailSender` to send a real OTP and add a verification step before
   the session is created.
2. **Concurrency on transfers** — `AccountServiceImpl.transfer()` doesn't
   lock rows, so two simultaneous transfers from the same account could
   theoretically race. Look into `LockMode.PESSIMISTIC_WRITE` on
   `session.get()`.
3. **Enums instead of Strings** — `role`, `status`, and transaction `type`
   are plain Strings for simplicity. Try converting them to Java enums with
   `@Enumerated(EnumType.STRING)`.
4. **Global exception handling** — errors are handled per-controller right
   now. Try a `@ControllerAdvice` class with `@ExceptionHandler(BankException.class)`
   for a single, centralized error page.
5. **Real reports** — `/admin/reports` aggregates in Java with streams over
   a small dataset. At real scale you'd write an HQL/native query with
   `GROUP BY MONTH(timestamp)` directly in `TransactionDao`.
6. **Loan disbursement** — approving a loan doesn't currently credit the
   customer's balance. Wire `AccountService` into `LoanServiceImpl.decideLoan()`
   to do that as a follow-up transaction.
7. **Auth as an interceptor** — the `isAdmin(session)` check is repeated in
   every `AdminController` method. Replace it with a `HandlerInterceptor`
   registered in `spring-servlet.xml`.

## Tech stack

- Java 17
- Spring MVC + Spring ORM 5.3.39 (still on the javax.* namespace, but runs fine on Java 17)
- Hibernate 5.6 (JPA annotations)
- MySQL 8 + HikariCP connection pool
- JSP/JSTL views
- BCrypt (jBCrypt) for password hashing
- Maven, Tomcat 9


## Portfolio upgrade added

The original project has now been extended without replacing the existing Spring MVC + DAO + Service architecture.

### Customer additions
- Modern responsive banking dashboard
- Deposit / withdrawal simulator
- Full recent transaction screen (up to 100)
- Beneficiary management
- Notifications with unread count and mark-all-read
- Savings goals with progress tracking
- Demo debit-card screen with masked card number
- EMI calculator
- UPI QR-code generation for the user's demo UPI ID
- Improved responsive navigation and banking-style UI
- Transfer notifications to sender and receiver
- Loan approval now disburses the approved amount into the customer's demo account and creates a transaction

### Admin additions
- Transaction monitoring page for the latest 100 transactions
- Existing customer, loan and report dashboards retained

### Important demo limitations
This remains a **portfolio banking simulator**, not a real banking/payment product. Deposit/withdrawal and UPI payments modify the application's demo MySQL data only. The QR code identifies the demo UPI ID; it does not connect to NPCI, a bank, or a real payment network. Card details are masked/demo-only.

Email OTP is still intentionally not connected to a real SMTP provider. If you want production-like OTP later, configure an email provider and add an OTP table/service rather than storing OTPs in the session.

### Next engineering upgrades
1. Spring Security instead of manual session checks.
2. Real email OTP with expiring, hashed OTP records.
3. Pessimistic row locking for concurrent transfers.
4. `@ControllerAdvice` for centralized exception handling.
5. Audit-log table for sensitive actions.
6. Pagination/filtering for large transaction history.
7. Proper database migrations (Flyway/Liquibase) instead of `hbm2ddl.auto=update`.
8. Automated unit/integration tests.


## SmartBank UPI Scan & Pay (Demo)

- Dashboard generates a SmartBank UPI QR using ZXing.
- `/upi/scan` opens a camera-based QR scanner with a manual UPI-ID fallback.
- Scanning a SmartBank QR opens `/upi/pay` with receiver details.
- The payment form validates the demo UPI PIN (`1234`) and uses the existing transactional transfer service.
- A successful payment debits the sender, credits the receiver, creates both transaction rows, and sends notifications.
- This is a portfolio simulator: it does not connect to real UPI rails or move real money.
