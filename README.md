# test-naming-conventions

Almost each project uses unit tests in codebase. It is important to have 
a common pattern of naming all that tests, because otherwise the project and
tests itself become a complete mess. It's a quite large discussion about
different [test naming patterns](https://stackoverflow.com/questions/155436/unit-test-naming-best-practices).
**test-naming-conventions** maven plugin helps to keep follow a single 
common test naming rule across all of your test classes.
## How to use
In order to use the plugin with the latest version just invoke the next 
command in the root of your project: 
```shell
mvn com.github.volodya-lombrozo:test-naming-conventions:check
```
If you want to use specific version, for example `0.1.0`, just run the next
command
```shell
mvn com.github.volodya-lombrozo:test-naming-conventions:0.1.0:check
```