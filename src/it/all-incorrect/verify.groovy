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
  'Test name \'ForbiddenWordTest#test\' doesn\'t follow naming rules, because test name doesn\'t have to contain the word \'test\', test path:',
  "Test name 'ForbiddenWordTest#test' doesn't follow naming rules, because the test name has to be written using present tense, test path:",
  "Test name 'ForbiddenWordTest#test1' doesn't follow naming rules, because test name doesn't have to contain the word 'test', test path:",
  "Test name 'ForbiddenWordTest#test1' doesn't follow naming rules, because the test name has to be written using present tense, test path:",
  "Test name 'ForbiddenWordTest#TEST' doesn't follow naming rules, because test has to be written by using Camel Case, test path:",
  "Test name 'ForbiddenWordTest#TEST' doesn't follow naming rules, because test name doesn't have to contain the word 'test', test path:",
  "Test name 'ForbiddenWordTest#TEST' doesn't follow naming rules, because the test name has to be written using present tense, test path:",
  "Test name 'ForbiddenWordTest#TEST1' doesn't follow naming rules, because test has to be written by using Camel Case, test path:",
  "Test name 'ForbiddenWordTest#TEST1' doesn't follow naming rules, because test name doesn't have to contain the word 'test', test path:",
  "Test name 'ForbiddenWordTest#TEST1' doesn't follow naming rules, because the test name has to be written using present tense, test path:",
  "Test name 'ForbiddenWordTest#createsTEST' doesn't follow naming rules, because test name doesn't have to contain the word 'test', test path:",
  "Test name 'ForbiddenWordTest#createsWithAnothertest' doesn't follow naming rules, because test name doesn't have to contain the word 'test', test path:",
  "Test name 'ForbiddenWordTest#testAnother' doesn't follow naming rules, because test name doesn't have to contain the word 'test', test path:",
  "Test name 'ForbiddenWordTest#testAnother' doesn't follow naming rules, because the test name has to be written using present tense, test path:"
].each { assert log.contains(it): "Log doesn't contain ['$it']" }
true