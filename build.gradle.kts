import com.expediagroup.graphql.plugin.gradle.graphql
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "1.4.20"
    id("application")
    //id("com.expediagroup.graphql") version "3.7.0"
    id("com.expediagroup.graphql") version "4.0.0-alpha.9"
}

group = "xyz.noahsc"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "slashsourcegraph.AppKt"
}

graphql {
    client {
        endpoint = "https://sourcegraph.com/.api/graphql"
        packageName = "slashsourcegraph.graphql"
        queryFiles = listOf(file("${project.projectDir}/src/main/resources/query.graphql"))
        allowDeprecatedFields = true
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    mavenCentral()
}

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDirs("src/main/kotlin", "build/generated/source/graphql/main")
    }
}

val ktorVersion = "1.3.1"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson", "gson", "2.8.6")

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")

    //implementation("org.slf4j:slf4j-simple:1.7.26")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.apache.logging.log4j:log4j-core:2.12.1")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.+")

    implementation("com.expediagroup:graphql-kotlin-ktor-client:4.0.0-alpha.9")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

val fatJar = task("fatJar", type = Jar::class) {
    archiveFileName.set(project.name + ".jar")
    manifest {
        attributes["Main-Class"] = "slashsourcegraph.AppKt"
    }
    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}