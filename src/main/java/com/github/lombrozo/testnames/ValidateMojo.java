package com.github.lombrozo.testnames;

import java.nio.file.Paths;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "check", defaultPhase = LifecyclePhase.VALIDATE)
public class ValidateMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    public MavenProject project;

    @Override
    public void execute() throws MojoFailureException {
        try {
            new CompositeTestPathRule(
                Paths.get(this.project.getTestCompileSourceRoots().get(0))
            ).validate();
        } catch (final WrongTestName ex) {
            throw new MojoFailureException(ex);
        }
    }
}
