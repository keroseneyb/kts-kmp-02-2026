plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        commonMain.dependencies {
            // No dependencies here for maximum decoupling
        }
    }
}

android {
    namespace = "com.kerosene.core.auth.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
