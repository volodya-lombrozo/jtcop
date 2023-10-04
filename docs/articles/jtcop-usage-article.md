# jtcop

## Introduction

### Background

I more than sure that we rarther often change our job titles, projects,
teams, technologies, and each time when I open a new project I see the
entirely different picture of the project, and specifically, the tests.
You may wonder why it so important. Well, because the well-organaized
tests are like a good documentation, they are easy to read and easy to
understand. If I want to add something new to a project, I always look through
the project tests and always start from them. I don't insist here that we
should write tests first, I'm just saying that tests are important for many
reasons (which one?):

### Purpose of the Article

So we decided to create a tool that will help us to keep our tests
more or less consistent, easy to read and orginized by similar principles.
We even created a tool which help us to keep our tests according with the rules
which we think are important for Java projects. I believe that the similar tool
might be created for other languages you like.

In this article I will try to explain how it fits into the lanscape of static
analysis tools and how it can halp you in our every-day-programming.

Of course, we aren't trying to say that our rules are the best and the only
way to organize tests, but we think that they are good enough to be used across
many projects and teams. And we hope that you will find them useful too.

* Clarity: Discuss the importance of immediately understanding what a test does
  and why.
* Maintainability: Describe the challenges of maintaining poorly organized or
  ambiguously named tests.

## The Inspiration Behind jtcop

### Referenced Articles.

**!Highlight some of the main takeaways!**

Other articles:

- https://dzone.com/articles/anatomy-of-a-good-java-test (Anatomy of a Good Java
  Test).
  We write tests for a number of reasons: To shape the design of our system.
  To ensure initial and ongoing correctness. Documentation.
    - The article significances the importance of test naming "The name of the
      test is crucially important, particularly from a documentation
      standpoint. "
    - Never start a test name with “test”.
    - "Don’t be afraid to be expressive. If your test name needs to be really
      long then go for it if it’s clear what’s going on."
    - The article also suggests to split tests into three sections: Setup,
      Action, Assertion, (Or even more famous Arrange, Act, Assert).
      Which also significances the importance of clear test structure.
    - ~"Don't use cryptic assertion messages"
- https://dzone.com/articles/importance-of-testing-grant-fritchey \[Opinion] (
  Importance of Testing).
    - Highlights the importance of testing (and not only for software
      development)
    - "Appropriate testing is one of the single best ways you can protect your
      production environment and your business"
    - "Testing will be a fundamental aspect of everything we do"
    - "Make testing a central part of everything that you do"
    - It isn't an article, it is an opinion
- https://dzone.com/articles/java-unit-testing-best-practices-how-to-get-the-mo (
  Java Unit Testing Best Practices)
    - Highlights the importance of Unit testing
        - "validates that each piece of your software", "a solid foundation for
          future development"
        - "identifies defects at early stages "
        - "safer to refactor"
        - Even helps in code review process and consider corner cases
    - **Best practice** of writing unit tests:
        - Unit Tests Should Be Trustworthy (fail when it needed)
        - **Unit Tests Should Be Maintainable and Readable**
        - Unit Tests Should Verify a Single Use Case
        - Unit Tests Should Be Isolated (Tests should be runnable on any
          machine, in any order, without affecting each other.)
        - Unit Tests Should Be Automated
        - Use a Good Mixture of Unit and Integration Tests
        - **"writing unit tests as you write your application code"**
- https://dzone.com/articles/7-tips-for-writing-better-unit-tests-in-java (7
  Tips for Writing Better Unit Tests in Java)
    - Use a Framework for Unit Testing (JUnit, TestNG)
    - Use Test Driven Development Judiciously!
    - Measure Code Coverage
    - Externalize test data wherever possible (@Parameterized.Parameters)
    - **Use Assertions Instead of Print Statements**
    - Build tests that have deterministic results
    - Test negative scenarios and borderline cases, in addition to positive
      scenarios
- https://dzone.com/articles/importance-of-unit-testing (Importance of Unit
  Testing)
    - "testing ensures security, customer satisfaction, and saves money in the
      long run. Sometimes it can save lives as well"
    - !**"Use consistent naming conventions and test one code at a time."**
    - "Make sure that there is a corresponding unit test case for a module if
      there is any change in the code. All the bugs must be fixed before moving
      to the next phase. It’s better to test as you commit a code to avoid
      errors. Focus more on the tests that affect the behavior of the system. "
    - "It takes less time to find and fix the bugs during unit testing"
    - "You end up with more reliable code as the bugs are fixed in the initial
      stage"
