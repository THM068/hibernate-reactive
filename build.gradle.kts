import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  id("com.github.johnrengelman.shadow") version "7.0.0"
  id("io.micronaut.library") version "3.1.1"
}

group = "com.chess.player"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val vertxVersion = "4.2.3"
val junitJupiterVersion = "5.7.0"

val mainVerticleName = "com.chess.player.reactive_hibernate_starter.MainVerticle"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
  mainClass.set(launcherClassName)
}

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-web")
  implementation("io.vertx:vertx-mysql-client")
  implementation("org.hibernate.reactive:hibernate-reactive-core:1.1.1.Final")

  implementation("org.apache.logging.log4j:log4j-api:2.14.1")
  implementation("org.apache.logging.log4j:log4j-core:2.14.1")
  implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
  implementation("org.slf4j:slf4j-api:1.7.30")

  implementation("io.vertx:vertx-service-proxy:4.2.4")
  implementation("io.vertx:vertx-codegen:4.2.4")
  annotationProcessor("io.vertx:vertx-codegen:4.2.4:processor")
  annotationProcessor("io.vertx:vertx-service-proxy:4.2.4")
  // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1")

  implementation("io.vertx:vertx-config:4.2.4")


  compileOnly("org.projectlombok:lombok:1.18.22")



  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Verticle" to mainVerticleName))
  }
  mergeServiceFiles()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}

tasks.withType<JavaExec> {
  args = listOf("run", mainVerticleName, "--redeploy=$watchForChange", "--launcher-class=$launcherClassName", "--on-redeploy=$doOnChange")
}

tasks.getByName<JavaCompile>("compileJava") {
  options.annotationProcessorGeneratedSourcesDirectory = File("$projectDir/src/main/generated")
}

sourceSets {
  main {
    java {
      srcDirs ("src/main/generated")
    }
  }
}
