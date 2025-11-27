/*
 * MIT License
 *
 * Copyright (c) 2022-2025 Volodya Lombrozo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.lombrozo.testnames;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ProductionTest {
    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius) implements Shape{}
    record Rectangle(double width, double height) implements Shape{}
    record Triangle(double base, double height) implements Shape{}

    static final class AreaCalculator {
        public static double calculate(Shape shape) {
            return switch (shape) {
                case null -> throw new IllegalArgumentException("Shape cannot be null");
                case Circle(double r) -> Math.PI * r * r;
                case Rectangle(double w, double h) -> w * h;
                case Triangle(double b, double h) -> 0.5 * b * h;
            };
        }
    }

    @Test
    public void calculatesAreaUsingPatternMatching() {
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(3.0, 8.0);
        Assertions.assertEquals(78.53981633974483, AreaCalculator.calculate(circle), 0.001, "Circle area calculation failed");
        Assertions.assertEquals(24.0, AreaCalculator.calculate(rectangle), 0.001, "Rectangle area calculation failed");
        Assertions.assertEquals(12.0, AreaCalculator.calculate(triangle), 0.001, "Triangle area calculation failed");
    }
}
