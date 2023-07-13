/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Volodya
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
  "Please rename the IncorrectITCaseName test class to start or end with one of the following prefixes",
  "Please rename the IncorrectITName test class to start or end with one of the following prefixes",
  "Please rename the IncorrectName test class to start or end with one of the following prefixes",
  "Please rename the IncorrectTestCaseName test class to start or end with one of the following prefixes",
  "Please rename the IncorrectTestName test class to start or end with one of the following prefixes",
  "Please rename the IncorrectTestsName test class to start or end with one of the following prefixes",
].each { assert log.contains(it): "Log doesn't contain ['$it']" }
[
  "Please rename the CorrectNameIT test class",
  "Please rename the CorrectNameITCase test class",
  "Please rename the CorrectNameTestCase test class",
  "Please rename the CorrectNameTest test class",
  "Please rename the CorrectNameTests test class",
  "Please rename the TestsCorrectName test class",
  "Please rename the TestCorrectName test class",
  "Please rename the TestCaseCorrectName test class",
  "Please rename the ITCaseCorrectName test class",
  "Please rename the ITCorrectName test class",
].each { assert !log.contains(it): "Log contains ['$it']" }
true