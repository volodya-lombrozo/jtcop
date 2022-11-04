package com.github.lombrozo.testnames;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
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
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            System.out.println("getTestClasspathElements elements:");
            project.getTestClasspathElements().forEach(System.out::println);
            System.out.println("getTestCompileSourceRoots elements:");
            project.getTestCompileSourceRoots().forEach(System.out::println);
            System.out.println("________");
            final List<Path> tests = Files.walk(
                    Paths.get(project.getTestCompileSourceRoots().get(0)))
                .filter(Files::exists)
                .filter(Files::isRegularFile).collect(Collectors.toList());
            System.out.println("Tests to scan: " + tests);

            for (final Path test : tests) {
                new RuleForAllTests(new JavaTestCode(test)).validate();
            }
        } catch (WrongTestName ex) {
            throw new MojoFailureException(ex);
        } catch (Exception occasion){
            throw new MojoExecutionException(occasion);
        }
    }
}
