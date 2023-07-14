# Test Class Name

The test class name should start or end with one of the next prefixes:
- `Test`
- `Tests`
- `TestCase`
- `IT`
- `ITCase`

Examples of valid test class names:
```java
public class UserTest {
    // valid
}

public class UserTests {
    // valid
}

public class UserTestCase {
    // valid
}

public class UserIT {
    // valid
}

public class UserITCase {
    // valid
}

public class TestsUser {
    // valid
}

public class TestUser {
    // valid
}

public class TestCaseUser {
    // valid
}

public class ITUser {
    // valid
}

public class ITCaseUser {
    // valid
}
```

The next cases will be considered as invalid:

```java
public class User {
    // invalid!
}

public class UserTestCaseTest {
    // invalid!
}
```

You can read more about that rule [here](https://www.yegor256.com/2023/01/19/layout-of-tests.html#integration-tests).