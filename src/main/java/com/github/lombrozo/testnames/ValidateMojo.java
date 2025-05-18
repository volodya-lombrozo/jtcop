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

import com.github.lombrozo.testnames.bytecode.BytecodeProject;
import com.github.lombrozo.testnames.complaints.ComplaintCompound;
import com.github.lombrozo.testnames.javaparser.JavaParserProject;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * The validate mojo.
 *
 * @since 0.1.0
 * @todo #178:90min Add BytecodeProject to the Cop.
 *  The Cop should be able to work with both ProjectJavaParser and BytecodeProject together.
 *  It will be able to check the test names in the compiled classes.
 *  We also have to add integration tests for the RuleAllTestsHaveProductionClass that will be
 *  able to check the test names in the compiled classes.
 */
@Mojo(name = "check", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true)
public final class ValidateMojo extends AbstractMojo {

    /**
     * The project to validate.
     */
    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    /**
     * Throw an exception if the test names are invalid.
     * Prints to a log otherwise.
     * @checkstyle MemberNameCheck (7 lines)
     */
    @SuppressWarnings("PMD.ImmutableField")
    @Parameter(defaultValue = "true")
    private boolean failOnError = true;

    /**
     * Ignore generated tests.
     * @checkstyle MemberNameCheck (7 lines)
     */
    @SuppressWarnings("PMD.LongVariable")
    @Parameter(defaultValue = "false")
    private boolean ignoreGeneratedTests;

    /**
     * Use experimental features.
     * Since most of the experimental features are not stable and more strict
     * they are disable by default.
     * @checkstyle MemberNameCheck (7 lines)
     */
    @SuppressWarnings("PMD.ImmutableField")
    @Parameter(defaultValue = "false")
    private boolean experimental;

    /**
     * The rules that have to be excluded from execution.
     */
    @Parameter(property = "exclusions")
    private String[] exclusions;

    /**
     * The directory with the generated sources.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources")
    private File sources;

    /**
     * The directory with the generated test sources.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-test-sources")
    private File tests;

    /**
     * Max number of mocks allowed.
     * Needed for {@link com.github.lombrozo.testnames.rules.RuleTestCaseContainsMockery}.
     * @checkstyle MemberNameCheck (3 lines)
     */
    @Parameter(defaultValue = "2")
    private int maxNumberOfMocks;

    /**
     * Skip the validation.
     */
    @Parameter(defaultValue = "false")
    private boolean skip;

    @Override
    public void execute() throws MojoFailureException {
        if (this.skip) {
            this.getLog().info(
                "Validation by JTCOP is skipped because the configuration parameter 'skip' is set to 'true'."
            );
        } else {
            this.validate();
        }
    }

    private void validate() throws MojoFailureException {
        this.getLog().info("Validating tests...");
        final ProjectWithoutJUnitExtensions proj = new ProjectWithoutJUnitExtensions(
            new Project.Combined(this.projects())
        );
        final Collection<Complaint> complaints = new ArrayList<>(
            new Cop(proj, new Parameters("maxNumberOfMocks", this.maxNumberOfMocks)).inspection()
        );
        if (this.experimental) {
            complaints.addAll(new Cop(proj, Cop.experimental()).inspection());
        }
        if (!complaints.isEmpty() && this.failOnError) {
            throw new MojoFailureException(new ComplaintCompound(complaints).message());
        } else if (!complaints.isEmpty()) {
            complaints.forEach(complaint -> this.getLog().warn(complaint.message()));
            this.getLog().info(
                "Read more about rules: https://github.com/volodya-lombrozo/jtcop/blob/main/docs/rules/"
            );
        }
        if (complaints.isEmpty()) {
            this.getLog().info("All tests are valid");
        }
    }

    /**
     * All projects to validate.
     * @return The projects
     */
    private Collection<Project> projects() {
        final Set<String> suppressed = this.suppressed();
        return Stream.concat(
            this.generated(suppressed),
            Stream.of(
                new JavaParserProject(
                    Paths.get(this.project.getCompileSourceRoots().get(0)),
                    Paths.get(this.project.getTestCompileSourceRoots().get(0)),
                    suppressed
                )
            )
        ).collect(Collectors.toList());
    }

    /**
     * The suppressed rules.
     * @return The suppressed rules
     */
    private Set<String> suppressed() {
        return Arrays.stream(this.exclusions)
            .filter(Objects::nonNull)
            .map(RuleName::new)
            .map(RuleName::withoutPrefix)
            .collect(Collectors.toSet());
    }

    /**
     * The generated projects.
     * @param suppressed The suppressed rules
     * @return The generated projects
     */
    private Stream<Project> generated(final Set<String> suppressed) {
        return Stream.of(
            new BytecodeProject(this.sources, this.tests),
            new JavaParserProject(
                this.sources.toPath(),
                this.tests.toPath(),
                suppressed
            )
        ).map(this::generated);
    }

    /**
     * The generated project.
     * @param proj The project
     * @return The generated project
     */
    private Project generated(final Project proj) {
        final Project result;
        if (this.ignoreGeneratedTests) {
            result = new Project.WithoutTests(proj);
        } else {
            result = proj;
        }
        return result;
    }
}
