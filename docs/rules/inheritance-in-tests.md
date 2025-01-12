# Inheritance in Tests

Rule codename: _RuleInheritanceInTests_
___
The test class should not extend any other class. Test inheritance can lead to
increased complexity, making tests harder to understand and maintain. Each test
class should be self-contained and focused solely on testing the functionality
of the production code it targets.

* Correct: A test class without a parent:
  ```java
  public class UserTest {
      // test methods
  }
  ```
* Wrong: A test class that extends another class:
  ```java
    public class UserTest extends BaseTest {
        // test methods
    }
  ```

Inheritance in tests can obscure the context of the test by introducing shared
state or behavior from parent classes, leading to subtle bugs and making it
harder to refactor tests in the future. Every test should clearly express its
intent without relying on external hierarchy.

### Exceptions

To ignore this rule, annotate the test class
with `@SuppressedWarnings("JTCOP.RuleInheritanceInTests")`.
