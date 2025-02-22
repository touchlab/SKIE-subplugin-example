plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    jvmToolchain(11)

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()
}

android {
    namespace = "co.touchlab.example.skie_subplugin_api"
    compileSdk = 34
}
