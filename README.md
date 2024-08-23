# jtcop

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.volodya-lombrozo/jtcop-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.volodya-lombrozo/jtcop-maven-plugin)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/volodya-lombrozo/jtcop/blob/main/LICENSE.txt)
[![Hits-of-Code](https://hitsofcode.com/github/volodya-lombrozo/jtcop?branch=main&label=Hits-of-Code)](https://hitsofcode.com/github/volodya-lombrozo/jtcop/view?branch=main&label=Hits-of-Code)
![Lines of code](https://img.shields.io/tokei/lines/github/volodya-lombrozo/jtcop?branch=main&label=Lines-of-Code)
[![codecov](https://codecov.io/gh/volodya-lombrozo/jtcop/branch/main/graph/badge.svg)](https://codecov.io/gh/volodya-lombrozo/jtcop)

This repository was inspired by various articles and discussion threads (such as
these
ones: ["Unit test naming best practices"](https://stackoverflow.com/questions/155436/unit-test-naming-best-practices)
and ["On The Layout of Tests"](https://www.yegor256.com/2023/01/19/layout-of-tests.html)),
and it consolidates knowledge on
best practices for organizing and naming tests. The purpose of jtcop is to
enhance the clarity and maintainability of your tests. In other words, jtcop is
a static linter similar to tools
like [CheckStyle](https://checkstyle.sourceforge.io)
or [PMD](https://pmd.github.io), but with a focus on test best practices.

You can read more about checks and rules in the [docs](docs/README.md).

## How to use

The plugin could be run using several approaches but for both of them you need
at least **Maven 3.1.**+ and **Java 8+**.

### Invoke the plugin directly

In order to use the plugin with the latest version just invoke the next command
in the root of your project:

```shell
mvn com.github.volodya-lombrozo:jtcop-maven-plugin:check
```

or short version:

```shell
mvn jtcop:check
```

After that you will see the result of the plugin execution in the console. If
you want to use specific (older) version of the plugin, for example `0.1.16`,
just run the next maven command with specified version:

```shell
mvn com.github.volodya-lombrozo:jtcop-maven-plugin:0.1.16:check
```

or snapshot version:

```shell
mvn com.github.volodya-lombrozo:jtcop-maven-plugin:1.0-SNAPSHOT:check
```

### Add the plugin to your `pom.xml`

The more convenient way to use the plugin is to add it to `pom.xml` file.
In order to do that, just add the next snippet to the `<plugins>` section:

```xml

<plugin>
  <groupId>com.github.volodya-lombrozo</groupId>
  <artifactId>jtcop-maven-plugin</artifactId>
  <version>1.3.1</version>
  <executions>
    <execution>
      <goals>
        <goal>check</goal>
      </goals>
    </execution>
  </executions>
</plugin>
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

## Skip the plugin execution

If you want to skip the plugin execution, just set the `skip` property to `true`
in the configuration:

```xml
<configuration>
  <skip>true</skip>
</configuration>
```

## How to Contribute

Fork repository, make changes, send us a pull request. We will review your
changes and apply them to the `main` branch shortly, provided they don't violate
our quality standards. 