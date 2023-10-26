# Mastering Test Code Quality Assurance

## Introduction

Over the years, many articles have highlighted the importance of unit and
integration tests and their benefits. They enable quick and accurate
[identification of errors](https://dzone.com/articles/importance-of-unit-testing),
simplify the debugging process,
support [safe refactoring](https://dzone.com/articles/java-unit-testing-best-practices-how-to-get-the-mo),
and
prove [invaluable during code reviews](https://www.yegor256.com/2019/12/03/testing-in-code-review.html).
These tests can also
significantly
reduce [development costs](https://dzone.com/articles/what-is-test-driven-development-and-why-its-import),
help catch mistakes early, and ensure
the final
product [aligns well with its specifications](https://dzone.com/articles/software-unit-testing-what-is-that-why-is-it-impor).
As such, testing is often
viewed as
a [central part](https://dzone.com/articles/importance-of-testing-grant-fritchey)
of the development process. However, within the
developer community, it's become clear in recent years that merely having unit
and integration tests isn't enough. A growing number of blog posts and articles
emphasize the need for well-structured and formatted tests. So, why is this
aspect so crucial?

## Best Practices

In short, poorly formatted tests or those
exhibiting [anti-patterns](https://dzone.com/articles/unit-testing-anti-patterns-full-list)
can significantly hamper a project's progress. It's not just my
perspective. Many articles stress the significance of well-structured tests
and provide best practices and insights on this topic.

One element that frequently emerges as pivotal in these discussions is the
naming of tests. Two articles in particular,
[Anatomy of a Good Java Test](https://dzone.com/articles/anatomy-of-a-good-java-test)
and [Importance of Unit Testing](https://dzone.com/articles/importance-of-unit-testing),
underscore the crucial role of effective test naming. They advise against using
the word "test" in test names, suggesting that appropriate naming can clearly
describe the test's objective or what it intends to verify. Additionally, the
article [Clean Unit Testing](https://dzone.com/articles/clean-unit-testing)
highlights not only the naming of test methods but also the importance for
maintainability of correct naming and ordering test variables.

Branching out from naming, assertions are another cornerstone in testing best
practices. Take, for instance, the
article [7 Tips for Writing Better Unit Tests in Java](https://dzone.com/articles/7-tips-for-writing-better-unit-tests-in-java)
that highlights the advantage of using assertions over print statements.
Other industry experts often emphasize limiting the number of assertions and
correctly positioning them within a single test.
The [AAA](https://stackoverflow.com/tags/arrange-act-assert/info)
pattern (Arrange, Act, Assert) is the perfect example of this intention:
by positioning assertions at the end of the test method, it ensures clarity and
readability for other developers. Moreover, the transparency of the
assertions themselves is also important. For instance, they should come
with [descriptive messages](https://dzone.com/articles/anatomy-of-a-good-java-test).

In fact, there are more suggestions to keep in mind:

* Appropriate usage of
  [mocks and stubs](https://stackoverflow.com/questions/3459287/whats-the-difference-between-a-mock-stub).
* Avoiding "if" statements in test blocks.
* Focusing on a single case in each unit
* Making tests
  as [isolated and automated](https://dzone.com/articles/java-unit-testing-best-practices-how-to-get-the-mo)
  as possible.
* Maintaining
  high [test and code coverage](https://dzone.com/articles/code-coverage-vs-test-coverage-which-is-better).
* Testing negative scenarios and borderline cases, in addition to positive ones.
* Avoiding non-deterministic results
  and [flaky tests](https://dzone.com/articles/a-detailed-guide-on-flaky-tests-causes-detection-a)
* Avoiding
  unit-test [anti-patterns](https://stackoverflow.com/questions/333682/unit-testing-anti-patterns-catalogue)

Yet, the realm of best practices is ever-evolving and this list isn't
exhaustive. New best practices continue to emerge. For example, the recent idea
about [layout of tests](https://www.yegor256.com/2023/01/19/layout-of-tests.html)
highlights the importance of structuring both unit and integration tests within
the source code. It's not just about refactoring tests anymore but also about
organizing them systematically within the source code.

In summation, as you can see, the community provides a variety of best practices
for creating quality tests. The real question, however, is: Are
these principles just theoretical, or are there practical solutions that can
help us achieve such quality?

## Gap Identification

Yes, I'm referring to static analyzers. Let's briefly examine the most widely
used ones, even though there are many similar tools available. I will focus only
on rules and checks that help to address at least some best practices discovered
previously.

### Checkstyle

[Checkstyle](https://checkstyle.sourceforge.io) is a development tool that helps
programmers write Java code that adheres to a coding standard. In other words,
Checkstyle is a static code analysis
tool ([linter](https://en.wikipedia.org/wiki/Lint_(software))) used in the Java
world. Although Checkstyle doesn't
provide features specifically tailored for tests, many of its features are
applicable to test code, just as they are to production code. It can assist with
javadoc comments, indentation, line length, cyclomatic complexity, etc. However,
to the best of my knowledge, the only feature related to tests is the ability to
enforce the test names convention by developing a specific checker. So, yes,
before using it, you need to develop your
own  [checker](https://stackoverflow.com/questions/24415234/configure-checkstyle-method-names-for-test-methods-only)
first.

Thus, while Checkstyle is a general tool that focuses solely on Java code, it
doesn't specifically address issues with tests. It doesn't consider specific
rules related to assertion checks, identification of anti-patterns,
or maintaining the layout of tests - all of which are essential to keep tests
consistent and clear in line with industry requirements and best practices.

### PMD

[PMD](https://pmd.github.io) is one more source code analyzer similar to
Checkstyle. It finds common programming flaws like unused variables, empty
catch blocks, unnecessary object creation, and so forth. While it supports
many different languages, here we are interested in Java only.
PMD, comparing with Checkstyle, has much more rules that check tests quality,
for example (but not limited to):

1. [JUnitAssertionsShouldIncludeMessage](https://pmd.github.io/pmd/pmd_rules_java_bestpractices.html#junitassertionsshouldincludemessage) -
   requires JUnit assertions include a message.
2. [JUnitTestContainsTooManyAsserts](https://pmd.github.io/pmd/pmd_rules_java_bestpractices.html#junittestcontainstoomanyasserts)
   checks if JUnit or TestNG test contains too many assertion statements.
3. [JUnitTestsShouldIncludeAssert](https://pmd.github.io/pmd/pmd_rules_java_bestpractices.html#junittestsshouldincludeassert)
   checks that JUnit tests include at least one assertion.
4. [TestClassWithoutTestCases](https://pmd.github.io/pmd/pmd_rules_java_errorprone.html#testclasswithouttestcases)
   checks that test classes have at least one testing method.
5. [UnnecessaryBooleanAssertion](https://pmd.github.io/pmd/pmd_rules_java_errorprone.html#unnecessarybooleanassertion)
   checks that JUnit assertions are used correctly without `assertTrue(true)`
   statements (line-hitter anti-pattern detection.)

Here is a short example of test violations that PMD can find:

```java
public class Foo extends TestCase { 
  public void testSomething() {
    // [JUnitAssertionsShouldIncludeMessage] Use the form:
    // assertEquals("Foo does not equals bar", "foo", "bar");
    // instead
    assertEquals("foo", "bar");
  }}

//[TestClassWithoutTestCases] Consider adding test methods if it is a test:
public class Bar extends TestCase {}

public class MyTestCase extends TestCase { 
  // Ok
  public void testMyCaseWithOneAssert() {
    boolean myVar = false;
    assertFalse("should be false", myVar);
  }
    
  //[JUnitTestsShouldIncludeAssert]
  //Bad, don't have any asserts 
  public void testSomething() {
    Bar b = findBar();
    b.work();
  }

  //[JUnitTestContainsTooManyAsserts]: 
  //Bad, too many asserts (assuming max=1)
  public void testMyCaseWithMoreAsserts() {
    boolean myVar = false;
    assertFalse("myVar should be false", myVar);
    assertEquals("should equals false", false, myVar);
    //[UnnecessaryBooleanAssertion] Bad, serves no real purpose - remove it:
    assertTrue(true);
  }}
```

However, all these checks are designed primarily for JUnit assertions and, in
some cases, for AssertJ. They don't support [Hamcrest](https://hamcrest.org)
assertions, which are widely adopted in the industry.
Also, while PMD can check method names, these checks are relatively simple. They
focus on aspects such as method name length, avoiding special characters like
underscores, and adhering
to [camel case](https://en.wikipedia.org/wiki/Camel_case) naming conventions.
Consequently, these checks are primarily intended for production code only and
don't examine
specific [test name patterns](https://dzone.com/articles/7-popular-unit-test-naming).
Moreover, to the best of my knowledge, PMD doesn't identify structural mistakes
or verify the correct placement of methods. Thus, PMD provides a rather limited
set of checks for tests.

### Sonar Qube

[SonarQube](https://www.sonarqube.org) is also a widely used tool for checking
code quality. SonarQube has a lot of rules similar to PMD that can be applied to
tests, for example:

1. [TestCases should contain tests](https://rules.sonarsource.com/java/tag/tests/RSPEC-2187/).
2. [Literal boolean values and nulls should not be used in assertions.](https://rules.sonarsource.com/java/tag/tests/RSPEC-2699/)
3. [Assertions should not compare an object to itself.](https://rules.sonarsource.com/java/tag/tests/RSPEC-5863/)
4. [Test assertions should include messages.](https://rules.sonarsource.com/java/tag/tests/RSPEC-2698/)
5. [Test methods should not contain too many assertions.](https://rules.sonarsource.com/java/tag/tests/RSPEC-5961/)
6. [Similar tests should be grouped in a single Parameterized test.](https://rules.sonarsource.com/java/tag/tests/RSPEC-5976/)

At the time of writing this text, there are
around [45 rules](https://rules.sonarsource.com/java/tag/tests/RSPEC-2701/)
specifically designed for tests. As you might have noticed, SonarQube has more
rules than PMD, although many of them overlap. However, to the best of my
knowledge, SonarQube doesn't check Hamcrest assertions and doesn't maintain the
layout of tests. It also doesn't show much concern about checking test
anti-patterns.

In summary, while static analyzers like Checkstyle, PMD, and SonarQube offer a
range of rules and checks to ensure code quality, there are noticeable gaps in
their capability to holistically address test-related concerns.
Checkstyle primarily focuses on Java production code consistency and offers
limited features specifically for tests, necessitating users to craft their own
checkers for unique test-related constraints. PMD offers a comprehensive set of
rules for JUnit assertions, but it lacks
support for widely-used frameworks like Hamcrest and does not check method
naming patterns.
SonarQube offers a broad array of rules, many of which overlap with PMD.
However, it still falls short in examining certain critical test checks, such as
Hamcrest assertions, test anti-patterns, and the correct placement of tests.
Collectively, while these tools lay a foundation for maintaining test code
quality, there is still significant room for improvement in aligning with
industry test standards and best practices.

## Introducing jtcop

In order to address the aforementioned gaps, we developed a new static
analyzer called [jtcop](https://github.com/volodya-lombrozo/jtcop) that focuses
on test quality in Java projects. It is a simple Maven plugin that checks tests
for common mistakes and anti-patterns.
We use it in our projects, and it has helped us maintain consistent and clear
tests. It also speeds up PR reviews significantly by preventing recurring
comments about issues like improper test placement or naming.
Although, we don't think our rules are the only good way to set
up tests, so feel free to share your ideas and suggestions by submitting tickets
and PRs.
In the following, I'll explain how jtcop fits into the landscape of static
analysis tools, which checks it utilizes, and how it can assist you in your
everyday programming.

### Test Names

I'm sure you know there are many ways to name your test. For example, you can
find
various [test naming conventions](https://dzone.com/articles/7-popular-unit-test-naming)
or even
some  [threads](https://stackoverflow.com/questions/155436/unit-test-naming-best-practices)
that have lengthy discussions on how to do it correctly.
Here is just a short summary how you can name your tests:

| Pattern                                                  | Example                                                    |
|----------------------------------------------------------|------------------------------------------------------------|
| methodName_stateUnderTest_expected                       | add_negativeNumbers_throwsException()                      |
| **when**\_condition_**then**_expected                    | when_ageLessThan18_then_isUnderageIsTrue()                 |
| **given**\_precondition_**when**\_action_**then**_result | given_userIsAdmin_when_deleteIsCalled_then_deleteSuccess() |
| test\[methodName]                                        | testAdd() or testIsUnderage()                              |
| **should**_expectedBehavior_when_condition               | should_throwException_when_negativeNumbersAreAdded()       |
| methodName_expected                                      | add_returnsSum() or isUnderage_returnsTrue()               |
| **can**Action                                            | canDeleteUser() or canCalculateSum()                       |
| methodName_doesExpectedBehavior                          | add_doesReturnSum() or isUnderage_returnsTrue()            |
| verbCondition (or verbResult)                            | calculatesSum() or deletesSuccessfully()                   |

`jtcop` prefers the last pattern:

1. Test names should use the present tense without a subject.
   For example, if you're testing a class `Animal` with a method `eat()`, the
   test name should be `eats()`. If you need to add more context, do it after
   the verb – for instance, `eatsApplesOnly()`.
2. Test names should use [camelCase](https://en.wikipedia.org/wiki/Camel_case).
3. Name shouldn't use the word "test", as it is redundant. The `@Test`
   annotation is sufficient.
4. Special characters like `_` and `$` are forbidden.

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

This style has been chosen by many developers and is widely used in numerous
projects. If you prefer a different pattern for test naming, just let us know,
and we'll be happy to add it to the plugin.

### Corresponding Production Class

Now, let's imagine we have a test class named `SumTest.java` with the test
method `checksSum()`. But what if the test occasionally fails? §believe most
would attempt to locate the issue and find the original class where the problem
occurred. But which class is it? The first guess would likely be `Sum.java`,
right? Yet, you might not find it, perhaps because the production class is named
something like `Addition.java` or `Calculator.java`.
This mismatch in naming conventions can lead to significant confusion and longer
troubleshooting times.
In other words, if you have a test class named `SumTest.java` and the
corresponding production class is `Addition.java`, it can be very confusing. The
more appropriate naming for the test class would be `AdditionTest.java`.
Essentially, the name of the test class isn't merely a label; it serves as a
pointer to the production class, helping developers pinpoint potential issues.

This is where `jtcop` comes into play. It helps ensure that your tests are
consistent with your production classes and suggests appropriate naming
conventions for them, effectively addressing the problem described. If you're
further interested in this issue, you can read about
it [here](https://www.yegor256.com/2023/01/19/layout-of-tests.html#test-classes).

The only exception in this case is integration tests. They are usually named
like `AdditionIT.java` or `AdditionIntegrationTest.java`. However, they should
be placed in a separate package, such as `it`, and have an appropriate suffix
like `IT` or `ITCase`.

### Test Methods Only

The next check is rather strict and is still considered
an [experimental](#experimental-features) feature.
However, the rule itself is simple: test classes should contain methods that are
only annotated with the `@Test` annotation. You might wonder what to do with
initialization methods or common code shared among different test cases. The
answer isn't straightforward and this rule is designed to guide you with it.
There aren't actually many options available. I'm referring to methods
such as static initialization methods, setup methods `@BeforeEach`
and `@AfterEach`, JUnit extensions, and Fake Objects. The approach you choose
for initializing your tests will determine their quality.

#### Static Methods

The first idea that comes to mind is using static methods. Developers often use
static methods to configure a common setup for several tests in the class.
Here's a simple example:

```java
@Test
void calculatesSum(){
  Summator s = init();
  Assertions.assertEquals(
    2, sum(1, 1), "Something went wrong, because 1 + 1 != 2"
  );
}

private static Summator init(){
  Summator s = new Summator();
  // Setup
  return s;
}
```

At first glance, it might seem like a good solution, but it does have inherent
problems. When such a method is used within a single class, it's usually not a
major concern, even though static methods typically lead to
[low cohesion and tight coupling](https://dzone.com/articles/static-classes-are-evil-or-make-your-dependencies).
However, issues arise when you begin to use it across multiple
classes or try to consolidate such methods into a centralized `TestUtils.java`
class. In this case the approach with static methods can become problematic:

1. It can lead to [confusion](#corresponding-production-class) for developers
   since `TestUtils.java` doesn't correspond to any class in the production
   code.
2. `TestUtils.java` might be considered
   an [anti-pattern](https://stackoverflow.com/questions/3340032/are-utility-classes-evil).

Thus, `jtcop` deems static methods in tests and utility classes as dangerous and
prohibits them. If you attempt to run `jtcop` against the previous code sample,
you'll receive the following warning message:

```shell
All methods should be annotated with @Test annotation.
```

#### SetUp and TearDown Methods

The next widely-used approach involves the so-called "setUp" methods.
By "setUp" methods, I'm referring to those annotated
with`@BeforeAll`, `@BeforeEach`, `@AfterAll`, or `@AfterEach`. An example of
using these annotations is as follows:

```java
Summator s;

@BeforeEach
void setUp(){
  s = new Summator();
  // Setup
}

@Test
void calculatesSum(){
  Summator s=init();
  Assertions.assertEquals(
    2, sum(1,1), "Something went wrong, because 1 + 1 != 2"
  );
}
```

This approach makes the situation even worse for many reasons.
The most obvious reason, familiar to most developers, is the need to "jump"
between test methods and the initialization part.
Then, over time, as the codebase grows and changes and as the number of test
cases in the test class increases, developers may become unaware of the
setup/teardown that happens for each test. This can lead to potential
misinterpretations. Furthermore, a developer may end up with setup code that is
unnecessary for certain tests, thus violating the principle of keeping tests
minimal and setting up only what is needed.
Next, using such methods can introduce another problem. They can lead to a
shared state between tests if not managed properly. This harms test isolation,
an extremely important quality of any test, which in turn can result in flaky
tests.
Moreover, using `@BeforeAll` and `@AfterAll` use static methods which inherit
all disadvantages of the [previous approach](#static-methods).

Hence, `jtcop` doesn't allow the use of such `setUp`/`tearDown` methods.

#### Test Extensions

Now, let's examine the approach supported by `jtcop`. JUnit 5
offers [Test Extensions](https://junit.org/junit5/docs/current/user-guide/#extensions)
that allow for the creation of custom extensions. These extensions
can be used to configure setup and teardown logic for all the tests in a class.

```java

@ExtendWith(SummatorExtension.class)
public class SumTest {
  @Test
  void calculatesSum(Summator s) {
    Assertions.assertEquals(
      2, s.sum(1, 1), "Something went wrong, because 1 + 1 != 2"
    );
  }}

class SummatorExtension implements ParameterResolver {
  @Override
  public boolean supportsParameter(ParameterContext pctx, ExtensionContext ectx) {
    return pctx.getParameter().getType() == Summator.class;
  }
  @Override
  public Object resolveParameter(
    Summator s =new Summator();
    // Setup
    return s;
  }}
```

Extensions offer a way to craft more modular and reusable test setups. In this
scenario, we've bypassed the need for utility classes, static
methods, and shared states between tests. These extensions are easily reused
across a multitude of test classes and standalone unit tests. What's more, these
extensions often have insight into the current test class, method, annotations
used, and other contextual details, paving the way for versatile and reusable
setup logic.

#### Fake Objects

Another method for test configuration and setup that `jtcop` supports is the use
of Fake objects, as
recommended [here](https://www.yegor256.com/2014/09/23/built-in-fake-objects.html).
These are positioned with other production objects, yet they provide a unique
"fake" behavior. By leveraging these objects, all setup can be handled directly
in the test, making the code cleaner and easier to read.

```java
abstract class Discount {
  // Usually we have rather complicated 
  // logic here for calculating a discount.
  abstract double multiplier();
  
  static class Fake extends Discount {
    @Override
    double multiplier() {
      return 1;
    }
  }}

public class PriceTest {
  @Test
  void retrievesSamePrice() {
    Price p = new Price(100, new Discount.Fake());
    Assertions.assertEquals(
      100, p.total(), "Something went wrong; the price shouldn't have changed"
    );
  }}
```

Fake objects often sit alongside production code, which is why `jtcop` doesn't
classify them as test classes. While mixing production and test code might seem
questionable, many projects have embraced this approach, finding it a practical
way to set up tests. These Fake objects aren't exclusively for testing; you
might sometimes integrate them into your production code. Additionally, this
strategy eliminates the need for using Mock frameworks with intricate
initialization logic.

### Test Assertions

`jtcop` also underscores the need to validate assertions in tests.
Recall the [Gap Identification](#gap-identification) section? Several tools out
there offer similar checks. Yet, many of them focus solely on JUnit assertions
or only catch high-level errors. `jtcop` supports both Hamcrest and JUnit
assertions and adheres to stricter guidelines for assertions. To paint a
clearer picture, let's dive into a few code snippets.

```java
@Test
void calculatesSum(){
  if(sum(1, 1) != 2){
    throw new RuntimeException("1 + 1 != 2");
  }
}
```

This code snippet lacks any assertions, meaning `jtcop` will warn about it.
Check out the next snippet as a proper replacement, and note the use of the
Hamcrest assertion.

```java
@Test
void calculatesSum(){
  assertThat(
    "Something went wrong, because 1 + 1 != 2",
    sum(1, 1),
    equalTo(2)
  );
}
```

Pay attention on the explanatory messages in the
assertion `Something went wrong, because 1 + 1 != 2` from the code below.
They're essential. Without such messages, it can
sometimes be challenging to understand what went wrong during test execution,
which can puzzle developers. For instance, consider this
[real example](https://github.com/volodya-lombrozo/jtcop/blob/c742a5cad69d4e2ae4e895c0c7a4b42f9d0122e5/src/test/java/com/github/lombrozo/testnames/CopTest.java#L38).
I've simplified it for clarity:

```java
@Test
void checksSuccessfully(){
  assertThat(
    new Cop(new Project.Fake()).inspection(),
    empty()
  );
}
```

Now, suppose this test fails. In that scenario, you'll receive the following
exception message:

```shell
Expected: an empty collection
  but: <[Complaint$Text@548e6d58]>
```

Not very informative, right? However, if you include an explanatory message in
the assertion:

```java
@Test
void checksSuccessfully(){
  assertThat(
    "Cop should not find any complaints in this case, but it has found something.",
    new Cop(new Project.Fake()).inspection(),
    empty()
  );
}
```

With this inclusion, you're greeted with a far more insightful message:

```shell
java.lang.AssertionError: Cop should not find any complaints in this case, but it has found something.
Expected: an empty collection
  but: <[Complaint$Text@548e6d58]>
```

In a perfect world, we'd offer more details — specifically, some context. This
sheds light on initialization values and provides developers with valuable
hints. As it stands, the message is significantly clearer and more approachable
than what we had before.

This feature has really made a difference for us. We no longer need to ask
developers to add explanations to their assertions in many PR reviews. Now, the
`jtcop` handles that for us.

### Line Hitters

The last feature I'd like to spotlight is
the [Line Hitter](https://stackoverflow.com/a/10323328/10423604) anti-pattern
detection.

> At first glance, the tests cover everything and code coverage tools confirm it
> with 100%, but in reality the tests merely hit the code, without doing any
> output analysis.

What this means is that you might stumble upon a test method in a program that
does not really verify anything. Take this for instance:

```java
@Test
void calculatesSum(){
  sum(1, 1);
}
```

This typically happens when a developer is more into their code coverage
numbers than genuinely ensuring the robustness of the test. There are tools that
can spot when assertions are missing in tests. But, as you know, developers
might always find a way around problems:

```java
@Test
void calculatesSum(){
  sum(1,1);
  assertThat(
    "I'm just hanging around",
    true,
    is(true)
  );
}
```

Yep, that’s our "Line Hitter" again, only this time, it's wearing the disguise
of an assertion statement. Luckily, `jtcop` can detect such tests and flag
them as unreliable.

## Setting Up jtcop

To get started with `jtcop`, simply add the plugin to your build configuration
file. If you're using Maven, here's how you can do it:

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

By default, the plugin operates in the `verify` phase, so no need to specify it.
However, if you wish to modify it, simply add the desired phase to the
`execution` section. Then, to run `jtcop`, use the following command:

```shell
mvn jtcop:check
```

If you stumble upon an issue, say, a test lacking a corresponding production
class, you'll get a clear error message:

```shell
[ERROR] Test SumTest doesn't have corresponding production class.
[ERROR]  Either rename or move the test class ./SumTest.java.
[ERROR]  You can also ignore the rule by adding @SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass") annotation.
[ERROR]  Rule: RuleAllTestsHaveProductionClass.
[ERROR]  You can read more about the rule here: <link>
```

Similarly, for the "Line Hitter" pattern previously mentioned:

```shell
[ERROR] Method 'calculatesSum' contains line hitter anti-pattern.
[ERROR]  Write valuable assertion for this test.
[ERROR]  You can also ignore the rule by adding @SuppressWarnings("JTCOP.RuleLineHitter") annotation.
[ERROR]  Rule: RuleLineHitter.
[ERROR]  You can read more about the rule here: <link>
```

By default, `jtcop` will halt the build if it detects issues with your tests. If
you only want to use it to highlight problems without interrupting the build,
you can configure `jtcop` to display only warning messages by adjusting the
failOnError property.

```xml

<configuration>
  <failOnError>false</failOnError>
</configuration>
```

However, I highly recommend keeping the default setting to maintain high-quality
tests.

#### Experimental features

As I mentioned earlier, some features are still experimental. To try them out,
just add the following configuration to your `pom.xml` file:

```xml

<configuration>
  <experimental>true</experimental>
</configuration>
```

Once done, all experimental features will be active in your project, giving
cleaner and more organized tests.

## Benefits

`jtcop` has already helped us in several ways:

* **Code Review**: The primary issue addressed by `jtcop` is the frequent
  appearance of comments such as "place this test class here", "rename this test
  method", or "that's a testing anti-pattern". `jtcop` saves time and aid
  developers in resolving these issues before even making a PR into a
  repository.

* **Improved Onboarding**: Another advantage we've observed is that
  well-structured and appropriately named test methods not only facilitate code
  understanding and maintenance, but also reduce the time spent explaining or
  documenting code style guides across multiple projects. As a result, we often
  receive well-formatted pull requests from new team members with little to no
  additional guidance.

* **Consistency**: `jtcop` ensures our tests remain consistent across numerous
  projects. So, when you delve into a project that uses `jtcop`, it becomes
  significantly easier to comprehend its workings and start contributing to it.

Overall, integrating `jtcop` has significantly streamlined our processes,
enhancing collaboration and understanding across our development projects.

## Future Plans

Looking ahead, we're preparing to enhance `jtcop` with additional rules.
One of our primary focuses is to address several anti-patterns like the ones
highlighted in this
insightful [StackOverflow thread](https://stackoverflow.com/questions/333682/unit-testing-anti-patterns-catalogue)
Just to name a few:

* **The Mockery** - tests that have too many mocks.
* **Excessive Setup** - tests that demand extensive setup.
* **Wait and See** - tests that need to pause for a specific duration before
  verifying if the tested code works as intended.

It's worth noting that these are just a few examples; there's a broader spectrum
of anti-patterns we're considering.

Additionally, we've also encountered issues with projects that have many tests
written in various styles. At times, it's incredibly tedious to address these
issues manually. Thus, another viable avenue is developing an application that
will automatically solve most of these problems.

So, if you have ideas or suggestions, please don't hesitate to open an issue or
submit a pull request in
our [repository](https://github.com/volodya-lombrozo/jtcop)
and share your thoughts with us. We're always eager to get feedback
or contributions from the community. Feel free to fork it if you want and craft
your own test checkers that fit your needs, or simply use `jtcop` as is.

## References

There are articles and discussion threads that inspired the creation of `jtcop`.
These might be helpful to delve deeper into the topic of test quality.

Importance of testing:

* [Importance of Testing](https://dzone.com/articles/importance-of-testing-grant-fritchey),
  Grant Fritchey
* [Importance of Unit Testing](https://dzone.com/articles/importance-of-unit-testing),
  Bala Murugan
* [Software Unit Testing: What Is That? Why Is it Important?](https://dzone.com/articles/software-unit-testing-what-is-that-why-is-it-impor),
  Anna Smith
* [What Is Test-Driven Development and Why It’s Important](https://dzone.com/articles/what-is-test-driven-development-and-why-its-import),
  Anna Smith

Best practices:

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
* [7 Popular Unit Test Naming Conventions](https://dzone.com/articles/7-popular-unit-test-naming)
  Ajitesh Kumar
* [Unit test naming best practices ](https://stackoverflow.com/questions/155436/unit-test-naming-best-practices)
  StackOverflow
* [Best Practices for Writing Unit Tests: A Comprehensive Guide](https://dzone.com/articles/best-practices-for-writing-unit-tests-a-comprehens)
  Or Hillel

Other:

* [A Detailed Guide on Flaky Tests: Causes, Detection, and Solutions](https://dzone.com/articles/a-detailed-guide-on-flaky-tests-causes-detection-a)
  Rhea Dube
* [Static Classes Are Evil, Make Your Dependencies Explicit](https://dzone.com/articles/static-classes-are-evil-or-make-your-dependencies)
  Vadim Samokhin
* [Built-in Fake Objects](https://www.yegor256.com/2014/09/23/built-in-fake-objects.html)
  Yegor Bugayenko

