---
title: RuleOnlyTestMethods
id: only-test-methods
layout: default
---

# Only test methods

Rule codename: _RuleOnlyTestMethods_

Each test class has to contain **only** test methods. That is all.
The _RuleOnlyTestMethods_ rule is rather strict, hence it is considered
an experimental feature. In order to enable the _RuleOnlyTestMethods_ rule,
please enable experimental features in your `pom.xml` file by switching the
flag:

```xml
<configuration>
  <experimental>true</experimental>
</configuration>
```
After that, the rule will take effect.

Wrong usage:

```java
@Test
void checksSomethingImportant(){
    //...
}

void anotherMethodWhichIsNotATest{
    //...    
}
```

Correct usage:

```java
@Test
void checksSomethingImportant(){
    //...
}

@Test
void allTheMethodsAreTests(){
    //...    
}
```

In order to suppress the rule, please use the next annotation:
`@SuppressedWarnings("JTCOP.RuleOnlyTestMethods")`.
