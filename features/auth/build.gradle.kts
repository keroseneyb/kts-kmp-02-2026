plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.kerosene.features.auth"
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:common"))
                implementation(libs.compose.navigation)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiTooling)
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.coil)
                implementation(libs.coil.okhttp)
            }
        }
    }
}

android {
    namespace = "com.kerosene.features.auth"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
