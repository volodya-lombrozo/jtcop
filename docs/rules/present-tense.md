# Present tense

The test method name should be a sentence that describes the test case using
present tense without subject. For example, if you have a test that tests
a `User` class, then the test method name should start from the verb followed by
any testing conditions. For example:

```java
public class UserTest {
    @Test
    public void createsUser() {
        // correct
    }

    @Test
    public void createsUserWithoutName() {
        // correct
    }

    @Test
    public void removesUser() {
        // correct
    }
}
```

The next cases will be considered as invalid:

```java
public class UserTest {
    @Test
    public void createUser() {
        // invalid!
    }

    @Test
    public void userIsCreated() {
        // invalid!
    }

    @Test
    public void userIsRemoved() {
        // invalid!
    }
}
```