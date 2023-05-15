# All Tests Have Production Class

Rule codename: _RuleAllTestsHaveProductionClass_
___
Each test class has to have corresponding production class.
Test class name has to be the same as production class name with `Test` suffix.
For example, if there is a class `User` then there has to be a test
class `UserTest`.

* Correct: `org.example.User` and `org.example.UserTest`
* Wrong: `org.example.User` and `org.example.SomeTest`

The name of the test class serves as an instruction to guide programmers to look
into the corresponding source file by removing the `Test` suffix from the class
name. If this instruction does not provide the necessary information,
frustration can ensue, particularly if the project is not one's own. Ultimately,
the name of the test class is the last hope for obtaining this information.

You can read more about that
rule [here](https://www.yegor256.com/2023/01/19/layout-of-tests.html#test-classes).