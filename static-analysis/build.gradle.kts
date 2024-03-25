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
}

application{
    mainClass.set("dgroomes.static_analysis.StaticAnalysisRunner")
}
