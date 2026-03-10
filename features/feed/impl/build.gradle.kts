plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.kerosene.features.feed"
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":features:feed:api"))
                implementation(project(":core:common"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:navigation"))
                implementation(project(":core:network:api"))
                implementation(libs.ktor.core)
                implementation(libs.ktor.client.serialization)
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
    namespace = "com.kerosene.features.feed"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
