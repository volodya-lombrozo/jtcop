# All Incorrect Tests

This test checks that `jtcop` might find incorrect tests and prints correct
human-readable messages.

To run this test, execute the following command:

```bash
mvn clean integration-test -Dinvoker.test=all-incorrect -DskipTests
```