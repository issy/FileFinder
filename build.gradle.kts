plugins {
    id("java")
}

group = "com.issy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Project dependencies
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
    implementation("commons-cli:commons-cli:1.9.0")

    // Test only
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.issy.Main"
    }
}
