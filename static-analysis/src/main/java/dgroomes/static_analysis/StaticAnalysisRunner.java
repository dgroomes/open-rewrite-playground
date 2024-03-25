package dgroomes.static_analysis;

import org.openrewrite.*;
import org.openrewrite.internal.InMemoryLargeSourceSet;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.RemoveUnusedImports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Please see the README for more information.
 */
public class StaticAnalysisRunner {

    private static final Logger log = LoggerFactory.getLogger(StaticAnalysisRunner.class);

    public static void main(String[] args) throws IOException {
        Path currentDirectory = Paths.get(".");
        if (!Files.exists(currentDirectory.resolve("example-program"))) {
            var msg = "The 'static-analysis' program is expected to be run from the root of the 'open-rewrite-playground' project, but the current directory is " + currentDirectory;
            throw new IllegalStateException(msg);
        }

        Path programUnderAnalysisSourceDir = currentDirectory.resolve("example-program/src/main/java");

        // For the demo, let's only apply a single recipe. Normally, you would apply many recipes.
        Recipe recipe = new RemoveUnusedImports();

        ExecutionContext ctx = new InMemoryExecutionContext(Throwable::printStackTrace);

        // The "sourceSet" physically represents the source code files of the program-under-analysis.
        LargeSourceSet sourceSet;
        {
            // Does OpenRewrite implement its own complete parser? Does it use aspects of the JDK's own parser?
            JavaParser javaParser = JavaParser.fromJavaVersion().build();

            // Find all Java source files in the "program-under-analysis".
            List<Path> sourcePaths;
            try (Stream<Path> stream = Files.walk(programUnderAnalysisSourceDir)) {
                sourcePaths = stream
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".java"))
                        .collect(Collectors.toList());
            }

            List<SourceFile> sourceFiles = javaParser.parse(sourcePaths, programUnderAnalysisSourceDir, ctx).toList();
            sourceSet = new InMemoryLargeSourceSet(sourceFiles);
        }

        // The interesting part!
        RecipeRun recipeRun = recipe.run(sourceSet, ctx);

        log.info("Recipe run: {}", recipeRun);
        // The recipe creates a set of code change suggestions. (I think recipes may also report findings without actually
        // suggesting a code change, but this is generally not the case for OpenRewrite, which is primarily a code
        // refactoring tool.)
        List<Result> results = recipeRun.getChangeset().getAllResults();
        for (Result result : results) {
            log.info("Result: {}", result);
            log.info("Diff: {}", result.diff(programUnderAnalysisSourceDir));
        }
    }
}
