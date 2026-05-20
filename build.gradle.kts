plugins {
    id("java")
}

group = "com.application"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework:spring-context:7.0.7")
}

tasks.test {
    useJUnitPlatform()
}