# jtcop

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.volodya-lombrozo/jtcop-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.volodya-lombrozo/jtcop-maven-plugin)

Almost each project uses unit tests in its codebase. It is important to have a
common pattern of naming all that tests, because otherwise the project and tests
itself become a complete mess. It's a quite large discussion about
different [test naming patterns](https://stackoverflow.com/questions/155436/unit-test-naming-best-practices)
.
**jtcop** maven plugin helps to keep following a single common
test naming rule across all of your test classes.

## Conventions

The only one convention that is supported by this plugin for now is the
**present tense without subject**.

### Present tense without subject

The test method name should be a sentence that describes the test case using
present tense without subject. For example, if you have a test that tests
a `User` class, then the test method name should start from the verb followed by
any testing conditions. For example:

```java
public class UserTest {
    @Test
    public void createsUser() {
        // correct
    }

    @Test
    public void createsUserWithoutName() {
        // correct
    }

    @Test
    public void removesUser() {
        // correct
    }
}
```

The next cases will be considered as invalid:

```java
public class UserTest {
    @Test
    public void createUser() {
        // invalid!
    }

    @Test
    public void userIsCreated() {
        // invalid!
    }

    @Test
    public void userIsRemoved() {
        // invalid!
    }
}
```

## How to use

The plugin could be run using several approaches but for both of them you need
at least **Maven 3.1.**+ and **Java 8+**.

### Invoke the plugin directly

In order to use the plugin with the latest version just invoke the next command
in the root of your project:

```shell
mvn com.github.volodya-lombrozo:jtcop-maven-plugin:check
```

After that you will see the result of the plugin execution in the console. If
you want to use specific (older) version of the plugin, for example `0.1.9`,
just run the next maven command with specified version:

```shell
mvn com.github.volodya-lombrozo:jtcop-maven-plugin:0.1.9:check
```

### Add the plugin to your `pom.xml`

The more convenient way to use the plugin is to add it to your `pom.xml` file.
In order to do that, just add the next snippet to your `pom.xml`:

```xml

<build>
  <plugins>
    <plugin>
      <groupId>com.github.volodya-lombrozo</groupId>
      <artifactId>jtcop-maven-plugin</artifactId>
      <version>0.1.9</version>
      <executions>
        <execution>
          <goals>
            <goal>check</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

The default plugin phase is `verify`, so you don't need to specify it directly,
but if you want to change it, just add the `phase` to `execution` section:

```xml

<execution>
  <phase>test</phase>
  <goals>
    <goal>check</goal>
  </goals>
</execution>
```

## How to Contribute

Fork repository, make changes, send us a pull request. We will review your
changes and apply them to the `main` branch shortly, provided they don't violate
our quality standards. 