# Camel Case

Each test name has to be written by using [camel case](https://en.wikipedia.org/wiki/Camel_case).

Correct:
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
Incorrect:
```java
public class UserTest {
    @Test
    public void create_user() {
        // invalid!
    }

    @Test
    public void user_is_created() {
        // invalid!
    }

    @Test
    public void user$is$removed() {
        // invalid!
    }
}
```