# Test Name: Not Uses Special Characters

Rule codename: _RuleNotUsesSpecialCharacters_
___

Each test name should not contain special characters like `$` or `_`.

Wrong:
```java
@Test
@Test
void checks_successfully() {
        //...
}

@Test
void checks$unsuccessfully(){
        //...
}
```

Correct:
```java
@Test
void checksSuccessfully() {
 //...
}

@Test
void checksUnsuccessfully(){
    //...
}
```

In order to suppress this rule, you can use the following annotation
`@SuppressedWarnings("JTCOP.RuleNotUsesSpecialCharacters")`.

You can read more about that
rule [here](https://www.yegor256.com/2023/01/19/layout-of-tests.html#assertions).