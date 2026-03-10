plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:common"))
            api(project(":core:auth:api"))
            implementation(project(":core:network:api"))
            implementation(libs.koin.core)
            implementation(libs.kotlin.serialization)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.fragment)
                implementation(libs.koin.android)
                implementation(libs.androidx.datastore)
            }
        }
    }
}

android {
    namespace = "com.kerosene.core.auth"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
