import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

group = "com.virtuslab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.kafka:kafka-streams:2.3.0")
    implementation("io.github.microutils:kotlin-logging:1.7.6")
    implementation("org.slf4j:slf4j-simple:1.7.28")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.8")

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("org.apache.kafka:kafka-streams-test-utils:2.3.0")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}