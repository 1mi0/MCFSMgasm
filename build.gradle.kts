import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    id("maven-publish")
}

group = "net.minikloon.fsmgasm"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.minikloon"
            artifactId = "fsmgasm"
            version = "1.0"

            from(components["java"])
        }
    }
}