plugins {
    id("java")
}

group = "nihvostain"
version = "test"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.0")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation(project(":common"))

}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "nihvostain.Main"  // Укажите ваш главный класс
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE  // Исключить дубликаты
}