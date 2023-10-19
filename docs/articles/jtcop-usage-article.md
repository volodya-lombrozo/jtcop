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
But why is this so important?

## Best Practice

Well, every time I delve into a new project, I'm presented with an entirely new,
distinct landscape. I start by reading the documentation and quickly delve into
the project's tests. First and foremost, tests are much better than even some
good documentation, since they are usually up-to-date, in contrast to the
documentation which can sometimes be misleading. By examining integration
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
even exhibit
some [anti-patterns](https://dzone.com/articles/unit-testing-anti-patterns-full-list),
it becomes challenging to work with the project. And of course, this isn't my
original thought. There are numerous articles that emphasize the importance of
well-structured and formatted tests, offering best practices and insights.

The first particularly significant aspect of these practices is the way tests
are named. Both the
articles, [Anatomy of a Good Java Test](https://dzone.com/articles/anatomy-of-a-good-java-test)
and [Importance of Unit Testing](https://dzone.com/articles/importance-of-unit-testing),
stress the paramount importance of effective test naming, especially from a
documentation perspective. They advise against the use of the word "test" in
test names, emphasizing that proper naming can serve as a clear descriptor
of what the test aims to verify or validate.
Additionally,
the [Clean Unit Testing](https://dzone.com/articles/clean-unit-testing) article
not only underscores effective test naming but also delves into the significance
of variable naming and ordering, both pivotal for enhancing test readability.

Assertions also hold an important position in testing best practices.
For instance, the
article [7 Tips for Writing Better Unit Tests in Java](https://dzone.com/articles/7-tips-for-writing-better-unit-tests-in-java)
strongly advises using assertions over print statements in tests. Another
recurring theme in multiple articles is the principle of
[limiting](https://dzone.com/articles/clean-unit-testing) and arranging
assertions within a single unit test.
The [AAA](https://stackoverflow.com/tags/arrange-act-assert/info)
pattern (Arrange, Act, Assert) is a good example of positioning
assertions at the tail end of the test method.
Adopting such a consistent structure ensures that tests are straightforward and
easily understandable to other developers.
Additionally, the clarity of the assertions themselves is also of utmost
importance.
They should be accompanied by clear and expressive messages, as mentioned
many times in numerous articles,
like [this](https://dzone.com/articles/anatomy-of-a-good-java-test)
and [this](https://www.yegor256.com/2023/01/19/layout-of-tests.html).

Furthermore,
some [best practices](https://dzone.com/articles/clean-unit-testing)
highlight important rules for the appropriate use of
[mocks and stubs](https://stackoverflow.com/questions/3459287/whats-the-difference-between-a-mock-stub),
avoiding "if" statements in test blocks, focusing on a single case in each unit,
and making tests
as [isolated and automated](https://dzone.com/articles/java-unit-testing-best-practices-how-to-get-the-mo)
as possible. We must also consider high code coverage and the
importance of testing negative scenarios and borderline cases, in addition to
positive ones. Lastly, it's essential to avoid non-deterministic
and [flaky tests](https://dzone.com/articles/a-detailed-guide-on-flaky-tests-causes-detection-a)
as well as the
unit-test [anti-patterns](https://stackoverflow.com/questions/333682/unit-testing-anti-patterns-catalogue)
I've previously mentioned.

So, yes, there's a lot of tacit knowledge that we can borrow from the community
to craft good unit and integration tests. And this isn't the end of the story.
There are other emerging ideas worth mentioning. For instance, the recent idea
about the
[layout of tests](https://www.yegor256.com/2023/01/19/layout-of-tests.html)
emphasizes the importance of maintaining a structured "layout" for both
unit and integration tests. This isn't merely about refactoring unit tests; it's
about organizing them systematically within the source code.

But are all these ideas and principles just theoretical, or do we have
ready-made solutions that help us adhere to these principles and keep our tests
consistent and clear?

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

Jtcop prefers the last pattern:

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
method `checksSum()`. But what if the test occasionally fails? I believe most
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
an [experimental](#experimental) feature.
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
  }}

public class SumTest {
  @Test
  void calculatesSum() {
    Summator s = new Summator(new Enivornement.Fake());
    Assertions.assertEquals(
      2, s.sum(1, 1), "Something went wrong, because 1 + 1 != 2"
    );
  }}
```

In this case you can avoid using any annotations, utility classes, static
methods:
just use Fake objects. As for Fake objects aren't a part of the testing code,
jtcoo doesn't consider them as a problem. It is just a short introduction
into Fake objects, so you can read more about that approach right
[here](https://www.yegor256.com/2014/09/23/built-in-fake-objects.html).

### Test Assertions

The next important point that jcop checks assertions in tests. The most
important check is, of course, the presense of assertions in a test.
jtcop understands two assertion types `JUnit` assertions and `Hamcrest`
assertions.

For example the next test is incorrect:

```java
@Test
void calculatesSum(){
  if(sum(1, 1) != 2){
    throw new RuntimeException("1 + 1 != 2");
  }
}
```

where the next is correct:

```java
@Test
void calculatesSum(){
  Assertions.assertEquals(
    2, sum(1,1), "Something went wrong, because 1 + 1 != 2"
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

This feature was extremely useful for us, because we don't need to ask the
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