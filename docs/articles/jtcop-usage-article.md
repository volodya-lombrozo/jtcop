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

The primary features of jtcop for now are:

* All tests have a corresponding production class (integration tests are
  exceptions).
* All tests have assertion explanatory message + assertion message itself
* All tests have correct test name (present tense + CamelCase + doesn't contain
  the "test" word and spam letters + special characters)
* Checks [line hitters](https://stackoverflow.com/a/10323328/10423604) (that
  checks nothing).
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

* Consistency: Discuss how jtcop can enforce consistency across a team or
  project.
* Improved Onboarding: Explain how a well-organized and named test suite can
  reduce the onboarding time for new team members.
* Better Refactoring: Discuss how clear tests help in the refactoring process by
  providing an understandable safety net.

## Feedback and Community Contribution

* Open Source Nature: If jtcop is open source, encourage community contributions
  and feedback.
* Roadmap: Share your vision for the future of jtcop. Highlight upcoming
  features or areas you'd like the community's help with.

## Conclusion

* Recap: Reiterate the importance of best practices in test organization and
  naming.
* Call to Action: Encourage readers to try jtcop in their projects and share
  their experiences.

## References

* List the articles, discussion threads, and any other resources you referenced
  throughout the article.
* Throughout the article, ensure to use screenshots, code examples, and any
  other visual aids to enhance clarity and engagement. Make sure to engage with
  the readers by asking questions or soliciting feedback, especially if the
  community can contribute to the plugin's development.