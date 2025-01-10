# Correct Test Cases

This integration test contains a set of tests that should pass.
Use this test to verify that the integration is working as expected.
Usually, this test is used to check new features or bug fixes.

If you want to run this test, you can use the following command:

```bash
mvn clean integration-test -Dinvoker.test=all-correct -DskipTests
```