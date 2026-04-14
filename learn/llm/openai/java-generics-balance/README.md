# Java Generic & Reusable Balance Management Module

This module is a focused learning project that demonstrates how to build **generic, reusable Java code** with:

- Design patterns (Strategy, Factory Method, Decorator, Repository, Command/Event style)
- Java Streams and functional programming
- Effective Java practices (immutability, records, Optional, generics, defensive copies)
- Spring Boot REST APIs for a balance management example

## What “generic/reusable” code means here

In this project, reusable code means:

- **Domain-first modeling** with immutable value objects (`Money`, `Transaction`)
- **Behavior via interfaces** (`OverdraftPolicy`, `GenericRepository<T, ID>`)
- **Composable logic** in stream pipelines (`TransactionFilter`)
- **Swappable implementations** (different overdraft strategies, in-memory repository)
- **Clear boundaries** between controller, service, domain, and repository layers

## Patterns used and why they matter

- **Strategy**: `OverdraftPolicy` allows changing overdraft rules without changing service/domain behavior.
- **Factory Method**: `Money.of(...)`, `Transaction.credit(...)`, and `Transaction.debit(...)` centralize validation and creation.
- **Decorator**: `LoggingAccountService` wraps service behavior and adds logging without changing core logic.
- **Repository**: `GenericRepository<T, ID>` decouples business logic from storage details.
- **Value Object**: `Money` and `Transaction` are immutable and self-validating.
- **Command/Event style**: each `Transaction` acts like an immutable applied event in account history.

## Run the Spring Boot application

From this module directory:

```bash
mvn spring-boot:run
```

Base URL: `http://localhost:8080`

Example requests:

```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountId":"A-100","owner":"Alice","initialAmount":1000,"currencyCode":"USD"}'

curl -X POST http://localhost:8080/api/accounts/A-100/credit \
  -H "Content-Type: application/json" \
  -d '{"amount":250,"currencyCode":"USD","description":"Salary"}'

curl http://localhost:8080/api/accounts/A-100/summary
```

## Source file summary

- `BalanceApplication.java`: Spring Boot entry point.
- `config/BalanceConfig.java`: wiring for `OverdraftPolicy` and `GenericRepository<Account, String>`.
- `controller/AccountController.java`: REST endpoints for account operations.
- `controller/dto/CreateAccountRequest.java`: DTO for account creation.
- `controller/dto/TransactionRequest.java`: DTO for credit/debit calls.
- `decorator/LoggingAccountService.java`: Decorator that logs operations and delegates.
- `domain/Money.java`: immutable money value object (`record`) with validation and arithmetic.
- `domain/Transaction.java`: immutable transaction event (`record`) with static factories.
- `domain/TransactionType.java`: transaction kind enum (`CREDIT`, `DEBIT`).
- `domain/Account.java`: immutable-style aggregate with defensive copies and transaction-based updates.
- `exception/AccountNotFoundException.java`: account lookup failure exception.
- `exception/InsufficientFundsException.java`: debit blocked due to overdraft policy.
- `exception/InvalidAmountException.java`: invalid monetary amount/currency input.
- `pipeline/TransactionFilter.java`: stream-based filtering, grouping, ranking, and sum utilities.
- `repository/GenericRepository.java`: reusable generic repository abstraction.
- `repository/InMemoryAccountRepository.java`: thread-safe in-memory account repository.
- `service/AccountService.java`: core business logic with repository + overdraft strategy.
- `service/AccountSummary.java`: immutable summary projection record.
- `strategy/OverdraftPolicy.java`: strategy interface.
- `strategy/NoOverdraftPolicy.java`: strict no-overdraft strategy.
- `strategy/LimitedOverdraftPolicy.java`: configurable overdraft strategy.
