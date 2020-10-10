import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    id(Libraries.GradlePlugin.application)
    id(Libraries.GradlePlugin.kotlin_multiplatform) version Versions.kotlin
    id(Libraries.GradlePlugin.shadow) version Versions.shadow
}

group = Packages.group
version = Packages.version

repositories {
    mavenCentral()
    jcenter()
}

kotlin {
    jvm {
        withJava()
    }

    js(IR) {
        useCommonJs()
        binaries.executable()

        browser {
            webpackTask {
                outputFileName = "main.bundle.js"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libraries.Kotlin.stdlib_jdk8)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Libraries.Ktor.server_netty)
                implementation(Libraries.Ktor.server_sessions)
                implementation(Libraries.Ktor.auth)
                implementation(Libraries.Ktor.html_builder)
                implementation(Libraries.Ktor.gson)
                implementation(Libraries.Ktor.websockets)
                implementation(Libraries.Kotlin.css_jvm)
                implementation(Libraries.Exposed.core)
                implementation(Libraries.Exposed.jdbc)
                implementation(Libraries.sqlite_driver)
                implementation(Libraries.logback)
            }
        }

        val jsMain by getting {
            dependencies {

            }
        }
    }
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

application {
    mainClassName = Packages.main_class
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to Packages.main_class
            )
        )
    }
}

// include JS artifacts in any JAR we generate
tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}