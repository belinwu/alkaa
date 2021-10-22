import com.android.build.gradle.internal.dsl.ManagedVirtualDevice

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.escodro.kotlin-quality")
    id("kotlin-parcelize")
}

android {
    defaultConfig {
        applicationId = "com.escodro.alkaa"
        versionCode = AlkaaVersions.versionCode
        versionName = AlkaaVersions.versionName

        compileSdk = AlkaaVersions.compileSdk
        minSdk = AlkaaVersions.minSdk
        targetSdk = AlkaaVersions.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        setProperty("archivesBaseName", "${parent?.name?.capitalize()}-$versionName")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
        htmlReport = true
        checkDependencies = true

        lintConfig = file("${rootDir}/config/filters/lint.xml")
        htmlOutput = file("${buildDir}/reports/lint.html")
    }

    setDynamicFeatures(setOf(":features:tracker"))

    compileOptions {
        sourceCompatibility = AlkaaVersions.javaCompileVersion
        targetCompatibility = AlkaaVersions.javaCompileVersion
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.asProvider().get()
    }

    packagingOptions {
        resources.excludes.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }

    testOptions {
        devices {
            add(
                ManagedVirtualDevice("pixel4api30").apply {
                    device = "Pixel 4"
                    apiLevel = 30
                    systemImageSource = "aosp-atd"
                    abi = "x86"
                }
            )
            add(
                ManagedVirtualDevice("pixel2api26").apply {
                    device = "Pixel 2"
                    apiLevel = 26
                    systemImageSource = "aosp"
                    abi = "x86"
                }
            )
            add(
                ManagedVirtualDevice("nexus9api29").apply {
                    device = "Nexus 9"
                    apiLevel = 29
                    systemImageSource = "aosp"
                    abi = "x86"
                }
            )
        }
        deviceGroups {
            create("alkaaDevices").apply {
                targetDevices.addAll(
                    listOf(
                        devices.getByName("pixel4api30"),
                        // TODO add again after tests: devices.getByName("pixel2api26"),
                        // TODO add again after tests: devices.getByName("nexus9api29")
                    )
                )
            }
            unitTests.isReturnDefaultValues = true
        }
    }

dependencies {
    implementation(projects.libraries.core)
    implementation(projects.libraries.splitInstall)
    implementation(projects.libraries.designsystem)
    implementation(projects.libraries.navigation)
    implementation(projects.data.local)
    implementation(projects.data.datastore)
    implementation(projects.data.repository)
    implementation(projects.domain)
    implementation(projects.features.task)
    implementation(projects.features.alarm)
    implementation(projects.features.categoryApi)
    implementation(projects.features.category)
    implementation(projects.features.preference)
    implementation(projects.features.search)
    implementation(projects.features.glance)

    implementation(libs.logcat)
    implementation(libs.compose.navigation)
    implementation(libs.compose.activity)
    implementation(libs.accompanist.animation)
    implementation(libs.androidx.playcore)
    implementation(libs.koin.android)

    implementation(libs.bundles.compose)

    androidTestImplementation(projects.libraries.test)
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.bundles.composetest) {
        exclude(group = "androidx.core", module = "core-ktx")
        exclude(group = "androidx.fragment", module = "fragment")
        exclude(group = "androidx.customview", module = "customview")
        exclude(group = "androidx.activity", module = "activity")
        exclude(group = "androidx.lifecycle", module = "lifecycle-runtime")
    }
}
