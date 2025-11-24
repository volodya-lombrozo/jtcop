---
title: LineHitterRule
id: line-hitter
layout: default
---

# Line hitter
At first glance, the tests cover everything and code coverage tools confirm it with 100%, but in reality the tests merely hit the code, without doing any output analysis.

## Example
Code to test

```java
int sum(a, b) {
    return a + b;
}
```

### Bad

```java
@Test
void someTest() {
    int a = sum(1, 3);
    assertThat("msg", true, equalTo(true));
    assertThat("msg", false, equalTo(false));
    assertThat("msg", true || false, equalTo(true));
}
```

### Good

```java
@Test
void someTest() {
    int actual = sum(1, 3);
    assertThat("1 plus 3 is 4", actual, equalTo(4));
}
```

### Ignoring the Rule

To ignore this rule, annotate a method or class with `@SuppressWarnings("JTCOP.LineHitterRule")`.

