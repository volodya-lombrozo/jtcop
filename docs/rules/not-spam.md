# Test Name: Do Not Repeat Characters

Rule codename: _RuleNotSpam_
___

Each test name should be meaningful without any repeating characters.

Wrong:
```java
@Test
void checksSss() {
    //...
}

@Test
void checksUnsuccessfullyYeaaa(){
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
`@SuppressedWarnings("JTCOP.RuleNotSpam")`.