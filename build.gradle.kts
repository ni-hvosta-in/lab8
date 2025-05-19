plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

javafx {
    version = "21" // Используйте LTS-версию
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("nihvostain.Main")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf(
        "--add-opens", "javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-exports", "javafx.graphics/com.sun.javafx.application=ALL-UNNAMED"
    )
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // JDK 17 обязательно
    }
}