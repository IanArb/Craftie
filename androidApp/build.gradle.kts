import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    implementation("androidx.activity:activity-compose:1.3.1")

    add(
        PLUGIN_CLASSPATH_CONFIGURATION_NAME,
        "androidx.compose.compiler:compiler:${Versions.compose}"
    )
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")
    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiTooling)
    implementation(Compose.foundationLayout)
    implementation(Compose.material)
    implementation(Compose.navigation)
    implementation(Compose.placeholderMaterial)
    implementation(Compose.coilCompose)
    implementation(Compose.constraintCompose)

    implementation(Koin.android)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    implementation(Hilt.hiltCompose)

    implementation("com.google.android.gms:play-services-maps:17.0.1")
    implementation("com.google.maps.android:maps-ktx:2.3.0")

    testImplementation(Test.turbine)
    testImplementation(Test.kotestAndroid)
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation(Test.coroutinesTest)
    testImplementation(Test.junit)

    //Firebase
    implementation("com.google.firebase:firebase-bom:28.4.0")

    compileOnly("io.realm.kotlin:library:0.5.0")
}

android {
    compileSdk = 30
    defaultConfig {
        applicationId = "com.craftie.android"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        val key = "MAPS_API_KEY"
        manifestPlaceholders["googleMapsApiKey"] = if (System.getenv(key).isNullOrEmpty()) fetchLocalApiKey() else System.getenv(key)
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

fun fetchLocalApiKey(): String {
    val local = Properties()
    local.load(project.rootProject.file("local.properties").inputStream())

    val mapsApiKeyId = "MAPS_API_KEY"

    return local[mapsApiKeyId] as String
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}