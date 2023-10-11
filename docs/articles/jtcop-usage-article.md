# jtcop

## Introduction

Over the years, numerous articles have been written about the importance of
unit and integration tests and the benefits they provide. Among these benefits
are the ability
to swiftly and
precisely [identify errors](https://dzone.com/articles/importance-of-unit-testing),
simplify debugging, and detect mistakes early.
Furthermore, effective testing can lead to significant savings in
development [costs](https://dzone.com/articles/what-is-test-driven-development-and-why-its-import)
and ensure
a [strong alignment](https://dzone.com/articles/software-unit-testing-what-is-that-why-is-it-impor)
between the specification and the final product.
Unit tests
facilitate [safe refactoring](https://dzone.com/articles/java-unit-testing-best-practices-how-to-get-the-mo)
and can
prove [invaluable during the code review](https://www.yegor256.com/2019/12/03/testing-in-code-review.html)
process.
Thus, testing is sometimes even considered
a [central part](https://dzone.com/articles/importance-of-testing-grant-fritchey)
of the development process.

However, in recent years, within the developer community, it has become evident
that the mere presence of unit and integration tests in a project is not
sufficient. There has been an increasing number of blog posts and articles
discussing the importance of well-structured and formatted tests.
So, why is this so important?

## Background

Well, every time I delve into a new project, I'm presented with an entirely new,
distinct landscape. I start by reading the documentation and quickly delve into
the project's tests. First and foremost, tests much better even a good
documentation, since they are usually up-to-date, in contrast to the
documentation, which can sometimes be misleading. By examining integration
tests, I can easily determine how to use the project application or tool. By
reviewing unit tests, I can readily understand the internal structure of the
project, how it functions, and how certain classes, functions or units operate.

Moreover, when I want or need to contribute to a project, the first question I
ask is, "Where should I add my changes?" And yes, I will look for the answer in
the tests, as they indicate which unit is most appropriate. If I need to fix a
bug, my first step will be to reproduce it. How? Yes, you are right - by writing 
a test. Thus, project tests guide me through all the actions I need to undertake 
with the project.

Now, let's imagine that the project's tests are chaotic or missing entirely. It
would be extremely difficult to understand the code, determine where to add my
changes, and ensure that my modifications won't break anything.

So, yes, the quality of tests is paramount. If tests are poorly formatted or
even exhibit some anti-patterns, it becomes challenging to work with the
project. And of course, this isn't my original thought. There are numerous
articles that emphasize the importance of well-structured and formatted tests,
offering best practices and insights.

### Best Practices

#### Best Practices (OLD)

https://dzone.com/articles/importance-of-unit-testing
Best practices suggested for unit testing include using consistent naming
conventions, providing a corresponding unit test case for each module, and
testing as code is committed.

https://dzone.com/articles/java-unit-testing-best-practices-how-to-get-the-mo
These include ensuring that tests are trustworthy (failing only when
necessary), maintainable, readable, and dedicated to verifying a single use
case. Tests should also be isolated and automated.

The
article [Importance of Unit Testing](https://dzone.com/articles/importance-of-unit-testing)
also highlights the importance of unit testing: it ensures security, customer
satisfaction, saves money (sometimes even lives) in the long run, reduces the
time required to find and fix bugs, and results in more reliable code.

The article suggests several best practices for unit testing, including using
consistent naming conventions, providing a corresponding unit test case for
every module, and testing as you commit code to avoid errors.

The article
[Java Unit Testing Best Practices](https://dzone.com/articles/java-unit-testing-best-practices-how-to-get-the-mo)
highlights the importance of unit testing, including the ability to identify
defects early in the development process, the ability to refactor safely, and
even help in the code review process.
Most importantly, the article offers numerous best practices for writing unit
tests. These include ensuring that tests are trustworthy (failing only when
necessary), maintainable, readable, and dedicated to verifying a single use
case. Tests should also be isolated and automated.

For instance, the article [Anatomy of a Good Java
Test](https://dzone.com/articles/anatomy-of-a-good-java-test) emphasizes the
significance of test naming, especially from a documentation standpoint. While
recommendations like adhering to consistent naming conventions
and avoiding the use of the word "test" in test names may seem obvious, they
are not always followed in practice. Furthermore, the article underscores the
value of organizing tests into three distinct
sections: [Setup, Action, Assertion](https://stackoverflow.com/tags/arrange-act-assert/info).
It also advises against using cryptic assertion messages and emphasizes the
importance of clarity and expressiveness in test writing.

The
article [7 Tips for Writing Better Unit Tests in Java](https://dzone.com/articles/7-tips-for-writing-better-unit-tests-in-java)
also offers a number of tips for writing better unit tests. These include using
a framework for unit testing, using test-driven development judiciously,
measuring code coverage, using assertions instead of print statements, and
building tests that have deterministic results. The article also emphasizes the
importance of testing negative scenarios and borderline cases, in addition to
positive scenarios.

The article [Clean Unit Testing](https://dzone.com/articles/clean-unit-testing)
also serves as an invaluable resource for those seeking to enhance their unit
tests. It introduces several best practices such as the appropriate use
of [mocks and stubs](https://stackoverflow.com/questions/3459287/whats-the-difference-between-a-mock-stub),
effective naming and ordering of variables, and the paramount importance of test
readability. The article underscores that each test should focus on a single
case, employing a limited and well-ordered set of assertions. It also warns
against including "if" statements in testing blocks, among many other insightful
recommendations.

#### Anti-Patterns and Layout of Tests

Additionally,
certain [threads](https://stackoverflow.com/questions/333682/unit-testing-anti-patterns-catalogue)
and [articles](https://dzone.com/articles/unit-testing-anti-patterns-full-list)
identify anti-patterns one might encounter in real projects.
These should be avoided as much as possible.

And another
intriguing [approach]((https://www.yegor256.com/2023/01/19/layout-of-tests.html))
that has emerged recently emphasizes the importance of maintaining a
structured "layout" for your unit and integration tests. This isn't merely about
naming test methods; it's about organizing them systematically within the source
code.

#### Conclusion and transition

Despite the undeniable impact and evidence highlighting the importance
of unit testing, achieving high-quality code in tests remains elusive. Many
developers
either neglect to write tests, don't cover most of the cases, or
fail to maintain them. Is there a solution to this problem?

## Gap Identification

As you might have noticed from the articles, there are some common ideas and
principles we can apply to create good unit tests. But what about the tools that
already help us keep our code clear and consistent? I'm referring to static
analyzers. Let's examine the most widely used ones. Please note that here I will
only briefly describe a few of them, even though there are plenty of similar
tools available. This is a vast topic on its own. Ok, let's just review them.

#### Checkstyle

[Checkstyle](https://checkstyle.sourceforge.io) - is one more development tool
that helps programmers write Java code that adheres to a coding standard. In
other words, Checkstyle is a static code analysis tool (linter) used in Java
world. Although Checkstyle doesn't provide features specifically tailored for
unit tests, many of its features are applicable to test code just as they are
to production code.

The only useful feature to the best of my knowledge is the ability to enforce
the test names convention by developing
your [own checker](https://stackoverflow.com/questions/24415234/configure-checkstyle-method-names-for-test-methods-only).
Well, yes, before using it you need to develop your own checker, but it is
possible.

Also, checkstyle can help you with javadoc comments, indentation, line length,
cyclomatic complexity, etc. So, it is a tool just for checking common rules. It
doesn't consider specific rules for unit tests, like test names, assertions,
and layout of tests.

However, as I mentioned before, the Checkstyle is a general tool that
concentrated on java code only and doesn't check high level problems with tests,
for example where they are placed. Also Checkstyle doesn't check the test
classes names and doesn't check if the test class has a corresponding production
class. So, to conclude, Checkstyle is a good tool, but it doesn't help us to
keep our tests consistent and clear according to industry requirements and best
practices.

#### PMD

[PMD](https://pmd.github.io) - a source code analyzer. It finds common
programming flaws like unused variables, empty catch blocks, unnecessary
object creation, and so forth. While it supports many different languages,
here we are interested in Java only. Let's try to find how PMD can help us to
keep our tests consistent and clear according to industry requirements and
best practices.

PMD, comparing with Checkstyle has much more rules that checks tests quality,
for example:

1. [JUnitAssertionsShouldIncludeMessage](https://pmd.github.io/pmd/pmd_rules_java_bestpractices.html#junitassertionsshouldincludemessage) -
   requires that JUnit assertions should include a message.

```java
public class Foo extends TestCase {
    public void testSomething() {
        assertEquals("foo", "bar");
// Use the form:
// assertEquals("Foo does not equals bar", "foo", "bar");
// instead
    }
}
```

2. [JUnitTestContainsTooManyAsserts](https://pmd.github.io/pmd/pmd_rules_java_bestpractices.html#junittestcontainstoomanyasserts)
   checks that JUnit or TestNG test contains too many assertion statements:

```java
public class MyTestCase extends TestCase {
    // Ok
    public void testMyCaseWithOneAssert() {
        boolean myVar = false;
        assertFalse("should be false", myVar);
    }

    // Bad, too many asserts (assuming max=1)
    public void testMyCaseWithMoreAsserts() {
        boolean myVar = false;
        assertFalse("myVar should be false", myVar);
        assertEquals("should equals false", false, myVar);
    }
}
```

3. [JUnitTestsShouldIncludeAssert](https://pmd.github.io/pmd/pmd_rules_java_bestpractices.html#junittestsshouldincludeassert)
   checks that JUnit tests include at least one assertion
4. [TestClassWithoutTestCases](https://pmd.github.io/pmd/pmd_rules_java_errorprone.html#testclasswithouttestcases)
   checks that test classes have at least one test case (testing method)
5. [UnnecessaryBooleanAssertion](https://pmd.github.io/pmd/pmd_rules_java_errorprone.html#unnecessarybooleanassertion)

- checks that JUnit assertions are used correctly without `assertTrue(true)` (or
  similar) statements.

However, all this checks designed mostly for JUnit assertions and in some cases
for AssertJ and don't support Hamcrest assertions that is widely using in the
industry (including us, personally).

Also, when PMD can sheck method names, all this checks are designed for
production code only and doesn't check specific test name patterns.

Moreover, to best my knowledge, PMD doesn't check the layout of tests and where
they are placed. So, PMD has a rather limited number of checks for tests.

#### Sonar Qube

[SonarQube](https://www.sonarqube.org) - is a tool that also widely used to
check code quality. SonarQube is an open-source platform that has a support
of Java and other languages.

SonarQube has a lot of rules similar to PMD that can be applied to tests,
for example:

1. [TestCases should contain tests](https://rules.sonarsource.com/java/tag/tests/RSPEC-2187/) -
   checks that test classes have at
   least one test case (testing method).
2. [Literal boolean values and nulls should not be used in assertions.](https://rules.sonarsource.com/java/tag/tests/RSPEC-2699/)
3. [Assertions should not compare an object to itself.](https://rules.sonarsource.com/java/tag/tests/RSPEC-5863/)
4. [Test assertions should include messages.](https://rules.sonarsource.com/java/tag/tests/RSPEC-2698/)
5. [Test methods should not contain too many assertions.](https://rules.sonarsource.com/java/tag/tests/RSPEC-5961/)
6. [Similar tests should be grouped in a single Parameterized test.](https://rules.sonarsource.com/java/tag/tests/RSPEC-5976/)

The full list you can find
right [here](https://rules.sonarsource.com/java/tag/tests/RSPEC-2701/).
It's around total 45 rules for tests.

As you might notice, SonarQube has more rules than PMD, although most of them
overlap with PMD. Also, SonarQube, to best my knowledge, doesn't check
Hamcrest assertions at all and doesn't keep the layout of tests.

### Introduction to jtcop

So we decided to create a tool that will help us to keep our tests
more or less consistent, easy to read and orginized by similar principles.
We even created a tool which help us to keep our tests according with the rules
which we think are important for Java projects. I believe that the similar tool
might be created for other languages you like.

In this article I will try to explain how it fits into the landscape of static
analysis tools and how it can help you in our every-day-programming.

Of course, we aren't trying to say that our rules are the best and the only
way to organize tests, but we think that they are good enough to be used across
many projects and teams. And we hope that you will find them useful too.

* Clarity: Discuss the importance of immediately understanding what a test does
  and why.
* Maintainability: Describe the challenges of maintaining poorly organized or
  ambiguously named tests.

## Introducing jtcop

Please, pay attention that most of the ideas was suggested by the community and
we just collected them. So it is not out now-how So, the primary features of
jtcop for now are:

### Test Names

There are lots of test naming convetions that can be used in the industry:

* https://dzone.com/articles/7-popular-unit-test-naming
* https://stackoverflow.com/questions/155436/unit-test-naming-best-practices

When they use different ways to name tests, we prefer to use the following
rules to name tests:

1. We use present tense without a subject for test names. For example if you
   test a class `Animal` with method `eat()`, the test name should be `eats()`,
   if you need add more context, you do it after a verb: `eatsApplesOnly()`.
2. We use [camelCase](https://en.wikipedia.org/wiki/Camel_case) for test names.
3. We don't use the "test" word in test names, since it is redundant and it is
   already clear that it is a test (it is already obvious from the test class
   name and by using `@Test` annotation).
4. We also forbid to use some special characters like `_` nad `$` together with
   spaming letters like. So we don't allow `eatsSSS()`, `_eats` and similar.

| Correct Names    | Incorrect Names      |
|------------------|----------------------|
| eats()           | testEats()           |
| eatsApplesOnly() | TestEatsApplesOnly() |
| runsQuickly()    | _runsQuickly()       |
| jumpsOverFence() | jumps_over_fence()   |
| drinksWater()    | drinks$Water()       |
| sleepsAtNight()  | sleepsZZZ()          |
| chewsGum()       | test_chewsGum()      |
| listensToMusic() | listens_To_Music()   |
| walksInPark()    | WalksInPark()        |
| barksLoudly()    | barks__loudly()      |    

Of course, it is the style that we prefer in our projects, if you prefer some
other patter for tests naming, just sent an issue or PR to jtcop and we will
be happy to add it to the plugin.

### Test Assertions

The next important point that jcop checks assertions in tests. The most
important check is, of course, the presense of assertions in a test.
jtcop understands two assertion types `JUnit` assertions and `Hamcrest`
assertions.

For example the next test is incorrect:

```java
@Test
void calculatesSum(){
    if(sum(1,1)!=2){
    throw new RuntimeException("1 + 1 != 2");
    }
    }
```

where the next is correct:

```java
@Test
void calculatesSum(){
    Assertions.assertEquals(
    2,sum(1,1),"Something went wrong, because 1 + 1 != 2"
    );
    }
```

or with using Hamcrest assertions:

```java
@Test
void calculatesSum(){
    MatcherAssert.assertThat(
    "Something went wrong, because 1 + 1 != 2",
    sum(1,1),
    Matchers.equalTo(2)
    );
    }
```

Pay attention on explanatory messages in
assertions `Something went wrong, because 1 + 1 != 2`.
They are required too. For example, let's consider
the [real example](https://github.com/volodya-lombrozo/jtcop/blob/c742a5cad69d4e2ae4e895c0c7a4b42f9d0122e5/src/test/java/com/github/lombrozo/testnames/CopTest.java#L38)
(I simplified it a bit):

```java
@Test
void checksSuccessfully(){
    MatcherAssert.assertThat(
    new Cop(new Project.Fake()).inspection(),
    Matchers.empty()
    );
    }
```

And, let's imagine this test fails and you get the next exception message:

```shell
Expected: an empty collection
     but: <[com.github.lombrozo.testnames.Complaint$Text@548e6d58]>
```

Not informative at all. Isn't it? But if you add explanatory message to the
assertion:

```java
@Test
void checksSuccessfully(){
    MatcherAssert.assertThat(
    "Cop should not find any complaints in this case, but it has found something.",
    new Cop(new Project.Fake()).inspection(),
    Matchers.empty()
    );
    }
```

you will get the next message:

```shell
java.lang.AssertionError: Cop should not find any complaints in this case, but it has found something.
Expected: an empty collection
     but: <[com.github.lombrozo.testnames.Complaint$Text@548e6d58]>
```

It is much better, isn't it? Of course in the perfect world, we will add more
information (aka context, that explains initialisation values and gives a clue
to the developer) to the message, but even now it is much better than the
previous example, because it is clear
and human-readable.

This feature was extremly useful for us, because we don't need to ask the
developer to add explanatory messages to assertions, like we did it in numerous
PR reviews. Now we just run jtcop and it will do it for us.

### Line Hitters

First of all it is worth mentioning what
is [Line Hitter]((https://stackoverflow.com/a/10323328/10423604)) is.

> At first glance, the tests cover everything and code coverage tools confirm it
> with 100%, but in reality the tests merely hit the code, without doing any
> output analysis.

In other words, It is a test method that doesn't check anything. For example:

```java
@Test
void calculatesSum(){
    sum(1,1);
    }
```

This often happens when a developer writes in order to increase code coverage
and doesn't care about the quality of the test.

Some tools might find the absence of assertions in tests, and in this case
developers might cheat a bit to avoid the problem:

```java
@Test
void calculatesSum(){
    sum(1,1);
    Assertions.assertTrue(true,"I'm just hanging around");
    }
```

Well, it is the same "Line Hitter", but with assertion statement. So, jtcop
is able to find such tests and mark them as incorrect.

### Corresponding Production Class

As was
already [mentioned](https://www.yegor256.com/2023/01/19/layout-of-tests.html#test-classes),
the well-formatted methods of unit tests is not enough.
For example, you have a test class with the name `SumTest.java` with
the test method `checksSum()` which tests the method `sum()`. But occasionally,
the test fails. What will you do? You will open the test class to find a
problem.
But which class? I belive the first guess is `Sum.java`, isn't it?
But what if the class is named `Addition.java`? Or `Calculator.java`?
Sometimes it is not so easy to find the corresponding class, especially if you
have a lot of classes in your project. So, jtcop checks if the test class has
a corresponding production class. If it doesn't, it will mark the test as
incorrect. In other words the name of the class isn't just a name, it is a
pointer to the production class where to pinpoint the problem.

So, if you have a test class `SumTest.java` with the corresponding production
class `Addition.java`, it is extremely confusing. The proper way is to name
test class `AdditionTest.java`.

The only exception in this case is integration tests. They are usually named
like `AdditionIT.java` or `AdditionIntegrationTest.java`. But they have to be
placed in the separate package, like `it`  and have an appropriate suffix:
`ITCase` (`maven-compiler-plugin`).

### Test Methods Only

Sometimes when you write a test class with many test cases you might
require to configure common setup for all the tests in the class.
jtcop can guide you in this case too. But before, I would like to mention
this rule is quite strict and since we don't use it in all our projects
this feature is still in experimental stack, so it is disabled by default
and you need to enable it manually (further I will
explain [how to do this](#experimental).)
Usually we have several ways to do it. Let's delve a bit deeper into each of
them and compare.

#### Plain Old Static Methods

Often developers (and, to be honest me too) use static methods to configure
common setup for all the tests in the class. For example:

```java
@Test
void calculatesSum(){
    Summator s=init();
    Assertions.assertEquals(
    2,sum(1,1),"Something went wrong, because 1 + 1 != 2"
    );
    }

private static Summator init(){
    Summator s=new Summator();
    // some common setup
    return s;
    }
```

Well, from the first glance it looks like a good solution, but it has some
problems. Usually, when such a method is used in the single class, it is not a
big deal, despite the fact that it is a static method usually
create [low cohesion and tight
coupling](https://dzone.com/articles/static-classes-are-evil-or-make-your-dependencies).
But when you start using it in many classes or even create something like
a `TestUtils.java` to move all initialization in one place, it
becomes [a problem](
https://www.yegor256.com/2023/01/19/layout-of-tests.html#test-prerequisites-wrong-way
):

1. It confuses a developer, because `TestUtils` doesn’t have a counterpart in
   the live code block.
2. `TestUtils.java` might be considered as
   an [anti-pattern](https://stackoverflow.com/questions/3340032/are-utility-classes-evil)
   itself
   or at least requires lots of experience to use them properly.

So, jtcop considers that methods and utility classes as dangerous and doesn't
allow them and you will the next exception message for the code above:

```shell
All methods of the test class 'SumTest.java' should be annotated with @Test annotation.
```

#### Annotations @BeforeAll, @BeforeEach and @AfterAll, @AfterEach

annotations. (don't allow)

```java
Summator s;

@BeforeEach
void setUp(){
    s=new Summator();
    // some common setup
    }

@Test
void calculatesSum(){
    Summator s=init();
    Assertions.assertEquals(
    2,sum(1,1),"Something went wrong, because 1 + 1 != 2"
    );
    }
```

Well, this methods makes the situation event worse, because all the tests
in the class will share the same number of varibles and will have to
initiate them each time. Let's imagine a test class with 500 unit tests. I don't
think that all of these tests will require the same initialization. Moreover,
using `@BeforeAll` and `@AfterAll` exactely the same as using static methods.
And one more thing - the developer will have to "compile" the test case in his
head all the time by jumping beween the test method and the `@BeforeAll` method,
which is tedious sometimes.

So, jtcop doesn't allow to use these annotations.

#### Test Extensions

JUnit 5 has a feature
called [Test Extensions](https://junit.org/junit5/docs/current/user-guide/#extensions)
that allows to create a custom extension to configure common setup for all the
tests in the class. For example:

```java

@ExtendWith(SummatorExtension.class)
public class SumTest {

    @Test
    void calculatesSum(Summator s) {
        Assertions.assertEquals(
            2, s.sum(1, 1), "Something went wrong, because 1 + 1 != 2"
        );
    }
}

class SummatorExtension implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext pctx,
        ExtensionContext ectx
    ) {
        return pctx.getParameter().getType() == Summator.class;
    }

    @Override
    public Object resolveParameter(
        Summator s =new Summator(
    );
    // some common setup
        return s;
}
}
```

In this case we avoided the need to keep utility classes, static methods and
common state between tests. It is easy to reuse amond many test classes and
separate unit tests.

So, jtcop allows to use this approach.

#### Fake Objects

And the last approach allowed by jtcop is to use Fake objects.
They are placed together with other live objects,
but have special “fake” behavior, for example, let's imagine that `Summator`
class depends on some environmental conditions to provide summation and it might
differ in different situations, but for some reason we want to exclude
environmental conditions from the test. So, we can either mock it by using
some mocking framework or create a Fake object. For example:

```java
class Environment {
    double multiplier() {
        return 0.5; // real multiplier that we want to ignore.
    }

    static class Fake extends Environment {
        @Override
        double multiplier() {
            return 1;
        }
    }
}

public class SumTest {

    @Test
    void calculatesSum() {
        Summator s = new Summator(new Enivornement.Fake());
        Assertions.assertEquals(
            2, s.sum(1, 1), "Something went wrong, because 1 + 1 != 2"
        );
    }
}
```

In this case you can avoid using any annotations, utility classes, static
methods:
just use Fake objects. As for Fake objects aren't a part of the testing code,
jtcoo doesn't consider them as a problem. It is just a short introduction
into Fake objects, so you can read more about that approach right
[here](https://www.yegor256.com/2014/09/23/built-in-fake-objects.html).

## How jtcop Works

### Integration

To start using jtcop the only thing you need to do is to add the plugin
to your build configuration file, for example, for Maven it looks like as
follows

```xml

<build>
  <plugins>
    <plugin>
      <groupId>com.github.volodya-lombrozo</groupId>
      <artifactId>jtcop-maven-plugin</artifactId>
      <version>1.1.1</version>
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
but if you want to change it, just add the required `phase` to `execution`
section.

Also, you can run jtcop by using simple command:

```shell
mvn jtcop:check
```

Then, if you have some problem, for example, you test doesn't have corresponding
production class, you will receive human readable explanatory message:

```shell
[ERROR] Test SumTest doesn't have corresponding production class.
[ERROR]  Either rename or move the test class ./SumTest.java.
[ERROR]  You can also ignore the rule by adding @SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass") annotation.
[ERROR]  Rule: RuleAllTestsHaveProductionClass.
[ERROR]  You can read more about the rule here: <link>
```

Or in case of Line hitter pattern, as it was discussed erlier:

```shell
[ERROR] Method 'calculatesSum' contains line hitter anti-pattern.
[ERROR]  Write valuable assertion for this test.
[ERROR]  You can also ignore the rule by adding @SuppressWarnings("JTCOP.RuleLineHitter") annotation.
[ERROR]  Rule: RuleLineHitter.
[ERROR]  You can read more about the rule here: <link>
```

jtcop by defult will stop the further build if your tests have some problems,
if you require the same level of quality, if you need just to check
time-to-time, you can "tell" jtcop to print warning messages instead by
setting `failOnError` property:

```xml

<configuration>
  <failOnError>false</failOnError>
</configuration>
```

But I highly recommend to leave it as is which will allow you to keep your tests
with higher quality.

#### Experimental

As I've already mentioned some features are still in experimental stack and
if you want to use it, you might enable them by adding the next configuration
to your `pom.xml` file:

```xml

<configuration>
  <experimental>true</experimental>
</configuration>
```

Now, all experimental features will be applied to your project and you can
get advantages of even better formatted and structured tests.

## Benefits of Using jtcop

* Consistency: jtcop helps us keep the tests similar across many projects. So
  when you open a project that employs jtcop, it's much easier to understand
  what
  is going on with a project and which guides the project follows
* Code Review: the main problem that was solved by jtcop is constantly
  mentioned noisy messages like "put this class here", "use different name" for
  it, or "it an antipattern". So by using jtcop we don't spend much time and
  help the developer to solve all the problems even before pushing something to
  us.
* Improved Onboarding: One more effect which we got is that well-organized and
  named test suites not only helps understand and maintain the code, it actually
  reduce the time for explaining or writing down code style guides across
  projects
  many projects which simplified onboarding new programmers. We receive already
  well-formatterd pull requests new team members without additional help,

## Future Plans

### Roadmap

In the future we are going to add more rules to jtcop, for example we can check
some anti-patterns, listed in
that [thread](https://stackoverflow.com/questions/333682/unit-testing-anti-patterns-catalogue),
lile
"The Mockery" (when a unit test contains too many mocks), "Excessive Setup" (a
test that requires a huge setup),
"Wait and See" (a tests that needs to 'wait' a specific amount of time before it
can 'see' if the code under test functioned as expected) and many others.

Also, it would be great to add some rules for other languages, like Python,
JavaScript, Go, etc, maybe by creating a separate tools for other languages.
So, if you are inspired by our idea, you can create your own tool for your
favorite language.

We also faced with the problem with projects that already have a lot of tests
written in a different style and sometimes it is extremely tedious to fix all
the problems manually. So, one more direction which makes sense is automatic
fixer, which will fix most of the problems automatically, but it is a bit more
complicated task that will be implemented later as a separate tool. But if you
already have some ideas how to implement it, please let us know and we will be
happy to help you.

### Ask for contribution

Of course jtcop is an open source tool, so we are always happy to receive any
feedback or contribution from the community. Make forks if you need and create
your own test checkers suitable for your needs, or just use the already existing
plugin. If you have any ideas or suggestions, please, feel free to create an
issue or pull request in
our [repository](https://github.com/volodya-lombrozo/jtcop) and share your
thoughts with us.

## Final Thoughts

Navigating through all the ideas about clear unit tests and best practice
for writing them we created a tool which helps us to keep our tests more or less
consistent, easy to read and orginized by similar principles. We have lots of
plans for the future, but we hope that you will find jtcop useful even now.
So, please, try it and share your thoughts with us, or create your own tool for
your specific needs, because it can improve the quality of your code and save
significant amount of time for you and your team.

## References

Here is a list of articles and discussion threads that inspired us to create
jtcop:

* [On the Layout of Tests](https://www.yegor256.com/2023/01/19/layout-of-tests.html),
  Yegor Bugayenko
* [Unit testing Anti-patterns catalogue](https://stackoverflow.com/questions/333682/unit-testing-anti-patterns-catalogue),
  StackOverflow
* [Anatomy of a Good Java Test](https://dzone.com/articles/anatomy-of-a-good-java-test),
  Sam Atkinson
* [Java Unit Testing Best Practices](https://dzone.com/articles/java-unit-testing-best-practices-how-to-get-the-mo),
  Brian McGlauflin
* [Clean Unit Testing](https://dzone.com/articles/clean-unit-testing), Alex
  Staveley
* [Unit Testing Anti-Patterns — Full List](https://dzone.com/articles/unit-testing-anti-patterns-full-list),
  Yegor Bugayenko
* [7 Tips for Writing Better Unit Tests in Java](https://dzone.com/articles/7-tips-for-writing-better-unit-tests-in-java),
  Reshma Bidikar
* [Importance of Testing](https://dzone.com/articles/importance-of-testing-grant-fritchey),
  Grant Fritchey
* [Importance of Unit Testing](https://dzone.com/articles/importance-of-unit-testing),
  Bala Murugan
* [Software Unit Testing: What Is That? Why Is it Important?](https://dzone.com/articles/software-unit-testing-what-is-that-why-is-it-impor),
  Anna Smith
* [What Is Test-Driven Development and Why It’s Important](https://dzone.com/articles/what-is-test-driven-development-and-why-its-import),
  Anna Smith