# Test Name: Don't Use Special Characters

Rule codename: _RuleNotUsesSpecialCharacters_
___

Each test name should not contain special characters like `$` or `_`.

Wrong:
```java
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
