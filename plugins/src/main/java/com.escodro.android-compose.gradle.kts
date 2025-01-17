import extension.androidConfig
import extension.composeBundle
import extension.composeConfig
import extension.composeTestBundle
import extension.proguardConfig

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.escodro.kotlin-quality")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

android {
    androidConfig(libs)
    proguardConfig()
    composeConfig(libs)

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf(
            "-Xstring-concat=inline"
        )
    }
}

dependencies {
    implementation(libs.logcat)
    implementation(libs.composeBundle)
    implementation(platform(libs.composeBom))
    implementation(libs.kotlinxCollectionsImmutable)

    androidTestImplementation(libs.composeTestBundle) {
        exclude(group = "androidx.core", module = "core-ktx")
        exclude(group = "androidx.fragment", module = "fragment")
        exclude(group = "androidx.customview", module = "customview")
        exclude(group = "androidx.activity", module = "activity")
        exclude(group = "androidx.lifecycle", module = "lifecycle-runtime")
    }
}
