# The 'skip' Integration Test

This test verifies that the `skip` feature works as expected.
To skip the plugin execution, the `skip` property must be set to `true` in
the configuration:

```xml

<configuration>
  <skip>true</skip>
</configuration>
  ```

To run the test, execute the following command:

```bash
mvn clean integration-test -Dinvoker.test=skip -DskipTests
```