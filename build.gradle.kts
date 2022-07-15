import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    id("maven-publish")
}

group = "net.minikloon.fsmgasm"
version = "1.0"

dependencies {
    testImplementation(kotlin("test"))
}


repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
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