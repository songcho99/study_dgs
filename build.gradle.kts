plugins {
	kotlin("jvm") version "2.0.21"
	kotlin("plugin.spring") version "2.0.21"
	kotlin("plugin.jpa") version "2.0.21"
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
	id ("com.netflix.dgs.codegen") version "7.0.3"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"
java.sourceCompatibility = JavaVersion.VERSION_21

allOpen {
	annotation("jakarta.persistence.Entity")
}

repositories {
	mavenCentral()
}

extra["netflixDgsVersion"] = "10.2.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	runtimeOnly("com.h2database:h2")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

dependencyManagement {
	imports {
		mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${property("netflixDgsVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.generateJava {
	language = "KOTLIN"
	typeMapping = mutableMapOf(
		"ID" to "Long"
	)
}