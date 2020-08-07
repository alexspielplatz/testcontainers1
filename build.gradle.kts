plugins {
    java
    war
}

repositories {
    mavenCentral()
}

dependencies {
    val junitJupiterVersion = "5.6.2"
    val testcontainersVersion = "1.14.3"
    // This dependency is used by the application.
    implementation("com.google.guava:guava:28.2-jre")
    compileOnly("javax:javaee-api:8.0.1")
    testImplementation("javax:javaee-api:8.0.1")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation("org.testcontainers:mockserver:$testcontainersVersion")
    testRuntimeOnly(group = "org.slf4j", name = "slf4j-log4j12", version = "1.7.29")
    testImplementation("org.apache.httpcomponents.client5:httpclient5:5.0.1")
    implementation("org.glassfish:javax.json:1.1.4")
    testRuntimeOnly("org.eclipse", "yasson", "1.0.1")
    testImplementation("org.assertj", "assertj-core", "3.16.1")

}

tasks.withType(War::class.java, {
    archiveName = "myservice.war"
})

tasks.withType(Test::class.java) {
    dependsOn(tasks.withType(War::class.java))
    dependsOn(tasks.withType(Copy::class.java))
}

tasks.create("prepareDockerFile", Copy::class.java) {
    from("Dockerfile")
    into("Deployment")
    dependsOn(tasks.withType(War::class.java))
}

tasks.create("prepareWar", Copy::class.java) {
    from("/build/libs/myservice.war")
    into("Deployment/war")
    dependsOn(tasks.withType(War::class.java))
}

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
}
