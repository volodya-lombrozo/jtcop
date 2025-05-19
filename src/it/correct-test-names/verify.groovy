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
String log = new File(basedir, 'build.log').text;
[
  "Test class 'IncorrectITCaseName.java' name should start or end with one of the following prefixes",
  "Test class 'IncorrectITName.java' name should start or end with one of the following prefixes",
  "Test class 'IncorrectName.java' name should start or end with one of the following prefixes",
  "Test class 'IncorrectTestCaseName.java' name should start or end with one of the following prefixes",
  "Test class 'IncorrectTestName.java' name should start or end with one of the following prefixes",
  "Test class 'IncorrectTestsName.java' name should start or end with one of the following prefixes",
].each { assert log.contains(it): "Log doesn't contain ['$it']" }
[
  "Test class 'CorrectNameIT.java' name should start or end with one of the following prefixes",
  "Test class 'CorrectNameITCase.java' name should start or end with one of the following prefixes",
  "Test class 'CorrectNameTestCase.java' name should start or end with one of the following prefixes",
  "Test class 'CorrectNameTest.java' name should start or end with one of the following prefixes",
  "Test class 'CorrectNameTests.java' name should start or end with one of the following prefixes",
  "Test class 'TestsCorrectName.java' name should start or end with one of the following prefixes",
  "Test class 'TestCorrectName.java' name should start or end with one of the following prefixes",
  "Test class 'TestCaseCorrectName.java' name should start or end with one of the following prefixes",
  "Test class 'ITCaseCorrectName.java' name should start or end with one of the following prefixes",
  "Test class 'ITCorrectName.java' name should start or end with one of the following prefixes",
].each { assert !log.contains(it): "Log contains ['$it']" }
true