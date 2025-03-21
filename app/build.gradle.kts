import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
    id("com.google.devtools.ksp")
    id("androidx.room") version "2.6.1" apply false
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.nexa.cinemate"
    compileSdk = 35

    packagingOptions {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    defaultConfig {
        applicationId = "com.nexa.cinemate"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        multiDexEnabled = true
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.json.jvm)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    ksp(libs.androidx.room.compiler)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.glide)
    implementation(libs.accompanist.pager)
    implementation(libs.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.ui.tooling)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.koin.android)
    implementation(libs.coil.compose)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.junit.junit)
    testImplementation(libs.insert.koin.koin.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.jupiter.junit.jupiter)

    androidTestImplementation(libs.insert.koin.koin.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.ui.test.manifest)
}
