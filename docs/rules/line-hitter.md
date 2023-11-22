# Line hitter
At first glance, the tests cover everything and code coverage tools confirm it with 100%, but in reality the tests merely hit the code, without doing any output analysis.

## Example
```java
    @Test
    void someTest() {
        // some code have to be covered
        assertThat("msg", true, equalTo(true));
        assertThat("msg", false, equalTo(false));
        assertThat("msg", true || false, equalTo(true));
    }
```
