# open-rewrite-playground

📚 Learning and exploring OpenRewrite.

> Large-scale automated source code refactoring
> 
> -- <cite>https://docs.openrewrite.org</cite>


## Overview

OpenRewrite is an open source ecosystem for automated source code refactoring. It is backed by a commercial company [Moderne](https://www.moderne.io/).
OpenRewrite supports Java, Kotlin and Groovy. While the main selling point of OpenRewrite is **refactoring** I'm personally
interested in the core engine with regard to static code analysis.

In this project I want to explore the core components of OpenRewrite and I hope to be able to run them against some
example Java code. Here is the design of my exploration:

* `example-program/`
  * This directory contains an example Java program. This represents the "program-under-analysis" because it will be
    scanned by a static analysis runner that is powered by OpenRewrite libraries.
* `static-analysis/`
  * This directory contains a Java program that uses OpenRewrite libraries to scan the example Java program.

This design is a bit different from how OpenRewrite is typically used. OpenRewrite is usually plugged directly into the
"host" build system of the program-under-analysis. For example, a typical usage of OpenRewrite is to add the OpenRewrite
Gradle plugin in the `plugins` block of the project's `build.gradle` file. OpenRewrite also has a way to splice into the
Gradle machinery of the program-under-analysis without making any source code changes to the project. I personally don't
want to execute a foreign project's Gradle build. That's a form of "eval" (a front door for malicious code).

Is a non-eval approach even possible with OpenRewrite? It looks like it was officially documented for some time but was
[removed in the docs](https://github.com/openrewrite/rewrite-docs/pull/245/files).

---
**NOTE**:

This was developed on macOS and for my own personal use.

---


## Instructions

Follow these instructions to build and run the OpenRewrite-powered static analysis.

1. Pre-requisite: Java 21
2. Build the static analysis runner:
   * ```shell
     ./gradlew :static-analysis:installDist
     ```
3. Run the static analysis:
   * ```shell
     ./static-analysis/build/install/static-analysis/bin/static-analysis
     ```
   * It should print a rather verbose description of the "recipe run" and a proposed change to remove an unused import
     in the program-under-analysis. The change is shown as a text diff, like you would see in a Git commit. Neat!


## Reference

* [OpenRewrite Documentation](https://docs.openrewrite.org)
* [OpenRewrite recipe *Find method usages*](https://docs.openrewrite.org/recipes/analysis/search/findmethods)
  * This is a non-refactoring recipe of OpenRewrite. This is the type of capability I'm interested in.
