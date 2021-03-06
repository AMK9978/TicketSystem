import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"

//	id("io.swagger.core.v3.swagger-gradle-plugin") version "2.1.9"
	id("org.flywaydb.flyway") version "5.2.4"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
	kotlin("plugin.jpa") version "1.4.32"
}

group = "com.Balloon"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("org.projectlombok:lombok:1.18.18")
	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")

	runtimeOnly("org.postgresql:postgresql:42.1.4")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2:1.3.148")
	testImplementation("org.junit.vintage:junit-vintage-engine:5.7.1")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

flyway {
	driver = "org.postgresql.Driver"
	baselineVersion = "1"
	url = "jdbc:postgresql://127.0.0.1:5432/balloonet"
	user = "amir"
	password ="amir"
	baselineOnMigrate = true
	locations = arrayOf("filesystem: resources / db / migration")
}
