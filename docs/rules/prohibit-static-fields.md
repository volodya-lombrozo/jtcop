# Prohibit Static Fields in Test Classes

Rule codename: _RuleProhibitStaticFields_
___
This rule ensures that test classes do not contain static fields. Static fields
in test classes can lead to shared state between tests, which can cause tests to
be interdependent and lead to flaky tests. Instead, constants should be used
directly within test methods.

### Correct Example

```java
class MyTest {
    @Test
    void testMethod() {
        final int constantValue = 5;
        // Use constantValue in the test
    }
}
```

### Incorrect Example

```java
class MyTest {
    private static final int SHARED_VALUE = 5;

    @Test
    void testMethod() {
        // Use SHARED_VALUE in the test
    }
}
```

### Rationale

Using static fields in test classes can introduce hidden dependencies between
tests, making them harder to maintain and understand. By avoiding static fields,
each test remains independent and easier to reason about.

- https://stackoverflow.com/a/442962/187141
- https://www.yegor256.com/2016/05/03/test-methods-must-share-nothing.html

### Suppressing the Rule

In order to suppress the rule, please use the next annotation:
`@SuppressedWarnings("JTCOP.RuleProhibitStaticFields")`.