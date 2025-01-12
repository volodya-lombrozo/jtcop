**# All Tests Have Production Class

Rule codename: _RuleAllTestsHaveProductionClass_
___
The test class should have the same name as the production class, with the
suffix `Test`. For instance, if there is a class named `User`, then there should
be a test class named `UserTest`.

* Correct: `org.example.User` and `org.example.UserTest`
* Wrong: `org.example.User` and `org.example.SomeTest`

The name of the test class serves as an instruction to guide programmers to look
into the corresponding source file by removing the `Test` suffix from the class
name. If this instruction does not provide the necessary information,
frustration can ensue, particularly if the project is not one's own. Ultimately,
the name of the test class is the last hope for obtaining this information.

### Exceptions

Annotations, Interfaces,
`@SuppressedWarnings("JTCOP.RuleAllTestsHaveProductionClass")`.

### Rationale

You can read more about that
rule [here](https://www.yegor256.com/2023/01/19/layout-of-tests.html#test-classes).**