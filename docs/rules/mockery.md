---
title: RuleTestCaseContainsMockery
id: mockery
layout: default
---

# Mockery

Sometimes mocking can be good, and handy. But sometimes developers can lose
themselves in their effort to mock out what isn’t being tested. In this case, a
unit test contains so many mocks, stubs, and/or fakes that the system under
test isn’t even being tested at all, instead data returned from mocks is what
is being tested. [Source][SA-description].

## Bad

```java
import java.util.ArrayList;
import org.mockito.Mockito;

final class FooTest {

    @Test
    void testsSomething() {
        Frame frame = Mockito.mock(Frame.class);
        Mockito.doReturn(new ArrayList<>(...)).when(frame).iterator();
        Table table = Mockito.mock(Table.class);
        Mockito.doReturn(frame).when(table).frame();
        Region region = Mockito.mock(Region.class);
        Mockito.doReturn(table).when(region).table(Mockito.anyString());
    }
}
```

## Good

Consider using less mocks, or try to use [Fake Objects]:

```java
import java.util.ArrayList;
import org.mockito.Mockito;

final class FooTest {

    @Test
    void testsSomething() {
        // FakeRegion, FakeTable, FakeFrame are fake objects, used for testing.
        Region region = new FakeRegion(
            new FakeTable(
                new FakeFrame()
            )
        );
        // region is ready
    }
}
```

You can configure the `maxNumberOfMocks` in plugin configuration (2 is the
default value):

```xml
<configuration>
  <maxNumberOfMocks>3</maxNumberOfMocks>
</configuration>
```

In order to suppress this rule, you can use the following annotation
`@SuppressedWarnings("JTCOP.RuleTestCaseContainsMockery")`.

[Fake Objects]: https://www.yegor256.com/2014/09/23/built-in-fake-objects.html
[SA-description]: https://stackoverflow.com/questions/333682/unit-testing-anti-patterns-catalogue/333686#333686
