# Assertions Integration Tests

This module contains integration tests for assertions.
It tests different assertions from JUnit and Hamcrest libraries along with
assertion messages.

## How to run

To run only these tests, execute the following command:

```bash
mvn clean integration-test invoker:run -Dinvoker.test=assertions -DskipTests
```
