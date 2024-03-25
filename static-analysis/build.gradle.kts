plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.slf4j.api)
    runtimeOnly(libs.slf4j.simple)
    implementation(libs.open.rewrite.core)
    implementation(libs.open.rewrite.java)
    implementation(libs.open.rewrite.java21)
}

application{
    mainClass.set("dgroomes.static_analysis.StaticAnalysisRunner")
}
