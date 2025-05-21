plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "nihvostain"
version = "test"
javafx {
    version = "21" // Используйте LTS-версию
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("nihvostain.Main")
}

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

    implementation(project(":common"))

}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "nihvostain.Main"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE  // Исключить дубликаты
}
tasks.withType<JavaExec> {
    jvmArgs = listOf(
        "--module-path", "C:/openjfx-21.0.7_windows-x64_bin-sdk/javafx-sdk-21.0.7/lib",
        "--add-modules", "javafx.controls,javafx.fxml"
    )
}

