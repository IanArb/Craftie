import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    implementation("androidx.activity:activity-compose:1.3.0-beta01")

    add(PLUGIN_CLASSPATH_CONFIGURATION_NAME, "androidx.compose.compiler:compiler:${Versions.compose}")
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiTooling)
    implementation(Compose.foundationLayout)
    implementation(Compose.material)
    implementation(Compose.navigation)
    implementation(Compose.accompanist)

    implementation(Koin.android)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    implementation(Hilt.hiltCompose)

    testImplementation(Test.turbine)
    testImplementation(Test.kotestAndroid)
    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation(Test.coroutinesTest)

    //Firebase
    implementation("com.google.firebase:firebase-bom:28.1.0")
}

android {
    signingConfigs {
        named("debug") {
            keyAlias = "craftie"
            keyPassword = "Guinness101"
            storeFile = rootProject.file("keystores/keystore-debug")
            storePassword = "Guinness101"
        }
        register("release") {
            keyAlias = "craftie"
            keyPassword = "Guinness101"
            storeFile = rootProject.file("keystores/keystore-release")
            storePassword = "Guinness101"
        }
    }

    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.craftie.android"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    packagingOptions {
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
    }
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}