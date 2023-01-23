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

package com.github.lombrozo.testnames;

import com.github.lombrozo.testnames.complaints.ComplexComplaint;
import com.github.lombrozo.testnames.rules.CompositeTestPathRule;
import java.nio.file.Paths;
import java.util.Collection;
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
 */
@Mojo(name = "check", defaultPhase = LifecyclePhase.VALIDATE)
public final class ValidateMojo extends AbstractMojo {

    /**
     * The project to validate.
     */
    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    /**
     * Throw an exception if the test names are invalid.
     * Prints to a log otherwise.
     */
    @Parameter(defaultValue = "true")
    private boolean failOnError = true;

    @Override
    public void execute() throws MojoFailureException {
        final Collection<Complaint> complaints = new CompositeTestPathRule(
            Paths.get(this.project.getTestCompileSourceRoots().get(0))
        ).complaints();
        if (!complaints.isEmpty() && this.failOnError) {
            throw new MojoFailureException(new ComplexComplaint(complaints).message());
        } else if (!complaints.isEmpty()) {
            complaints.forEach(complaint -> this.getLog().warn(complaint.message()));
        }
    }
}
