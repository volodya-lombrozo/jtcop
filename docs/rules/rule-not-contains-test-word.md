# Avoid 'test' Word in Test Names

Rule codename: _RuleNotContainsTestWord_
___

Test names should not contain the word 'test'. This rule ensures that test names
are descriptive and do not rely on the generic term 'test', which can be
redundant and uninformative.

Correct:

```java
@Test
void calculatesSumCorrectly(){
    // Test implementation
}
```

Incorrect:

```java
@Test
void testCalculatesSum(){
    // Test implementation
}
```

The rule checks for the presence of the word 'test' in any case (e.g., 'test', '
Test', 'TEST') within the test name.

Exceptions:
`@SuppressedWarnings("JTCOP.RuleNotContainsTestWord")`.

For more information on naming conventions, refer
to [this guide](https://www.yegor256.com/2023/01/19/layout-of-tests.html#naming).
