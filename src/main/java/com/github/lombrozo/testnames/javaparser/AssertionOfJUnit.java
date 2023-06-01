package com.github.lombrozo.testnames.javaparser;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;

public class AssertionOfJUnit implements ParsedAssertion {

    /**
     * The method call.
     */
    private final MethodCallExpr call;

    /**
     * The allowed methods.
     */
    private final Set<String> allowed;

    public AssertionOfJUnit(final MethodCallExpr call) {
        this(call, AssertionOfJUnit.allowedJUnitNames());

    }

    private AssertionOfJUnit(final MethodCallExpr call, final Set<String> methods) {
        this.call = call;
        this.allowed = methods;
    }

    @Override
    public boolean isAssertion() {
        return this.call.getArguments().size() > 2
            && this.allowed.contains(this.call.getName().toString());
    }

    @Override
    public Optional<String> explanation() {
        final Optional<String> result;
        final NodeList<Expression> args = this.call.getArguments();
        if (args.isEmpty() || args.size() < 3) {
            result = Optional.empty();
        } else {
            result = Optional.ofNullable(args.get(2).asStringLiteralExpr().asString());
        }
        return result;
    }

    private static Set<String> allowedJUnitNames() {
        return Arrays.stream(Assertions.class.getMethods())
            .filter(AssertionOfJUnit::isAssertion)
            .map(Method::getName)
            .collect(Collectors.toSet());
    }

    private static boolean isAssertion(final Method method) {
        final int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers);
    }
}
