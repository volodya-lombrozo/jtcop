# Parallel Execution

This integration test is used to test the parallel execution of the 
`jtcop-maven-plugin`. 

To run the test, execute the following command:

```bash
mvn clean integration-test -Dinvoker.test=parallel -DskipTests
```