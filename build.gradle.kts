// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version = "1.8.0"
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    alias(libs.plugins.google.gms.google.services) apply false
}