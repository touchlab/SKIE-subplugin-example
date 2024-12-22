import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    compileOnly(libs.touchlab.skie.compiler.core)
    implementation(projects.exampleSkieSubpluginApi)
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}
