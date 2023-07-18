import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.23"
    id("com.vaadin") version "24.1.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

group = "de.mdk"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

extra["vaadinVersion"] = "24.1.2"

dependencies {
    implementation("com.vaadin:vaadin-spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.nats:jnats:2.11.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("com.vaadin:vaadin-bom:${property("vaadinVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.register("createDockerNetwork"){
    doLast{
        exec{
            executable("sh")
            args("-c", " docker network inspect nats >> /dev/null 2>&1 || docker network create nats")
        }
    }
}

tasks.register("runNatsServer"){
    dependsOn("createDockerNetwork")
    doLast{
        exec{
            executable("sh")
            args("-c", "docker container inspect nats || docker run --name nats " +
                    "-d --network nats --rm -p 4222:4222 -p 8222:8222 nats --http_port 8222")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