- https://dzone.com/articles/software-unit-testing-what-is-that-why-is-it-impor (
  Software Unit Testing: What Is That? Why Is it Important?)
    - "localize every little mistake quickly and accurately as well as simplify
      the debugging process if any issue occurs"
    - "unit tests give the ability to detect errors at an early stage of the
      development process "
    - "this technique helps to save time and costs in terms of future
      developments"
    - "alignment between specification and the result of development"
- https://dzone.com/articles/what-is-test-driven-development-and-why-its-import (
  What Is Test-Driven Development and Why It’s Important)
    - The same points as a
      previous [article](https://dzone.com/articles/software-unit-testing-what-is-that-why-is-it-impor)
- https://dzone.com/articles/clean-unit-testing (Clean Unit Testing)
    - "Doing unit testing properly is a lot harder than people think."
    - Reason for Failure (Single resposibility principle by mocking all except
      the unit (class) itself)
    - Stubbing and Mocking (use stubs and mocks in corresponding cases)
    - **"unit test code is much more readable when it mirrors the code under
      test"**
    - **Variable Declaration Order** "It is much easier to read the test code if
      they stubs and mocks are defined in the same order as they appear in the
      class that is being tested."
    - **Variable Naming** (The variable names used to represent the stubs and
      mocks should be the same names that are used in the actual code.)
    - Avoid Over Stubbing
    - "A test should check a single interaction with the class you are testing.
      Now, this doesn't mean you can only have one assert, it means you should
      have a narrow when and a wide then. "
    - This article also consider three different parts of the test: "given:,
      when:,, and then"
    - This article also mentions the importance of Assertions order (the same
      for mocking and stubbing)
    - **"A when:  code block or a then: code block should never contain an if
      statement."**
- https://dzone.com/articles/unit-testing-anti-patterns-full-list (Unit Testing
  Anti-Patterns — Full List)
    - Anti-patterns which have to be avoided
- https://stackoverflow.com/questions/333682/unit-testing-anti-patterns-catalogue
    - Anti-patterns which have to be avoided
- https://www.yegor256.com/2023/01/19/layout-of-tests.html
    - "The layout of tests is a very important aspect of the quality of your
      code. It's not just about how you name your test methods,
      it's about how you organize them in the source code."

Interesting from other articles (cites):

- "In this article, I am going to share an example of such a test app. You can
  do the same in your projects.
- "Technology stack: Netbeans IDE. Java. Maven."
- "developers either don't write unit tests at all, don't write enough tests, or
  they don't maintain them"

### Gap Identification

Talk about what existing tools offer and where they fall short in terms of
checking test organization and naming conventions:
Tools to check:

* PMD
* Checkstyle
* SonarQube
* (SpotBugs?)

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
    2, sum(1, 1), "Something went wrong, because 1 + 1 != 2"
  );
}
```

or with using Hamcrest assertions:

```java
@Test
void calculatesSum(){
  MatcherAssert.assertThat(
    "Something went wrong, because 1 + 1 != 2",
    sum(1, 1),
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
void checksSuccessfully() {
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
void checksSuccessfully() {
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
  sum(1, 1);
}
```

This often happens when a developer writes in order to increase code coverage
and doesn't care about the quality of the test.

Some tools might find the absence of assertions in tests, and in this case
developers might cheat a bit to avoid the problem:

```java
@Test
void calculatesSum(){
  sum(1, 1);
  Assertions.assertTrue(true);
}
```

Well, it is the same "Line Hitter", but with assertion statement. So, jtcop
is able to find such tests and mark them as incorrect.

### Corresponding Production Class

As was already [mentioned](https://www.yegor256.com/2023/01/19/layout-of-tests.html#test-classes),
the well-formatted methods of unit tests is not enough. 
For example, you have a test class with the name `SumTest.java` with 
the test method `checksSum()` which tests the method `sum()`. But occasionally, 
the test fails. What will you do? You will open the test class to find a problem.
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

//todo: What about integration tests 




### Test Methods Only

* Only test methods.

Todo: Detailed Comparison with Other Tools: Draw parallels with tools like
CheckStyle or PMD. Highlight the unique selling points of jtcop.

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
//todo
```

Or in case of Line hitter pattern, as it was discussed erlier:

```shell
//todo
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