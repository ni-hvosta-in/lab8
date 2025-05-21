plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

javafx {
    version = "23.0.1" // Используйте LTS-версию
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("nihvostain.Main")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf(
        "--module-path", "C:/openjfx-21.0.7_windows-x64_bin-sdk/javafx-sdk-21.0.7/lib",
        "--add-modules", "javafx.controls,javafx.fxml"
    )
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "nihvostain.Main"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE  // Исключить дубликаты
}